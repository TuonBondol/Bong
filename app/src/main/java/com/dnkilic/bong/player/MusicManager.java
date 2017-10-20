package com.dnkilic.bong.player;

import android.app.Activity;
import android.util.Log;

import com.dnkilic.bong.ITask;

import ai.api.model.AIResponse;

public class MusicManager implements ITask {

    @Override
    public void execute(AIResponse result) {

    }
/*

    private CallSteeringResult mCallSteeringResult;
    private ICommand mCommand;
    private Activity mActivity;

    public MusicManager(CallSteeringResult callSteeringResult, ICommand command, Activity activity) {
        mCallSteeringResult = callSteeringResult;
        mCommand = command;
        mActivity = activity;
    }

    @Override
    public void Execute() throws Exception {

        Log.i(MainActivity.TAG, "MusicManager is started.");

        if(mCallSteeringResult.getCommandType() == CommandType.PLAY_MUSIC)
        {
            playMusic();
        }
        else if(mCallSteeringResult.getCommandType() == CommandType.PLAY_RADIO)
        {
            playRadio();
        }
    }

    private void playMusic() throws Exception {

        Log.i(MainActivity.TAG, "MusicManager play music is called.");

        String genre;

        if(mCallSteeringResult.getSlotValues() != null )
        {
            if(mCallSteeringResult.getSlotValues().get("@genre") != null)
            {
                genre = mCallSteeringResult.getSlotValues().get("@genre").get(0);
            }
            else {
                genre = "";
            }
        }
        else
        {
            genre = "";
        }

        MusicRepositoryMediator adapter = new MusicRepositoryMediator(mActivity);

        switch(adapter.searchMusic(genre))
        {
            case MusicRepositoryMediator.EMPTY_MUSIC_LIST:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_music_manager_there_is_no_music))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case MusicRepositoryMediator.NO_MUSIC_WITH_SELECTED_CRITERIA:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_music_manager_there_is_no_music_with_selected_criteria))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case MusicRepositoryMediator.QUERY_MUSIC_LIST_ERROR:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_music_manager_error_while_querying_musics))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case MusicRepositoryMediator.SUCCESSFUL_PLAY_MUSIC:
                //mCommand.onCommandExecutionContinueWithMusicPlay(adapter.getSelectedMusic());
                mCommand.onCommandExecutionContinueWithMediaPlay(adapter.getSelectedMusic());
                break;
            case MusicRepositoryMediator.ERROR_PLAY_MUSIC:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_music_manager_technical_error))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
        }
    }

    private void playRadio() {
        Log.i(MainActivity.TAG, "MusicManager play radio is called.");
        mCommand.onCommandExecutionContinueWithMediaPlay(new Radio("TRT Radio", "http://trtcanlifm-lh.akamaihd.net/i/RADYO1_1@182345/master.m3u8"));
    }
*/
}
