
package org.sunspotworld.demo;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import com.sun.spot.wot.WebApplication;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.utils.Base64;

/**
 *
 * @author vgupta
 */
public class HOLLEDController extends WebApplication {
    private ITriColorLEDArray myLEDs = (ITriColorLEDArray)
            Resources.lookup(ITriColorLEDArray.class);

    public void init() {
        if (myLEDs == null) {
            myLEDs = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        }
        for (int i = 0; i < myLEDs.size(); i++) {
            myLEDs.getLED(i).setRGB(0,0,0);
            myLEDs.getLED(i).setOff();                // turn off all LEDs
        }
    }

    public HOLLEDController(String str) {
        super(str);
    }

    public HttpResponse processRequest(HttpRequest request) throws IOException {
        String respStr = "[]";
        HttpResponse response = new HttpResponse();

        // We assume all LEDs are the same color, we look at LED 4 ...
        if (request.getMethod().equalsIgnoreCase("GET")) {
            /*
             * Send a JSON string with timestamp and r, g, b values in
             * response to a GET.
             */
            respStr = "[" + myLEDs.getLED(4).getRed() + "," +
                        myLEDs.getLED(4).getGreen() + "," +
                        myLEDs.getLED(4).getBlue() + "]\n";

            response.setStatus(HttpResponse.SC_OK);
            response.setHeader("Content-Type", "text/plain");
            // Note: Allow this to be cached for 20 sec to reduce gateway load
            response.setHeader("Cache-Control", "max-age=20");
            response.setBody(respStr.getBytes());
            return response;
        } else if (request.getMethod().equalsIgnoreCase("PUT")) {
            int rgb[] = new int[3]; // integers are automatically initialized to 0
            String val = "";

            try {
                // We expect the POSTed data to be in the format [r,g,b]
                // We first remove the square brackets and then split the rgb
                // string using coma as separator. We check for three integers,
                // each in the range 0-255. Otherwise, we complain.
                String rgbStr = new String(request.getBody());
                System.out.println("Received rgb string: " + rgbStr);
                // The next two lines remove the leading [ and trailing ] if present
                if (rgbStr.startsWith("[")) rgbStr = rgbStr.substring(1);
                if (rgbStr.endsWith("]")) rgbStr = rgbStr.substring(0, rgbStr.length() - 1);
                System.out.println("New rgb string: " + rgbStr);
                StringTokenizer st = new StringTokenizer(rgbStr, ",");
                if (st.countTokens() != 3) {
                    throw new IOException("Incorrect number of integers.");
                }

                for (int i = 0; i < 3; i++) {
                    val = st.nextToken();
                    rgb[i] = Integer.parseInt(val);
                    if (rgb[i] < 0 || rgb[i] > 255) {
                        throw new IOException("Incorrect integer range.");
                    }
                }
            } catch (Exception e) {
                response.setStatus(HttpResponse.SC_BAD_REQUEST);
                response.setBody(("Error: Need input in " +
                        "form [r,g,b] each 0-255").getBytes());
                return response;
            }

            // We use validated r,g,b values to set the color of all the LEDs
            for (int i = 0; i < myLEDs.size(); i++) {
                myLEDs.getLED(i).setRGB(rgb[0], rgb[1], rgb[2]);
                myLEDs.getLED(i).setOn();
            }
           
            response.setStatus(HttpResponse.SC_OK);
            return response;
        }

        // We return an error for HTTP methods other than GET, PUT
        response.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
        response.setHeader("Allow", "GET,PUT");

        return response;
    }

    /*
     * We only support HTTP Basic authentication which
     * sends a Base-64 encoding of "username:password" in the
     * "Authorization" header.
     *
     * General form:
     * Authorization: <authType> <authToken>
     *
     * Example:
     * Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     * ...
     */
    public boolean isAuthorized(HttpRequest req) {
        /* XXX Uncomment the following lines to authenticate PUTs.
         * Replace "Ali Baba" with the authorized username and
         * "open sesame" with the authorized password.
         */
//        String authorizedUser = "Ali Baba";
//        String authorizedPassword = "open sesame";
//
//        if (req.getMethod().equalsIgnoreCase("PUT")) {
//            String auth = req.getHeader("Authorization");
//
//            return ((auth != null) &&
//                    auth.equalsIgnoreCase("Basic " +
//                    Base64.encode(authorizedUser + ":" + authorizedPassword)));
//        }

        return true; // GETs should still succeed
    }
}

