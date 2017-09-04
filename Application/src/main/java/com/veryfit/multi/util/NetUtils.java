package com.veryfit.multi.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetUtils {
    private NetUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm != null && cm.getActiveNetworkInfo().getType() == 1) {
            return true;
        }
        return false;
    }

    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
