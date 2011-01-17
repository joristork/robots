/*
 * MySmallJetty.java
 *
 * Created on Feb 26, 2009 1:24:40 PM;
 */

package org.sunspotworld;

import java.io.File;
import java.net.BindException;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.HashUserRealm;
import org.mortbay.jetty.security.UserRealm;
import org.mortbay.jetty.webapp.WebAppContext;


/**
 * Sample Sun SPOT host application
 */
public class MySmallJetty {
    private static int SERVER_PORT = 8080;
    /**
     * Print out our radio address.
     */
    public void run() {
        System.out.println("*************************************************");
        System.out.println("Launching jetty on port " + SERVER_PORT + " ...");
        System.out.println("After the server is running, point your " +
                "browser to\n\thttp://localhost:" + SERVER_PORT + "/index.jsp");
        System.out.println("Log in as demo/demo when prompted.");
        System.out.println("*************************************************");
        try {
            //System.out.println("jetty.home=" + System.getProperty("jetty.home"));
            Server server = new Server();

            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(SERVER_PORT);
            org.mortbay.jetty.Connector[] conns = {
                (org.mortbay.jetty.Connector) connector,
            };

            conns[0] = connector;
            server.setConnectors(conns);

            String jetty_home = ".";
            WebAppContext webapp1 = new WebAppContext();
            webapp1.setContextPath("/"); // "/spotweb"
            webapp1.setWar(jetty_home + File.separator + "webapps" +
                    File.separator + "SPOTWeb.war");
           // webapp1.setDefaultsDescriptor(jetty_home + "/etc/webdefault.xml"); // this is optional
            //server.setHandler(webapp1);
            
            //WebAppContext webapp2 = new WebAppContext();
            //webapp2.setContextPath("/SPOTNet");
            //webapp2.setWar(jetty_home + "/webapps/SPOTNet.war");

            WebAppContext[] wacs = { webapp1 }; // { webapp1, webapp2 };

            server.setHandlers(wacs);

            HashUserRealm hur = new HashUserRealm();
            hur.setName("Test Realm");
            hur.setConfig(jetty_home + File.separator + "etc" +
                    File.separator + "realm.properties");
            hur.setRefreshInterval(0);
            UserRealm[] realms = {
                hur,
            };
            server.setUserRealms(realms);

            server.start();
            server.join();

        } catch (BindException e) {
            System.out.println(
                    " *** Another server appears to be running at port " + SERVER_PORT + ".        ***\n" +
                    " *** Either shut down that server and restart this application ***\n" +
                    " *** or modify SERVER_PORT to use an unsused port number.      ***");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Caught " + e);
        }
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        MySmallJetty app = new MySmallJetty();
        app.run();
    }
}
