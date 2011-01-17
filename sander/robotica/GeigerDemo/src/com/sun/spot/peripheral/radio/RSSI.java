/*
 */

package com.sun.spot.peripheral.radio;

/**
 *
 * @author Ron Goldman
 */
public class RSSI {

    private static final int RSSI_REG = 0x13;

    public static int getRSSI(I802_15_4_PHY i802phyRadio) {
        return (byte)(((CC2420)i802phyRadio).getRegValue(RSSI_REG) & 0xFF);
    }

}
