package com.dnkilic.bong.player;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

public class NotificationPlayer {

    private Activity mAct;
    private Uri mSelectedMusicUri;
    private MediaPlayer mPlayer = null;

    public NotificationPlayer(Activity activity, Uri uri) {
        mAct = activity;
        mSelectedMusicUri = uri;
    }

    public void startNotification(MediaPlayer.OnCompletionListener listener, MediaPlayer.OnErrorListener errorListener)
    {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
        mPlayer.setOnCompletionListener(listener);
        mPlayer.setOnErrorListener(errorListener);

        try {
            mPlayer.setDataSource(mAct, mSelectedMusicUri);
            mPlayer.prepare();
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        if (mPlayer != null) {
            mPlayer.release();
        }
    }
}
