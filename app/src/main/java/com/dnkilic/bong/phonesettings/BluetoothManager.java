package com.dnkilic.bong.phonesettings;

import android.bluetooth.BluetoothAdapter;

public class BluetoothManager {

    public boolean enableBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            return true;
        } else {
            return false;
        }
    }

    public boolean disableBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            return false;
        } else {
            bluetoothAdapter.disable();
            return true;
        }
    }
}
