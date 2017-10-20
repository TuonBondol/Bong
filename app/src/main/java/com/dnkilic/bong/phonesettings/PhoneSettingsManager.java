package com.dnkilic.bong.phonesettings;

import android.app.Activity;
import android.location.Address;
import android.media.AudioManager;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

import com.dnkilic.bong.ITask;
import com.dnkilic.bong.location.LocationService;
import com.dnkilic.bong.location.LocationUpdateListener;

import ai.api.model.AIResponse;

public class PhoneSettingsManager implements ITask, LocationUpdateListener {

    @Override
    public void execute(AIResponse result) {

    }

    @Override
    public void onLocationUpdateError(int error) {

    }

    @Override
    public void onLocationUpdate(Address address) {

    }
/*

    private CallSteeringResult mCallSteeringResult;
    private Activity mActivity;

    public PhoneSettingsManager(CallSteeringResult callSteeringResult, Activity activity) {
        mCallSteeringResult = callSteeringResult;
        mActivity = activity;
    }

    @Override
    public void Execute() throws Exception {
        executeSettings();
    }

    private void executeSettings() throws Exception {

        if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_INCREASE_VOLUME) {
            AudioSettings ringerModeGetter = new AudioSettings(mActivity);
            if (ringerModeGetter.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                AudioSettings audioVolumeSetter = new AudioSettings(mActivity);
                switch (audioVolumeSetter.increaseVolumeFromSilent()) {
                    case AudioSettings.MAX_AUDIO_LIMIT:
                        Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_volume_already_max))
                                .record()
                                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                                .build());
                        break;
                    case AudioSettings.MIN_AUDIO_LIMIT:
                        Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_volume_already_min))
                                .record()
                                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                                .build());
                        break;
                    case AudioSettings.SUCCESS_AUDIO_OPERATION:
                        Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                                .record()
                                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                                .build());
                        break;
                }
            } else {
                AudioSettings audioVolumeSetter = new AudioSettings(mActivity);
                switch (audioVolumeSetter.increaseVolume()) {
                    case AudioSettings.MAX_AUDIO_LIMIT:
                        Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_volume_already_max))
                                .record()
                                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                                .build());
                        break;
                    case AudioSettings.MIN_AUDIO_LIMIT:
                        Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_volume_already_min))
                                .record()
                                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                                .build());
                        break;
                    case AudioSettings.SUCCESS_AUDIO_OPERATION:
                        Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                                .record()
                                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                                .build());
                        break;
                }
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_DECREASE_VOLUME) {
            AudioSettings audioVolumeSetter = new AudioSettings(mActivity);

            switch (audioVolumeSetter.decreaseVolume()) {
                case AudioSettings.MAX_AUDIO_LIMIT:
                    Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_volume_already_max))
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                    break;
                case AudioSettings.MIN_AUDIO_LIMIT:
                    Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_volume_already_min))
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                    break;
                case AudioSettings.SUCCESS_AUDIO_OPERATION:
                    Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                    break;
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_RING_MODE_NORMAL) {
            AudioSettings ringerModeGetter = new AudioSettings(mActivity);
            if (ringerModeGetter.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_ringer_mode_already_ringer))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            } else {
                AudioSettings audioRingModeSetter = new AudioSettings(mActivity);
                audioRingModeSetter.setRingerModeAsNormal();
                Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_RING_MODE_SILENCE) {
            AudioSettings ringerModeGetter = new AudioSettings(mActivity);
            if (ringerModeGetter.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_ringer_mode_already_silent))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            } else {
                AudioSettings audioRingModeSetter = new AudioSettings(mActivity);
                audioRingModeSetter.setRingerModeAsSilent();
                Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_RING_MODE_VIBRATE) {
            AudioSettings ringerModeGetter = new AudioSettings(mActivity);
            if (ringerModeGetter.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_ringer_mode_already_vibrate))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            } else {
                AudioSettings audioRingModeSetter = new AudioSettings(mActivity);
                audioRingModeSetter.setRingerModeAsVibrate();
                Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_CHECK_BATTERY) {
            ChargeConditionChecker chargeConditionChecker = new ChargeConditionChecker();
            Announcer.makeTTS(new Announce.Builder(insertOperationDataToAnnounce(mCallSteeringResult.getAnnounceText(), " % ", chargeConditionChecker.getBatteryLevel(mActivity)))
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .build());
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_OPEN_WIFI) {
            InternetManager wifiStateManager = new InternetManager(mActivity);
            if (wifiStateManager.turnOnWifi()) {
                Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            } else {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_wifi_already_enabled)).record().addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED).build());
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_CLOSE_WIFI) {
            InternetManager wifiStateManager = new InternetManager(mActivity);
            if (wifiStateManager.isMobileDataEnabled()) {
                if (wifiStateManager.turnOffWifi()) {
                    Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                } else {
                    Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_wifi_already_disabled))
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                }
            } else {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_close_wifi_on_no_3g))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .addCommandType(Announce.CONFIRMATION_ANNOUNCE)
                        .build());
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_INCREASE_BRIGTHNESS) {
            BrightnessManager brightnessManager = new BrightnessManager(mActivity);
            try {
                if (brightnessManager.increaseBrightness()) {
                    Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                            .record()
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .build());
                } else {
                    Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_brightness_already_max))
                            .record()
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .build());
                }
            } catch (Exception e) {
                FirebaseCrash.logcat(Log.ERROR, "PhoneSettingsManager", "Exception caught");
                FirebaseCrash.report(e);
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_error_while_increasing_brightness))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                e.printStackTrace();
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_DECREASE_BRIGHTNESS) {
            BrightnessManager brightnessManager = new BrightnessManager(mActivity);
            try {
                if (brightnessManager.decreaseBrightness()) {
                    Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                            .record()
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .build());
                } else {
                    Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_brightness_already_min))
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                }
            } catch (Exception e) {
                FirebaseCrash.logcat(Log.ERROR, "PhoneSettingsManager", "Exception caught");
                FirebaseCrash.report(e);
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_error_while_decreasing_brightness))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                e.printStackTrace();
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_CHECK_LOCATION) {
            new LocationService(mActivity, this).connectLocationService();
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_OPEN_CAMERA) {
            Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                    .addCommandType(Announce.LAUNCH_ANOTHER_APP_ANNOUNCE)
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .record()
                    .build());
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_OPEN_BLUETOOTH) {
            BluetoothManager bluetoothManager = new BluetoothManager();
            if (bluetoothManager.enableBluetooth()) {
                Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            } else {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_bluetooth_already_enabled)).record().addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED).build());
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_CLOSE_BLUETOOTH) {
            BluetoothManager bluetoothManager = new BluetoothManager();
            if (bluetoothManager.disableBluetooth()) {
                Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
            } else {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_bluetooth_already_disabled)).record().addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED).build());
            }
        } else if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_POWER_SAVE_MODE) {
            BluetoothManager bluetoothManager = new BluetoothManager();
            bluetoothManager.disableBluetooth();

            BrightnessManager brightnessManager = new BrightnessManager(mActivity);
            try {
                brightnessManager.decreaseBrightness();
            } catch (SettingNotFoundException e) {
                FirebaseCrash.logcat(Log.ERROR, "PhoneSettingsManager", "Exception caught");
                FirebaseCrash.report(e);
                e.printStackTrace();
            }

            InternetManager wifiStateManager = new InternetManager(mActivity);
            wifiStateManager.turnOffWifi();

            Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                    .record()
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .build());
        }
    }

    private String insertOperationDataToAnnounce(String announce, String operator, String operationResult) {
        return announce + operator + operationResult;
    }

    @Override
    public void ExecuteWithAdditionalValues(String dictation) throws Exception {
    }

    @Override
    public void ExecuteFromOtherApplication() throws Exception {
        if (mCallSteeringResult.getCommandType() == CommandType.SETTINGS_OPEN_CAMERA) {
            CameraManager cameraManager = new CameraManager(mActivity);
            cameraManager.launchCamera();
        }
    }

    @Override
    public void ExecuteWithConfirmationTreeDecision(int confirmationStatus) throws Exception {

        switch (confirmationStatus) {
            case ConfirmationStatus.CONFIRMED:
                InternetManager wifiStateManager = new InternetManager(mActivity);
                if (wifiStateManager.turnOffWifi()) {

                } else {
                    Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_wifi_already_disabled))
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                }
                break;
            case ConfirmationStatus.NOT_CONFIRMED:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_confirmation_tree_exact_match_no))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case ConfirmationStatus.NULL:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_phone_settings_manager_close_wifi_on_no_3g))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .addCommandType(Announce.CONFIRMATION_ANNOUNCE)
                        .build());
                break;
        }
    }

    @Override
    public void onLocationUpdateError(int error) {
        switch (error) {
            case LocationService.LOCATION_SERVICE_ERROR:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_location_service_error_while_getting_location))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case LocationService.LOCATION_SERVICE_NOT_AVAILABLE:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_location_service_is_not_available))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
        }
    }

    @Override
    public void onLocationUpdate(Address address) {

        String fullAddress = "";
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            fullAddress = fullAddress + " " + address.getAddressLine(i);
        }

        Announcer.makeTTS(new Announce.Builder(insertOperationDataToAnnounce(mCallSteeringResult.getAnnounceText(), " ", fullAddress))
                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                .record()
                .build());
    }
*/
}
