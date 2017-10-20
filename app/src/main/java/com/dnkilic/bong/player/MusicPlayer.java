package com.dnkilic.bong.player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class MusicPlayer extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    public static final String ACTION_START = "com.dnkilic.dplayer.INITIALIZE";
    public static final String ACTION_PLAY = "com.dnkilic.dplayer.PLAY";
    public static final String ACTION_STOP = "com.dnkilic.dplayer.STOP";
    public static final String ACTION_PAUSE = "com.dnkilic.dplayer.PAUSE";
    public static final String ACTION_FORWARD = "com.dnkilic.dplayer.FORWARD";
    public static final String ACTION_BACKWARD = "com.dnkilic.dplayer.BACKWARD";
    public static final String ACTION_FORWARD_LONG = "com.dnkilic.dplayer.FORWARD_LONG";
    public static final String ACTION_BACKWARD_LONG = "com.dnkilic.dplayer.BACKWARD_LONG";
    public static final String ACTION_START_RADIO = "com.dnkilic.dplayer.RADIO_START";
    public static final String ACTION_START_LIBRARY = "com.dnkilic.dplayer.LIBRARY_START";

    public static final int REFRESH_UI_STOP = 1;
    public static final int REFRESH_UI_PAUSE = 2;
    public static final int REFRESH_UI_PLAY = 3;

    private static final int CLICK_IN_MILLIS = 5000;
    private static final int LONG_CLICK_IN_MILLIS = 10000;

    private String mDataSource;
    private int mCurrentStreamType = -1;

    private MediaPlayer mPlayer = null;
    private int mCurrentPosition;
    private Uri mSelectedMusicUri;
    private String mediaName;
    private Notification notification;
    private Messenger messageHandler;

    public MusicPlayer() {
    }

    @Override
    public void onCreate() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_STOP);
        intentFilter.addAction(ACTION_PAUSE);
        intentFilter.addAction(ACTION_PLAY);
        intentFilter.addAction(ACTION_FORWARD);
        intentFilter.addAction(ACTION_BACKWARD);
        intentFilter.addAction(ACTION_START_RADIO);
        intentFilter.addAction(ACTION_START_LIBRARY);
        registerReceiver(broadcastReceiver, intentFilter);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {

            if (intent.getAction() != null) {
                if (intent.getAction().equals(ACTION_START)) {
                    mSelectedMusicUri = intent.getData();
                    mediaName = intent.getStringExtra("TITLE");
                    messageHandler = (Messenger) intent.getExtras().get("MESSENGER");

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Service", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("lastStreamType", Media.MUSIC);
                    editor.apply();

                    startPlaying();
                }
                else if(intent.getAction().equals(ACTION_STOP)) {

                    SharedPreferences sharedPref = getSharedPreferences("Service", Context.MODE_PRIVATE);
                    int lastStreamType = sharedPref.getInt("lastStreamType", 0);

                    if(lastStreamType == Media.STORY_READER) {
                        mCurrentStreamType = Media.STORY_READER;
                        stopLibrary();
                    }
                    else {
                        stopPlaying();
                    }
                }
                else if(intent.getAction().equals(ACTION_PAUSE)) {
                    //TODO
                    pausePlaying();
                }
                else if(intent.getAction().equals(ACTION_PLAY)) {
                    //TODO
                    resumePlaying();
                }
                else if(intent.getAction().equals(ACTION_FORWARD)) {
                    forwardPlaying(false);
                }
                else if(intent.getAction().equals(ACTION_BACKWARD)) {
                    backwardPlaying(false);
                }
                else if(intent.getAction().equals(ACTION_START_RADIO)) {
                    mSelectedMusicUri = intent.getData();
                    mDataSource = mSelectedMusicUri.toString();
                    mediaName = intent.getStringExtra("TITLE");
                    messageHandler = (Messenger) intent.getExtras().get("MESSENGER");

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Service", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("lastStreamType", Media.RADIO);
                    editor.apply();

                    mCurrentStreamType = Media.RADIO;

                    startRadio();
                }
                else if(intent.getAction().equals(ACTION_START_LIBRARY)) {
                    mCurrentStreamType = Media.STORY_READER;
                    mSelectedMusicUri = intent.getData();
                    mDataSource = mSelectedMusicUri.toString();
                    mediaName = intent.getStringExtra("TITLE");
                    messageHandler = (Messenger) intent.getExtras().get("MESSENGER");
                    mCurrentPosition = intent.getIntExtra("CURRENT_POSITION", 0);

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Service", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("lastBook", mediaName);
                    editor.putInt("lastStreamType", Media.STORY_READER);
                    editor.apply();

                    startLibrary();
                }
            }
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendMessage() {

        try {
            Message message = Message.obtain();
            message.arg1 = REFRESH_UI_STOP;
            messageHandler.send(message);
        } catch (RemoteException e) {
            stopSelf();
            e.printStackTrace();
        }
    }

    public void onPlay(String state) {
        switch (state) {
            case ACTION_FORWARD_LONG:
                forwardPlaying(true);
                break;
            case ACTION_BACKWARD_LONG:
                backwardPlaying(true);
                break;
        }
    }

    private void forwardPlaying(boolean isLongClick)
    {
        if (mPlayer != null) {
            try {
                mCurrentPosition = mPlayer.getCurrentPosition();

                int duration = mPlayer.getDuration();
                if(isLongClick)
                {
                    if(duration - mCurrentPosition < LONG_CLICK_IN_MILLIS)
                    {
                        mPlayer.seekTo(duration);
                    }
                    else
                    {
                        mPlayer.seekTo(mCurrentPosition + LONG_CLICK_IN_MILLIS);
                    }
                }
                else
                {
                    if(duration - mCurrentPosition < CLICK_IN_MILLIS)
                    {
                        mPlayer.seekTo(duration);
                    }
                    else
                    {
                        mPlayer.seekTo(mCurrentPosition + CLICK_IN_MILLIS);
                    }
                }
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "MusicPlayer forwardPlaying() failed");
                e.printStackTrace();
            }
        }
    }

    private void backwardPlaying(boolean isLongClick)
    {
        if (mPlayer != null) {
            try {
                mCurrentPosition = mPlayer.getCurrentPosition();

                if(isLongClick)
                {
                    if(mCurrentPosition < LONG_CLICK_IN_MILLIS)
                    {
                        mPlayer.seekTo(0);
                    }
                    else
                    {
                        mPlayer.seekTo(mCurrentPosition - LONG_CLICK_IN_MILLIS);
                    }
                }
                else
                {
                    if(mCurrentPosition < CLICK_IN_MILLIS)
                    {
                        mPlayer.seekTo(0);
                    }
                    else
                    {
                        mPlayer.seekTo(mCurrentPosition - CLICK_IN_MILLIS);
                    }
                }
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "MusicPlayer backwardPlaying() failed");
                e.printStackTrace();
            }
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnErrorListener(this);

        try {
            mPlayer.setDataSource(getApplicationContext(), mSelectedMusicUri);
            mPlayer.prepare();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "MusicPlayer initializeAndPlay() failed");
            e.printStackTrace();
            stopSelf();
        }
    }

    private void pausePlaying() {
        if (mPlayer != null) {
            try {
                mPlayer.pause();
                mCurrentPosition = mPlayer.getCurrentPosition();
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "MusicPlayer pauseMusic() failed");
                e.printStackTrace();
                stopSelf();
            }
        }
    }

    private void resumePlaying() {
        if (mPlayer != null) {
            try {
                mPlayer.seekTo(mCurrentPosition);
                mPlayer.start();
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "MusicPlayer playMusic() failed");
                e.printStackTrace();
                stopSelf();
            }
        }
    }

    public void onPlayUrl(String state) {
    /*   switch (state) {
            case ACTION_START_RADIO:
                startRadio();
                break;
            case ACTION_START_LIBRARY:
                startLibrary();
                break;
           case ACTION_STOP_LIBRARY:
               startLibrary();
               break;
            case ACTION_STOP:
                stopLibrary();
                break;
        }*/
    }

    private void startLibrary() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnPreparedListener(this);

        try {
            mPlayer.setDataSource(mDataSource);
            mPlayer.prepare();
            mPlayer.seekTo(mCurrentPosition);
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "MusicPlayer startRadio() failed");
            e.printStackTrace();
            stopSelf();
        }
    }

    private void startRadio() {
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnErrorListener(this);

        try {
            mPlayer.setDataSource(mDataSource);
            mPlayer.prepare();

        } catch (Exception e) {
            Log.e(MainActivity.TAG, "MusicPlayer startRadio() failed");
            e.printStackTrace();
            stopSelf();
        }
    }

    private void stopPlaying() {
        if (mPlayer != null) {

            try {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "MusicPlayer stopPlaying() failed");
                e.printStackTrace();
            }

            stopForeground(true);
        }
    }

    private void stopLibrary() {

        if (mPlayer != null) {

            int currentPosition = 0;

            try {
                currentPosition = mPlayer.getCurrentPosition();
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            } catch (Exception e) {
                Log.e(MainActivity.TAG, "MusicPlayer stopPlaying() failed");
                e.printStackTrace();
            }

            SharedPreferences sharedPref = getSharedPreferences("Service", Context.MODE_PRIVATE);
            String title = sharedPref.getString("lastBook", "");

            LibraryDatabaseHelper libraryDatabaseHelper = new LibraryDatabaseHelper(getApplicationContext());
            libraryDatabaseHelper.setCurrentPositionOfMedia(title, currentPosition);

            stopForeground(true);
        }
    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if (action.equals(ACTION_STOP)) {
                stopSelf();
            }
            else if(action.equals(ACTION_PAUSE)) {
                //TODO
                pausePlaying();

                RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification);
                remoteView.setImageViewResource(R.id.ivIcon, R.mipmap.ic_launcher);
                remoteView.setTextViewText(R.id.tvAppName, getResources().getString(R.string.app_name));
                remoteView.setTextViewText(R.id.tvTitle, mediaName);
                remoteView.setImageViewResource(R.id.ibBackward, R.mipmap.ic_player_fast_backward);
                remoteView.setOnClickPendingIntent(R.id.ibBackward, makePendingIntent(ACTION_BACKWARD));
                remoteView.setImageViewResource(R.id.ibForward, R.mipmap.ic_player_fast_forward);
                remoteView.setOnClickPendingIntent(R.id.ibForward, makePendingIntent(ACTION_FORWARD));
                remoteView.setImageViewResource(R.id.ibPlayPause, R.mipmap.ic_player_play);
                remoteView.setOnClickPendingIntent(R.id.ibPlayPause, makePendingIntent(ACTION_PLAY));
                remoteView.setImageViewResource(R.id.ibStop, R.mipmap.ic_player_stop);
                remoteView.setOnClickPendingIntent(R.id.ibStop, makePendingIntent(ACTION_STOP));
                remoteView.setImageViewResource(R.id.ivSingleLine, android.R.color.white);

                notification = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(remoteView)
                        .setCustomBigContentView(remoteView)
                        .setContentIntent(makeContentIntent())
                        .build();

                startForeground(2001, notification);
            }
            else if(action.equals(ACTION_PLAY)) {
                resumePlaying();
                //TODO

                RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification);
                remoteView.setImageViewResource(R.id.ivIcon, R.mipmap.ic_launcher);
                remoteView.setTextViewText(R.id.tvAppName, getResources().getString(R.string.app_name));
                remoteView.setTextViewText(R.id.tvTitle, mediaName);
                remoteView.setImageViewResource(R.id.ibBackward, R.mipmap.ic_player_fast_backward);
                remoteView.setOnClickPendingIntent(R.id.ibBackward, makePendingIntent(ACTION_BACKWARD));
                remoteView.setImageViewResource(R.id.ibForward, R.mipmap.ic_player_fast_forward);
                remoteView.setOnClickPendingIntent(R.id.ibForward, makePendingIntent(ACTION_FORWARD));
                remoteView.setImageViewResource(R.id.ibPlayPause, R.mipmap.ic_player_pause);
                remoteView.setOnClickPendingIntent(R.id.ibPlayPause, makePendingIntent(ACTION_PAUSE));
                remoteView.setImageViewResource(R.id.ibStop, R.mipmap.ic_player_stop);
                remoteView.setOnClickPendingIntent(R.id.ibStop, makePendingIntent(ACTION_STOP));
                remoteView.setImageViewResource(R.id.ivSingleLine, android.R.color.white);

                notification = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(remoteView)
                        .setCustomBigContentView(remoteView)
                        .setContentIntent(makeContentIntent())
                        .build();

                startForeground(2001, notification);
            }
            else if(action.equals(ACTION_FORWARD)) {
                forwardPlaying(false);
            }
            else if(action.equals(ACTION_BACKWARD)) {
                backwardPlaying(false);
            }
        }
    };

    private void startForeground()
    {
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification);
        remoteView.setImageViewResource(R.id.ivIcon, R.mipmap.ic_launcher);
        remoteView.setTextViewText(R.id.tvAppName, getResources().getString(R.string.app_name));
        remoteView.setTextViewText(R.id.tvTitle, mediaName);
        remoteView.setImageViewResource(R.id.ibBackward, R.mipmap.ic_player_fast_backward);
        remoteView.setOnClickPendingIntent(R.id.ibBackward, makePendingIntent(ACTION_BACKWARD));
        remoteView.setImageViewResource(R.id.ibForward, R.mipmap.ic_player_fast_forward);
        remoteView.setOnClickPendingIntent(R.id.ibForward, makePendingIntent(ACTION_FORWARD));
        remoteView.setImageViewResource(R.id.ibPlayPause, R.mipmap.ic_player_pause);
        remoteView.setOnClickPendingIntent(R.id.ibPlayPause, makePendingIntent(ACTION_PAUSE));
        remoteView.setImageViewResource(R.id.ibStop, R.mipmap.ic_player_stop);
        remoteView.setOnClickPendingIntent(R.id.ibStop, makePendingIntent(ACTION_STOP));
        remoteView.setImageViewResource(R.id.ivSingleLine, android.R.color.white);

        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(remoteView)
                .setCustomBigContentView(remoteView)
                .setContentIntent(makeContentIntent())
                .build();

        startForeground(2001, notification);
    }

    private PendingIntent makePendingIntent(String broadcast)
    {
        Intent intent = new Intent(broadcast);
        return PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
    }

    private PendingIntent makeContentIntent()
    {
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        return PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(MainActivity.TAG, "MediaPlayer.OnCompletionListener media play is done.");

        if (mCurrentStreamType == Media.STORY_READER) {

            SharedPreferences sharedPref = getSharedPreferences("Service", Context.MODE_PRIVATE);
            String title = sharedPref.getString("lastBook", "");

            LibraryDatabaseHelper libraryDatabaseHelper = new LibraryDatabaseHelper(getApplicationContext());
            String nextPart = libraryDatabaseHelper.getNextPartOfMedia(title);

            if (nextPart == null || nextPart.isEmpty()) {
                libraryDatabaseHelper.deleteItem(title);
                sendMessage();
            } else {
                mDataSource = nextPart;
                mCurrentPosition = 0;
                stopLibrary();
                startLibrary();
            }
        } else {
            stopSelf();
            stopForeground(true);
            sendMessage();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        sendMessage();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        if(mCurrentStreamType == Media.RADIO)
        {
            startForegroundWithRadio();
        }
        else
        {
            startForeground();
        }

        mp.start();
    }

    private void startForegroundWithRadio() {
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.radio_notification);
        remoteView.setImageViewResource(R.id.ivIcon, R.mipmap.ic_launcher);
        remoteView.setTextViewText(R.id.tvAppName, getResources().getString(R.string.app_name));
        remoteView.setTextViewText(R.id.tvTitle, mediaName);
        remoteView.setImageViewResource(R.id.ibStop, R.mipmap.ic_player_stop);
        remoteView.setOnClickPendingIntent(R.id.ibStop, makePendingIntent(ACTION_STOP));
        remoteView.setImageViewResource(R.id.ivSingleLine, android.R.color.white);

        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(remoteView)
                .setCustomBigContentView(remoteView)
                .setContentIntent(makeContentIntent())
                .build();

        startForeground(2001, notification);
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null)
        {
            mPlayer.release();
            mPlayer = null;
        }

        stopSelf();
        sendMessage();
        unregisterReceiver(broadcastReceiver);
    }
}
