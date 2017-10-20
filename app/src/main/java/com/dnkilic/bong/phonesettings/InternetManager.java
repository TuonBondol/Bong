package com.dnkilic.bong.phonesettings;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class InternetManager {

    private Activity mActivity;

    public InternetManager(Activity activity) {
        mActivity = activity;
    }

    public Boolean isMobileDataEnabled() {

        boolean isDataEnabled;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isSimSupport()) {
                isDataEnabled = Settings.Secure.getInt(mActivity.getContentResolver(), "mobile_data", 0) == 1;
            } else {
                isDataEnabled = false;
            }
        } else {
            if (((TelephonyManager) mActivity.getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE))
                    .getSubscriberId() == null) {
                isDataEnabled = false;
            } else {
                Object connectivityService = mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
                ConnectivityManager cm = (ConnectivityManager) connectivityService;
                try {
                    Class<?> c = Class.forName(cm.getClass().getName());
                    Method m = c.getDeclaredMethod("getMobileDataEnabled");
                    m.setAccessible(true);
                    isDataEnabled = (Boolean) m.invoke(cm);
                } catch (Exception e) {
                    e.printStackTrace();
                    isDataEnabled = false;
                }
            }
        }

        return isDataEnabled;
    }

    private boolean isSimSupport() {
        TelephonyManager tm = (TelephonyManager) mActivity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);
    }



    public boolean turnOffWifi() {
        WifiManager wifiManager = (WifiManager) mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean turnOnWifi() {
        WifiManager wifiManager = (WifiManager) mActivity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);

            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }
}
