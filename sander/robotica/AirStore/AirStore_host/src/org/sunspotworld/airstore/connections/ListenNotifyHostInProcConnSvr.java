
/*
* Copyright (c) 2006 Sun Microsystems, Inc.
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

package org.sunspotworld.airstore.connections;
import com.sun.spot.util.Utils;

/**
 *
 * The task of serving up conncetions doesn't really exist on the host in the
 * case of the server being in the same process like this.
 *
 */
public class ListenNotifyHostInProcConnSvr implements IListenNotifyLocalConnSvr {

      private boolean hasAccepted = false;
     public IListenNotifyConnection listenaccept() {
        if (!hasAccepted) {
            hasAccepted = true;
            return new ListenNotifyHostInProcConnection();
        } else {

            Utils.sleep(Long.MAX_VALUE);

            return new ListenNotifyHostInProcConnection();
        }
    }
    public void close() {
    }
}
