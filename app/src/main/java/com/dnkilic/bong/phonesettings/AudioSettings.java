package com.dnkilic.bong.phonesettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;

public class AudioSettings {

    public static final int SUCCESS_AUDIO_OPERATION = 0;
    public static final int MAX_AUDIO_LIMIT = 1;
    public static final int MIN_AUDIO_LIMIT = 2;

    private Activity mActivity;

    public AudioSettings(Activity activity) {
        mActivity = activity;
    }

    public int increaseVolume() {

        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);

        long systemVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        long ringVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        long musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        long maxSystemVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        long maxRingVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        long maxMusicVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (systemVolume == maxSystemVolume && ringVolume == maxRingVolume && musicVolume == maxMusicVolume) {
            return AudioSettings.MAX_AUDIO_LIMIT;
        }

        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

        return AudioSettings.SUCCESS_AUDIO_OPERATION;
    }

    public int decreaseVolume() {
        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);

        long systemVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        long ringVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        long musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (systemVolume == 0 && ringVolume == 0 && musicVolume == 0) {
            return AudioSettings.MIN_AUDIO_LIMIT;
        }

        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

        return AudioSettings.SUCCESS_AUDIO_OPERATION;
    }

    public int getRingerMode() {
        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);

        int ringerMode = 2;

        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                ringerMode = AudioManager.RINGER_MODE_SILENT;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                ringerMode = AudioManager.RINGER_MODE_VIBRATE;
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                ringerMode = AudioManager.RINGER_MODE_NORMAL;
                break;
        }
        return ringerMode;
    }

    public void setRingerModeAsVibrate() {
        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    public void setRingerModeAsSilent() {
        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public void setRingerModeAsNormal() {
        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public int increaseVolumeFromSilent() {

        AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);

        long systemVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        long ringVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        long musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        long maxSystemVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        long maxRingVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        long maxMusicVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (systemVolume == maxSystemVolume && ringVolume == maxRingVolume && musicVolume == maxMusicVolume) {
            return AudioSettings.MAX_AUDIO_LIMIT;
        }

        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

        return AudioSettings.SUCCESS_AUDIO_OPERATION;
    }
}
