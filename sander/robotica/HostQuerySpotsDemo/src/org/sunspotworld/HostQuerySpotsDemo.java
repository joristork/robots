/*
 * Copyright (c) 2010 Oracle.
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

package org.sunspotworld;

import com.sun.spot.client.DummySpotClientUI;
import com.sun.spot.client.IUI;
import com.sun.spot.client.SpotClientCommands;
import com.sun.spot.client.SpotClientException;
import com.sun.spot.client.command.GetPowerStatsCmd;
import com.sun.spot.client.command.HelloResult;
import com.sun.spot.client.command.HelloResultList;
import com.sun.spot.peripheral.ota.ISpotAdminConstants;
import com.sun.spot.util.IEEEAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties; 

/**
 * Sample Sun SPOT client application.
 *
 * This application demonstrates how to locate nearby SPOTs and then send them
 * commands over-the-air (OTA).
 *
 * Please refer to the section "Using the SPOT Client" in the SPOT Developer's
 * Guide for details. Also refer to the hostjavadoc in the SDK/doc folder.
 *
 */
public class HostQuerySpotsDemo {

    // parameters needed by SpotClientCommands constructor
    private IUI ui = new DummySpotClientUI(false);
    private String pathToApp = ".";
    private String commPort = System.getProperty("SERIAL_PORT");    // set by host-run script
    private int echoPort = ISpotAdminConstants.MASTER_ISOLATE_ECHO_PORT;
    private File sunspotArmDir;
    private String libPath;
    private String keystorePath;

    public HostQuerySpotsDemo() throws IOException {
        Properties p = new Properties();
        String userHome = System.getProperty("user.home");
        p.load(new FileInputStream(new File(userHome, ".sunspot.properties")));
        String sunspotHome = p.getProperty("sunspot.home");
        sunspotArmDir = new File(sunspotHome, "arm");
        libPath = sunspotHome + File.separator + "arm" +
                File.separator + p.getProperty("spot.library.name");
        keystorePath = userHome + File.separator + "sunspotkeystore";
    }

    /**
     * Discover any nearby SPOTs and send them some OTA commands.
     * 
     * @throws IOException
     */
    public void discover() throws IOException {

        SpotClientCommands broadcastCommandRepository = new SpotClientCommands(
                ui,
                pathToApp,
                libPath,
                sunspotArmDir,
                keystorePath,
                commPort,
                null,
                echoPort);

        System.out.println("Sending Hello command...");
        HelloResultList helloList = (HelloResultList)broadcastCommandRepository.execute("hello", "5000", "8", "broadcast", null);
        System.out.println("Hello details: " + helloList.toString());

        // now send some OTA commands to all the SPOTs we just found
        int cnt = 1;
        for (Object w : helloList) {
            HelloResult who = (HelloResult) w;
            if (who.isSpot()) {
                String remoteAddress = IEEEAddress.toDottedHex(who.remoteAddress);
                System.out.println("\n\n" + (cnt++) + ": " + remoteAddress);
                sendOTAcommands(remoteAddress);
            }
        }
    }

    /**
     * Send some OTA commands to the specified SPOT.
     * In particular blink the SPOT's LEDs & then get its power statistics.
     *
     * @param remoteAddress IEEE address of SPOT to send OTA commands
     */
    public void sendOTAcommands(String remoteAddress) {

        SpotClientCommands commandRepository = null;
        try {
            commandRepository = new SpotClientCommands(
                    ui,
                    pathToApp,
                    libPath,
                    sunspotArmDir,
                    keystorePath,
                    commPort,
                    remoteAddress,
                    echoPort);
        } catch (IOException ex) {
            System.out.println("Error opening OTA connection to " + remoteAddress);
            return;
        }

        try {
            commandRepository.execute("synchronize");   // all OTA sessions start with a sync
            commandRepository.execute("blink", 5);      // blink the SPOT's LEDs
            GetPowerStatsCmd.Result powerStats =        // get power statistics from SPOT
                    (GetPowerStatsCmd.Result)commandRepository.execute("getpowerstats");
            System.out.println(powerStats.toString());
            System.out.println("\nBattery level = " + powerStats.getBatteryLevel() + "%");
        } catch (IOException iex) {
            System.out.println("Error executing remote command: " + iex);
        } catch (SpotClientException ex) {
            System.out.println("Error executing remote command: " + ex);
        } finally {
            try {
                commandRepository.execute("quit");      // all OTA sessions end with a quit
            } catch (Exception ex) {
                System.out.println("Error ending session: " + ex);
            }
        }
    }

    /**
     * Start up the host application.
     */
    public static void main(String[] args) throws Exception {
        HostQuerySpotsDemo app = new HostQuerySpotsDemo();
        app.discover();
        System.exit(0);
    }
}
