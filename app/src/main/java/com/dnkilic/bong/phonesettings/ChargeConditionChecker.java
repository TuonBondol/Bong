package com.dnkilic.bong.phonesettings;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class ChargeConditionChecker {

    public String getBatteryLevel(Activity activity) {
        Intent batteryIntent = activity.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);


        if (level == -1 || scale == -1) {
            return Integer.toString(50);
        }

        Double batteryLevel = (double) level / scale * 100;

        return String.valueOf(batteryLevel.intValue());
    }
}