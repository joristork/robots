/*
 * Copyright (c) 2006-2010 Sun Microsystems, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the 
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 **/

package org.sunspotworld.demo;
 
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.sensorboard.IDemoBoard;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.Utils;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.sunspotworld.demo.utilities.RadioDataIOStream;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;

/**
 * The main class for the Ectoplasmic Bouncing Ball demo. 
 * This runs on both the desktop and the Sun SPOT device The former
 * requires that setSensorBoard(..) is called with a simulated sensor board 
 * argument before you start.
 * This class handles the radio communication work, and tracking the connection status,
 * but farms out the ball animation
 * and the tastk of setting the color to other classes.
 * 
 * @author randy
 */
public class SPOTBounce extends MIDlet implements ISwitchListener {
    private static int MIN_CHANNEL = 43;             // The lowest channel for passing the ball between spots. See generate3AvailablePortNumbers().

    BallAnimator animator;
    BallColorSetter initialBallColorSetter; 
    
    ISwitch sw1, sw2;                                 //Switches on the demo sensor board.
    
    // Booleans are used to track state. 
    boolean colorIsDetermined;                        //True if this SPOT has found another running this demo, and so has already chosen its color.
    boolean receivedWhoWantsToPlay;                   //True if this SPOT has received an "invitation" asking to report its presence.
    boolean seekingConnectLeft, seekingConnectRight;  //True after you press a Left or Right button, but before it has established some connection.
    boolean listeningForAPartner;                     //True if 
    
    DatagramConnection dgBConn;                       //Used for broadcast
    RadioDataIOStream leftIOStream, rightIOStream;    // Used to pass a ball to another SPOT
    
    long thisAddressAsNumber;
    
    private RadiogramConnection connListenPartner;
    
    public SPOTBounce() {
    }
    
    public void initialize(){
        colorIsDetermined = false;
        seekingConnectLeft = seekingConnectRight = false;
        listeningForAPartner = false;

        ITriColorLED[] leds = MyTriColorLED.getLEDs((ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class));
        for(int i = 0; i<8;i++){
            leds[i].setOn();       // turn each one on
            leds[i].setRGB(1,1,1); // BUG WORKAROUND: set to something non-zero
            leds[i].setRGB(0,0,0); // black it out
        }
        initialBallColorSetter = new BallColorSetter();
        initialBallColorSetter.setClient(this);
        animator = new BallAnimator();
        animator.setClient(this);
        animator.setLeds(leds);
        thisAddressAsNumber = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
        sw1.addISwitchListener(this);
        sw2.addISwitchListener(this);
        Ball ball = new Ball();
        ball.setColorKey(Ball.BLUE);
        animator.addBall(ball, false); //False indicates do not make a sound when invoked
        animator.setAccelerometer((IAccelerometer3D) Resources.lookup(IAccelerometer3D.class));
        startListenForAPartnerThread();  // picks up chatter on the broadcast channel
    }
    
    public void start() throws IOException {
        initialize();
        startLeftEndDisplay();
        startRightEndDisplay();
        startPingForInitialColorThread();
        //Main animation loop ...
        while(true) {
            animator.doKinematics();
            animator.display();
            try {
                Thread.sleep((int) animator.getDt());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void initRadioStream(String addr, int port, boolean toLeft){
        if(toLeft){
            leftIOStream  = RadioDataIOStream.open(addr, port);
        } else{
            rightIOStream = RadioDataIOStream.open(addr, port);
        }
    }
    
    synchronized public void sendRadioStreamMessage(String m, RadioDataIOStream strm)throws IOException {
        synchronized(strm){
            strm.writeUTF(m);
            strm.flush();
        }
    }
    
    synchronized public void sendBroadcastMessage(String m){
        try {
            if(dgBConn == null) dgBConn = (DatagramConnection) Connector.open("radiogram://broadcast:37");
        } catch (IOException ex) {
            System.out.println("Could not open radiogram broadcast connection");
            ex.printStackTrace();
            return;
        }
        try {
            Datagram dg = dgBConn.newDatagram(dgBConn.getMaximumLength());
            dg.writeUTF(m);
            dgBConn.send(dg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void startPingForInitialColorThread(){
        new Thread(){
            public void run() {
                Random rand = new Random(thisAddressAsNumber);
                while(!colorIsDetermined){
                    sendBroadcastMessage("PingForColor");
                    Utils.sleep(1000 + rand.nextInt(1000));
                }
                //  Need one more to make sure everyone hears from everyone else
                    sendBroadcastMessage("PingForColor"); 
            };
        }.start();
    }
    
    public void initialBallColorCue(long x, boolean stopPinging){
        /// For three addresse x is either 2, 0 or -2.
        System.out.println("Initial ball color cue = " + x);
        if(colorIsDetermined || (animator.getNumBalls() != 1)) return;
        if(x > 0){
            animator.colorFirstBall(Ball.BLUE);
        } else if( (int)x == 0) {
            animator.colorFirstBall(Ball.RED);
        } else if( x < 0) {
            animator.colorFirstBall(Ball.GREEN);
        }
        if(stopPinging) colorIsDetermined = true;
    }
    
    /**
     *
     * @throws java.io.IOException
     */
    public void startInvitationThread() throws IOException {
        // System.out.println("[startInvitationThread] starting.");
        new Thread(){
            Random rand = new Random(thisAddressAsNumber);
            public void run() {
                boolean keepRunning = seekingConnectLeft || seekingConnectRight;
                while(keepRunning){
                    int[] ap = generate3AvailablePortNumbers();
                    sendBroadcastMessage("PlayCatch? " + ap[0] + " " + ap[1] + " " + ap[2]);
                    // Sleep randomly for between 1 and 2 seconds.
                    Utils.sleep(1000 + rand.nextInt(1000));
                    keepRunning = seekingConnectLeft || seekingConnectRight;
                    //System.out.println("[startInvitationThread] Another invitation = " + keepRunning);
                }
            };
        }.start();
    }
    
    public void startListenForAPartnerThread(){
        new Thread(){
            public void run() {
                try {
                    listenForAPartner();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //System.out.println("[startListenForAPartnerThread > inner thread] No more partner listening.");
            }
        }.start();
    }
    
    private String[] separateStrings(String msg) {
        StringTokenizer stk = new StringTokenizer(msg, " ");
        String [] result = new String[stk.countTokens()];
        for (int i = 0; stk.hasMoreTokens(); i++) {
            result[i] = stk.nextToken();
        }
        return result;
    }

    /**
     *  There are three states. And three booleans one column for each.
     *                                                 seekingConnect(Left|Right)   acceptingInvitation  foundPartner
     *    seeking a connection      "PlayCatch?"                  true                     false              false
     *    accepting a connection     "Sure"                       true                      true              false
     *    confirming                 "Confirm"                    false                     true               true
     * @throws java.io.IOException
     */
    public void listenForAPartner() throws IOException {
        listeningForAPartner = true;
        String otherAddress;
        int port = -1;
        if(connListenPartner == null) connListenPartner = (RadiogramConnection) Connector.open("radiogram://:37");
        Datagram dg = connListenPartner.newDatagram(connListenPartner.getMaximumLength());
        String message;
        boolean acceptingInvitation = false;
        boolean foundPartner = false;
        System.out.println("[listenForAPartner] started.");
        while(listeningForAPartner){
            acceptingInvitation = false;
            foundPartner = false; 
            connListenPartner.receive(dg); // hang here 
            message = dg.readUTF();
            String messageTokens[] = separateStrings(message);
            otherAddress =  dg.getAddress();
            long otherAddressAsNumber = IEEEAddress.toLong(otherAddress);
            //System.out.println("[listenForAPartner] Got message =" + messageToken + " while invite L, R = " + seekingConnectLeft + ", " + seekingConnectRight);
            if(messageTokens[0].equals("PingForColor")) initialBallColorSetter.collectAddress(otherAddressAsNumber);
            if(seekingConnectLeft || seekingConnectRight) {
                /*
                 * Use address size to break communication symmetry problem.
                 * All are sending "PlayCatch? port1 port2 pot3"
                 * The lesser address replies "Sure greaterAddress selectedPort". The greater must simply wait for that reply.
                 * The greater sends "Confirm lesserAddress".
                 */
                if(messageTokens[0].equals("PlayCatch?") && (thisAddressAsNumber < otherAddressAsNumber)) {
                    port = selectPortFrom(messageTokens[1], messageTokens[2], messageTokens[3]);
                    //System.out.println("[listenForAPartner] got PlayCatch, selected port value = " + port);
                    replyWithDatagram("Sure " + port, dg);
                    //acceptingInvitation = true;
                } else if(messageTokens[0].equals("Sure") && (thisAddressAsNumber > otherAddressAsNumber)) {
                    //Taking the first one we get. Others will saying "Sure" to any PlayCatch they get.
                    port = Integer.valueOf(messageTokens[1]).intValue();
                    replyWithDatagram("Confirm", dg);
                    acceptingInvitation = true;
                    foundPartner = true;
                } else if(messageTokens[0].equals("Confirm") ) { //&& acceptingInvitation){
                    foundPartner = true;
                } else {
                    foundPartner = false;
                    /* 
                     * Other conditions are uninteresting or should not be possible:
                     *  "Play catch?" from lesser address: should be ignored.
                     * "Sure." from greater address was intended for an even greater address.
                     */
                }
                if(foundPartner){ 
                    if(seekingConnectLeft){
                        initRadioStream(otherAddress, port, true);  //true indicates left
                        startRadioStreamListenerThread(leftIOStream);
                        acceptingInvitation = false;
                        seekingConnectLeft = false;  //That will stop the invitation thread
                    }
                    if(seekingConnectRight){
                        initRadioStream(otherAddress, port, false);  //false indicates right
                        startRadioStreamListenerThread(rightIOStream);
                        acceptingInvitation = false;
                        seekingConnectRight = false; //That will stop the invitation thread
                    }
                }
            } else {
                //  System.out.println("[listenForAPartner]       ....ignoring message, not issuing invitations right now.");
            } 
        }
        System.out.println("[listenForAPartner] exiting.");
    }
    
    public void replyWithDatagram(String message, Datagram adressSourceDG){
        Datagram dgreply;
        try {
            dgreply = connListenPartner.newDatagram(connListenPartner.getMaximumLength());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        dgreply.reset();        // reset stream pointer
        dgreply.setAddress(adressSourceDG);
        try {
            dgreply.writeUTF(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            connListenPartner.send(dgreply);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private int selectPortFrom(String string, String string0, String string1) {
        int port;
        Vector pieces = new Vector(); 
        pieces.addElement(string);
        pieces.addElement(string0);
        pieces.addElement(string1);
        String pn;
        if(leftIOStream != null) {
            pn = "" +  leftIOStream.getPortNumber();
            if(pieces.contains(pn))pieces.removeElement(pn);
        }
        if(rightIOStream != null){
            pn = "" + rightIOStream.getPortNumber();
            if(pieces.contains(pn))pieces.removeElement(pn);
        } 
        port = Integer.parseInt((String)(pieces.elementAt(0))); 
        return port;
    }
    
    public int selectPortFromMessage(String message){
        String pn;
        int port;
        String messageTokens[] = separateStrings(message);
        return selectPortFrom(messageTokens[1], messageTokens[2], messageTokens[3]);
    }
    
    
    public void startRadioStreamListenerThread(final RadioDataIOStream strm){
        new Thread(){
            public void run(){
                boolean listen = true;
                while(listen){
                    String s = "";
                    try{
                        s = strm.readUTF();
                    } catch (IOException e) {
                        //Bail out of thread
                        listen = false; 
                    }
                    if(s.equals("Close")) { 
                        strm.close();
                        listen = false;
                    } else if(! s.equals("")) {
                        messageArrived(separateStrings(s), (strm == leftIOStream));
                    }
                }
            }
        }.start();
    }
    
    public void ballOffEnd(Ball ball, boolean toLeft){
        colorIsDetermined = true; //Initial color had better be set by now, cause this event means it is too late.
        try {
            if(toLeft) sendRadioStreamMessage("takeBall" + " " +  ball.serializationString(), leftIOStream);
            else       sendRadioStreamMessage("takeBall" + " " +  ball.serializationString(), rightIOStream); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void messageArrived(String[] strs, boolean lefEnd){
        if("takeBall".equals(strs[0])){
            colorIsDetermined = true; //Initial color had better be set by now, cause this event means it is too late.
            Ball b = Ball.newFromSerializationStrings(strs);
            takeNewBall(b, lefEnd);
        }
    }
    
    public void takeNewBall(Ball ball, boolean leftEnd){
        /* 
         * Ball arrives in state appropriate to come from left. If from right
         * must adjust accordingly.
         */
        if(! leftEnd ) {
            ball.setX( 7.0 - ball.getX());
            ball.setV( - ball.getV());
        } 
        animator.addBall(ball, true); // True indicates make a sound when invoked.
    }
    
    /**
     * The switches toggle between states.
     * If the associated left/right end is not open, start invitations to establishing a connection.
     * If in the process of starting a connection for the NON-associated end (right/left) but none is
     * yet established, just exit immediately.
     * If already in the process of inviting a connection but none is yet established, quit that process.
     *
     **/
    synchronized public void sw1Closed(){
        if(isLeftEndOpen()){ 
            try {
                sendRadioStreamMessage("Close", leftIOStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            leftIOStream.close();
            return;
        }
        if(seekingConnectRight) { 
            seekingConnectRight = false;
            //..that will stop the invitation thread
            // and just in  case from some race condition.
            if(rightIOStream != null) rightIOStream.close();
            //..and presumably seekingConnectLeft is false, so we pass on to opening that up.
        }
        if(seekingConnectLeft){
            seekingConnectLeft = false;
            //..that will stop the invitation thread
            // and just in  case from some race condition.
            if(leftIOStream != null) leftIOStream.close();
            return;
        } else {
            seekingConnectLeft = true;
            try {
                startInvitationThread();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    synchronized public void sw2Closed(){
        if(isRightEndOpen()){ 
            try {
                sendRadioStreamMessage("Close", rightIOStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            rightIOStream.close();
            return;
        }
        if(seekingConnectLeft) { 
            seekingConnectLeft = false;
            //..that will stop the invitation thread
            // and just in  case from some race condition.
            if(leftIOStream != null) leftIOStream.close();
            //..and presumably seekingConnectRight is false, so we pass on to opening that up.
        }
        if(seekingConnectRight){
            seekingConnectRight = false;
            //..that will stop the invitation thread
            // and just in  case from some race condition.
            if(rightIOStream != null) rightIOStream.close();
            return;
        } else {
            seekingConnectRight = true;
            try {
                startInvitationThread();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Callback for when the switch state changes from released to pressed.
     */
    public void switchPressed(SwitchEvent se) {
        if (se.getSwitch() == sw1) {
            sw1Closed();
        } else {
            sw2Closed();
        }
    }

    /**
     * Callback for when the switch state changes from pressed to released.
     */
    public void switchReleased(SwitchEvent se) {
    }
    
    //For this app we only need two.
    public int[] generate3AvailablePortNumbers(){
        int[] r = new int[3];
        Vector used = new Vector();
        // Note in Java's &&, right hand expression is not evaluated unless left is true.
        if( leftIOStream != null &&  leftIOStream.isOpen()) used.addElement(new Integer( leftIOStream.getPortNumber()));
        if(rightIOStream != null && rightIOStream.isOpen()) used.addElement(new Integer(rightIOStream.getPortNumber()));
        //Start at the lowest channel, and search upwards till an available one is found. 
        int c = MIN_CHANNEL;
        int index = 0;
        while(index < 3){
            if (! used.contains(new Integer(c))){
                r[index] = c;
                index = index + 1;
            }
            c = c + 1;
        } 
        return r;
    }
    
    public boolean isLeftEndOpen(){
        if(leftIOStream == null) return false;
        return leftIOStream.isOpen();
    }
    
    public boolean isRightEndOpen(){
        if(rightIOStream == null) return false;
        return rightIOStream.isOpen();
    }
    
    /**
     * The left end of the LEDs is solid red if the left end is "closed" meaning
     * the SPOT is not connected to another SPOT for purposes of passing
     * the ball back and forth. Thi sstarts a thread that dispplay that red
     * LED at the end only if appropriate. It also causes the "pulsing" of the
     * LED during the interval between when you press the button and the 
     * SPOT establishes a connection. You can also stop the pulsing by pressing
     * either button.  
     **/
    public void startLeftEndDisplay(){
        final ITriColorLED led = MyTriColorLED.getLEDs((ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class))[0];
        new Thread(){
            public void run(){
                boolean wasClosed = false;
                boolean isClosed = !isLeftEndOpen();
                while(true){
                    isClosed = !isLeftEndOpen();
                    if(isClosed && (! seekingConnectLeft)){
                        //closed and not seeking 
                        //The test it is a hack to cut down on LED jitter, in case there is a RED ball 
                        // displaying at this end.
                        if(led.getRed() < 20) 
                            led.setRGB(4, led.getGreen(), led.getBlue());
                    } else if( isClosed && seekingConnectLeft){
                        //closed and seeking
                        int r = led.getRed();
                        r = r - 2;
                        if (r < 0) r = 20;
                        led.setRGB(r, led.getGreen(), led.getBlue());
                    } else if(!isClosed && wasClosed){
                        //was closed and just became open.
                        led.setRGB(0, led.getGreen(), led.getBlue());
                    }
                    Utils.sleep(50); 
                    wasClosed = isClosed;
                }
            }
        }.start();
    }
     
    /**
     * The right end of the LEDs is solid red if the right end is "closed" meaning
     * the SPOT is not connected to another SPOT for purposes of passing
     * the ball back and forth. This starts a thread that displays that red
     * LED at the end only if appropriate. It also causes the "pulsing" of the
     * LED during the interval between when you press the button and the 
     * SPOT establishes a connection. You can also stop the pulsing by pressing
     * either button.  
     **/
    public void startRightEndDisplay(){
        final ITriColorLED led = MyTriColorLED.getLEDs((ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class))[7];
        new Thread(){
            public void run(){
                boolean wasClosed = false;
                boolean isClosed = !isRightEndOpen();
                while(true){
                    isClosed = !isRightEndOpen();
                    if(isClosed && (! seekingConnectRight)){
                        //closed and not seeking  
                        //The test it is a hack to cut down on LED jitter, in case there is a RED ball 
                        // displaying at this end.
                        if(led.getRed() < 20) 
                            led.setRGB(4, led.getGreen(), led.getBlue());
                    } else if( isClosed && seekingConnectRight){
                        //closed and seeking
                        int r = led.getRed();
                        r = r - 2;
                        if (r < 0) r = 20; 
                        led.setRGB(r, led.getGreen(), led.getBlue());
                    } else if(!isClosed && wasClosed){
                        //was closed and just became open. 
                        led.setRGB(0, led.getGreen(), led.getBlue());
                    }
                    Utils.sleep(50); 
                    wasClosed = isClosed;
                }
                
            }
        }.start();
    }
    
    /** BASIC STARTUP CODE **/
    
    protected void startApp() throws MIDletStateChangeException {
        BootloaderListenerService.getInstance().start();       // Listen for downloads/commands over USB connection
        //setSensorBoard(EDemoBoard.getInstance());
        try {
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }
    
    /**
     * Called if the MIDlet is terminated by the system.
     * I.e. if startApp throws any exception other than MIDletStateChangeException,
     * if the isolate running the MIDlet is killed with Isolate.exit(), or
     * if VM.stopVM() is called.
     * 
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true when this method is called, the MIDlet must
     *    cleanup and release all resources. If false the MIDlet may throw
     *    MIDletStateChangeException  to indicate it does not want to be destroyed
     *    at this time.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        ITriColorLED[] leds = MyTriColorLED.getLEDs( (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class));
        for (int i = 0; i < 8; i++){
            leds[i].setOff();       // turn off all LEDs
        }
    }

}
