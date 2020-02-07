package com.utilities;

import android.os.Build;

import java.util.UUID;

/**
 * Created by bitu on 31/8/17.
 */

public class DeviceUtils {

    public static String getUniqueDeviceId () {

        String m_szDevIDShort = "35"
                + (Build.BOARD.length() % 10)
                + (Build.BRAND.length() % 10)
                + (Build.CPU_ABI.length() % 10)
                + (Build.DEVICE.length() % 10)
                + (Build.MANUFACTURER.length() % 10)
                + (Build.MODEL.length() % 10)
                + (Build.PRODUCT.length() % 10);


        String serial = null;
        try {
            serial = Build.class.getField(Build.SERIAL).get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }

        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

}
