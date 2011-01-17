/*
 * IRobotCreateDemo.java
 *
 * Copyright (c) 2009 Sun Microsystems, Inc.
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
 */

package org.sunspotworld.demo;

import org.sunspotworld.create.IRobotCreate;
import org.sunspotworld.create.IBumpListener;
import org.sunspotworld.create.IWallListener;
import org.sunspotworld.create.Create;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.util.Utils;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * A simple demo to bounce an emulated Create robot off the walls
 *
 * @author Ron Goldman

 */
public class IRobotCreateDemo extends MIDlet implements IBumpListener {

    private IRobotCreate create = Create.makeIRobotCreate();

    // please position the Create just above the bottom wall,
    // pointing to the right
    private void wall() {
        create.stop();
        int h = create.getHeading();
        System.out.println("Old heading = " + h);
        if (h != 90) {
            if (h < 90 || h > 270) {
                create.driveDirect(-30, 30);
                create.waitAngle(Math.abs(90 - h));
                create.stop();
            } else {
                create.driveDirect(30, -30);
                create.waitAngle(Math.abs(90 - h));
                create.stop();
            }
        }
        System.out.println("\nHere we go again");
        while (!create.isBumpRight()) {
            if (create.isWall()) {
                System.out.println("Too close");
                create.driveDirect(210, 200);
            } else {
                System.out.println("Too far");
                create.driveDirect(200, 210);
            }
            Utils.sleep(200);
        }
        create.stop();

    }

    // turn to right by specified # degrees (must be > 0)
    private void turn(int deg) {
        int start = create.getAccumulatedAngle();
        create.driveDirect(200, -200);
        create.waitAngle(deg - 10);
        create.stop();
        int traveled = create.getAccumulatedAngle() - start;
        if (Math.abs(traveled) < deg) {
            create.driveDirect(20, -20);
            create.waitAngle(deg - Math.abs(traveled));
            create.stop();
            traveled = create.getAccumulatedAngle() - start;
        }
        if (Math.abs(traveled) > deg) {
            create.driveDirect(-10, 10);
            create.waitAngle(Math.abs(traveled) - deg);
            create.stop();
        }
    }

    private void centerLine() {
        create.driveDirect(300, 300);
        create.waitBump();
        turn(180);
        int dStart = create.getAccumulatedDistance();
        create.driveDirect(500, 500);
        create.waitBump();
        create.stop();
        int reStart = create.getAccumulatedDistance();
        int dist = Math.abs(reStart - dStart) / 2;
        create.driveDirect(-500, -500);
        create.waitDistance(dist - 20);
        create.stop();
        int traveled = create.getAccumulatedDistance() - reStart;
        if (Math.abs(traveled) < dist) {
            create.driveDirect(-50, -50);
            create.waitDistance(dist - Math.abs(traveled));
            create.stop();
            traveled = create.getAccumulatedDistance() - reStart;
        }
        if (Math.abs(traveled) > dist) {
            create.driveDirect(20, 20);
            create.waitDistance(Math.abs(traveled) - dist);
            create.stop();
        }
    }

    private void center() {
        centerLine();
        turn(90);
        centerLine();
    }

    public void bumpEvent(boolean bumpRight, boolean bumpLeft, boolean oldRight, boolean oldLeft) {
        boolean bumped = false;
        if (bumpRight && !oldRight) {
            bumped = true;
            System.out.print("Right Bump!!!   ");
        }
        if (bumpLeft && !oldLeft) {
            bumped = true;
            System.out.print("Left Bump!!!");
        }
        if (bumped) {
            System.out.println("");
        }
    }

    private String beaconInfo() {
        int beacon = create.getInfraredByte();
        String result;
        if (beacon == IRobotCreate.INFRARED_BYTE_BEACON_X) {
            result = "X";
        } else if (beacon == IRobotCreate.INFRARED_BYTE_BEACON_O) {
            result = "O";
        } else {
            result = "no beacon present";
        }
        return result;
    }

    protected void startApp() throws MIDletStateChangeException {
        System.out.println("Hello, world");
        create.addIBumpListener(this);
        create.getHeading();
        System.out.println("Current beacon = " + beaconInfo());
        center();
//        wall();
        System.out.println("Goodbye");
        notifyDestroyed();
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
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
    }
}
