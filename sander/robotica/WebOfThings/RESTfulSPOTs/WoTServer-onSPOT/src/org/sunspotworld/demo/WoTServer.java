/*
 * WoTServer.java
 *
 * Created on Apr 23, 2010 4:43:14 PM;
 */

package org.sunspotworld.demo;

import com.sun.spot.wot.NanoAppServer;
import com.sun.spot.wot.TCP6Handler;
import com.sun.spot.wot.UDP6Handler;
import com.sun.spot.peripheral.ISleepManager;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class WoTServer extends MIDlet {
    protected void startApp() throws MIDletStateChangeException {

        try {
            // Deep sleep is disabled for this exercise so the SPOTs are
            // always reachable from the Gateway
            System.out.println("***** Disabling deep sleep *****");
            ISleepManager sleepManager = Spot.getInstance().getSleepManager();
            sleepManager.disableDeepSleep();
            // This will make sure the SPOT is configured as an ENDNODE the next time it is rebooted.
            Spot.getInstance().setPersistentProperty("spot.mesh.routing.enable", "ENDNODE");

            // We create an instance of the NanoAppServer and register
            // web applications mapped to specific parts of the server's
            // URL space
            NanoAppServer nas = new NanoAppServer();

            // Pre-existing services include the following ...
            // ... this one returns status information (e.g. battery, uptime)
            nas.registerApp("/status", new Status("n=Status\nsh=s\nd=http://bit.ly/8YtWQE"));
            // ... this one can be used to read/create/modify SPOT properties
            // We'll use this to set the spot.name property which assigns
            // a name to the SPOT. Note that one can associate meta information
            // with a Web Application by passing a string of Java properties
            // Here, the name is "Properties manager", the short URL is p
            // i.e. the application can be accessed at /props as well as /p,
            // and the URL for the description is http://bit.ly/aa8iOZ
            nas.registerApp("/props", new PropertiesManager("n=Properties\nsh=p\nd=http://bit.ly/aa8iOZ"));
            // ... this one can be used to blink LEDs (useful to physically
            // locate a SPOT with known Id)
            nas.registerApp("/blink", new Blinker("n=Blink LEDs\nsh=b\nd=http://bit.ly/bPAzq1"));
            // ... this one shows total and available memory
            nas.registerApp("/mem", new MemorySensor("n=Access Memory information\nsh=m\nd=http://bit.ly/boIMfV"));
            // For this hands-on-lab, we will create two additional web
            // applications. The first will allow us to access the light sensor 
            // reading using an HTTP GET. The second will allow us to retrieve 
            // and monitor the LED settings via HTTP GETs and PUTs.
            nas.registerApp("/light", new HOLLightSensor("n=Light sensor\nd=http://bit.ly/aLqyNA"));
            /*
             * XXX Replace xxxx in the realm specification with the last 4 hex digits of your SPOT
             */
            nas.registerApp("/leds", new HOLLEDController("n=LED control\nd=http://bit.ly/9C3rlP\nrealm=spot-xxxx"));

            if (WoTConstants.SVC_CONNECTION_TYPE.equals("udp")) {
                new UDP6Handler(WoTConstants.TCP_UDP_SVCPORT, nas).start();
            } else {
                new TCP6Handler(WoTConstants.TCP_UDP_SVCPORT, nas).start();
            }

            System.out.println("Registering switch listener ...");
            ISwitch sw = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
            sw.addISwitchListener(new SwitchListener());

            if (WoTConstants.AUTO_DISCOVERY) {
                DiscoveryHandler dh = new DiscoveryHandler();
                dh.start();
                // XXX commented out for emulator
                // System.out.println("Adding deep sleep listener ...");
                // Spot.getInstance().addDeepSleepListener(dh);
            } else {
                System.out.println("***** Autodiscovery is turned off *****");
            }
            //new SPOTRHTTPHandler("http://sensor.network.com:1234/warn", nas).start();
            
            // *** Do not use these for the Hands-on-lab at JavaOne 2010 ***
            // and a few other to access temperature, energy, memory and app
            // information
//            nas.registerApp("/temp", new TempSensor("n=Access temperature readings\nsh=t\nd=http://bit.ly/azWwbW"));
//            nas.registerApp("/energy", new EnergySensor("n=Access energy information\nd=http://bit.ly/9hA5Ai"));
//            nas.registerApp("/apps", new AppManager("n=Application manager\nd=http://bit.ly/b7Jdrv"));
//            nas.setDefaultApp(new DefaultApp("n=Default app"));
//          // This is the original app for LED control (finer grained)  
//            nas.registerApp("/leds", new LEDController("n=Monitor/control LEDs\nd=http://bit.ly/9C3rlP\nrealm=LEDLord"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
