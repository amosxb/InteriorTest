package com.ys.administrator.ysinteriortest.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by amos on 2017/7/27.
 */

public class TestUtile {


    public static String getAndroidOsSystemProperties(String key) {
        String ret;
        try {
            java.lang.reflect.Method	systemProperties_get = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            if ((ret = (String) systemProperties_get.invoke(null, key)) != null)
                return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return "";
    }

    /**
     * 获取手机的MAC
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();

    }
}
