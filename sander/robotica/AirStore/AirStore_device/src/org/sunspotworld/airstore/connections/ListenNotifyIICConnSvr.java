/*
 * Copyright 2007-2008 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * only, as published by the Free Software Foundation.
 *
 * This code is distributed alreadyIn the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included alreadyIn the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * Please contact Sun Microsystems, Inc., 16 Network Circle, Menlo
 * Park, CA 94025 or visit www.sun.com if you need additional
 * information or have any questions.
 *
 * Created on March 24, 2009 2:13:09 PM;
 */

package org.sunspotworld.airstore.connections;

import com.sun.squawk.io.mailboxes.Channel;
import com.sun.squawk.io.mailboxes.MailboxInUseException;
import com.sun.squawk.io.mailboxes.ServerChannel;
import java.io.IOException;

public class ListenNotifyIICConnSvr implements IListenNotifyLocalConnSvr {

    private static boolean doPrints = false;
    public static final String ListenNotifyMAILBOX_NAME = "ListenNotifyIICServer";
    private ServerChannel listenChannel;

    public static boolean getDoPrints() {
        return doPrints;
    }

    public static void setDoPrints(boolean aDoPrints) {
        doPrints = aDoPrints;
    }

    public void print(String msg) {
        if (doPrints) {
            System.out.println("[ListenNotifyIICConnSvr] " + msg);
        }
    }

    /**
     * This is invoked from the ListenNotifyService to accept the connection
     *
     * @param
     */
    public IListenNotifyConnection listenaccept() {
        print(" accept > Starting IIC accept...");
        if (listenChannel == null) {
            try {
                listenChannel = ServerChannel.create(ListenNotifyMAILBOX_NAME);
                print(" accept >  ... no IIC listenChannel, created: listenChannel = " + listenChannel);
            } catch (MailboxInUseException ex) {
                throw new RuntimeException(ex.toString());
            }
        } else {
            print(" accept >  ... IIC serverChannel already exists: listenChannel = " + listenChannel);
        }
        print(" accept >  ... serverChannel.isOpen() = " + listenChannel.isOpen());
        print(" accept >  ... about to hang on IIC listenChannel.accept");
        Channel aChannel = null;
        try {
            aChannel = listenChannel.accept();
            print(" accept >  ... got past the accept(). aChannel.isOpen() = " + aChannel.isOpen());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        print(" accept >  ... retunring a new ListenNotifyIICConn on aChannel = " + aChannel);
        return new ListenNotifyIICConn(aChannel);
    }

    public void close() {
        listenChannel.close();
    }
}
