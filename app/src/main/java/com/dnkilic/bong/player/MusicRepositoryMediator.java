package com.dnkilic.bong.player;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Random;

public class MusicRepositoryMediator {

    public static final int EMPTY_MUSIC_LIST = 0;
    public static final int QUERY_MUSIC_LIST_ERROR = 1;
    public static final int ERROR_PLAY_MUSIC = 2;
    public static final int NO_MUSIC_WITH_SELECTED_CRITERIA = 3;
    public static final int SUCCESSFUL_PLAY_MUSIC = 4;

    private Activity mAct;
    private ArrayList<Music> mSelectedMusicList;
    private MusicRepository mMusicRepository;
    private Music mSelectedMusic;

    public MusicRepositoryMediator(Activity act) {
        mAct = act;
    }

    public int searchMusic(String genre) {
        switch(queryMusicFiles()) {
            case MusicRepository.NO_MUSIC:
                return MusicRepositoryMediator.EMPTY_MUSIC_LIST;
            case MusicRepository.QUERY_ERROR:
                return MusicRepositoryMediator.QUERY_MUSIC_LIST_ERROR;
            case MusicRepository.SUCCESSFUL_MUSIC_QUERY:

                boolean isGenreEnabled;

                isGenreEnabled = !genre.isEmpty();

                searchSelectedGenreOnMusicList(genre, isGenreEnabled);

                if (mSelectedMusicList == null || mSelectedMusicList.isEmpty()) {
                    return MusicRepositoryMediator.NO_MUSIC_WITH_SELECTED_CRITERIA;
                }

                mSelectedMusic = getRandomSongFromSelectedMusicListByGenre();
                return MusicRepositoryMediator.SUCCESSFUL_PLAY_MUSIC;
            default:
                return MusicRepositoryMediator.ERROR_PLAY_MUSIC;
        }
    }

    public Music getSelectedMusic() {
        return mSelectedMusic;
    }

    private Music getRandomSongFromSelectedMusicListByGenre() {
        return mSelectedMusicList.get(new Random().nextInt(mSelectedMusicList.size()));
    }

    private void searchSelectedGenreOnMusicList(String genre, boolean isGenreEnabled) {
        ArrayList<Music> musicList = mMusicRepository.getMusicList();
        mSelectedMusicList = new ArrayList<>();

        for (Music music : musicList) {
            if (isGenreEnabled) {
                if (music.getGenre().toLowerCase().contains(genre)) {
                    mSelectedMusicList.add(music);
                }
            } else {
                mSelectedMusicList.add(music);
            }
        }
    }


    private int queryMusicFiles() {
        mMusicRepository = new MusicRepository(mAct);
        return mMusicRepository.queryMusicFiles();
    }
}
