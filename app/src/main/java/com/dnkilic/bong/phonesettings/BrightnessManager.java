package com.dnkilic.bong.phonesettings;

import android.app.Activity;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

public class BrightnessManager {

    private Activity mActivity;

    public BrightnessManager(Activity activity) {
        mActivity = activity;
    }

    public boolean decreaseBrightness() throws SettingNotFoundException {
        int curBrightnessValue = android.provider.Settings.System.getInt(mActivity.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(mActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0);

        if (curBrightnessValue == 0) {
            return false;
        } else if (curBrightnessValue > 50) {
            Settings.System.putInt(mActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, curBrightnessValue - 50);
            return true;
        } else {
            Settings.System.putInt(mActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
            return true;
        }
    }

    public boolean increaseBrightness() throws SettingNotFoundException {
        int curBrightnessValue = android.provider.Settings.System.getInt(mActivity.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(mActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0);

        if (curBrightnessValue == 255) {
            return false;
        } else if (curBrightnessValue < 205) {
            Settings.System.putInt(mActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, curBrightnessValue + 50);
            return true;
        } else {
            Settings.System.putInt(mActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
            return true;
        }
    }
}
