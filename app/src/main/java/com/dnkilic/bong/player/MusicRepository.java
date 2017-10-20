package com.dnkilic.bong.player;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.sestek.ma.assistant.MainActivity;

import java.util.ArrayList;

public class MusicRepository {

    public static final int SUCCESSFUL_MUSIC_QUERY = 0;
    public static final int NO_MUSIC = 1;
    public static final int QUERY_ERROR = 2;

    private Activity mAct;
    private ArrayList<Music> mMusicList;

	public MusicRepository(Activity act) {
		mAct = act;
	}

	public ArrayList<Music> getMusicList()
	{
		return mMusicList;
	}

    public int queryMusicFiles() {
        return startManagingMediaCursor();
	}

    private int startManagingMediaCursor() {

		String[] projection = {
				MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.DURATION
		};

		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

		try {
			Cursor mediaCursor = mAct.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					projection, selection, null, null);

			mMusicList = new ArrayList<>();

			if (mediaCursor.moveToFirst()) {
				do {
					String title = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
					String album = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
					String artist = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
					String path = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
					String displayName = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
					int musicId = Integer.parseInt(mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
					int duration = Integer.parseInt(mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))); //60000


					if(duration >= 60000)
					{
						String[] genreProjection = {
								MediaStore.Audio.Genres.NAME,
								MediaStore.Audio.Genres._ID
						};

						Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
						Cursor genresCursor = mAct.getContentResolver().query(uri,
								genreProjection, null, null, null);

						if (genresCursor.moveToFirst()) {
							do {
								if((genresCursor.getString(genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME)) != null
										|| !genresCursor.getString(genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME)).isEmpty()) )
								{
									String genre = genresCursor.getString(genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME));
									mMusicList.add(new Music(musicId, artist, album, title, genre, path, displayName));
									Log.i(MainActivity.TAG, "QueryMusic : Title : " + title);
									Log.i(MainActivity.TAG, "QueryMusic : Genre : " + genre);
									Log.i(MainActivity.TAG, "QueryMusic : Path : " + path);
								}
							} while (genresCursor.moveToNext());

							genresCursor.close();
						}
						else
						{
							String genre = "Unknown";
							mMusicList.add(new Music(musicId, artist, album, title, genre, path, displayName));
							Log.i(MainActivity.TAG, "QueryMusic : Title : " + title);
							Log.i(MainActivity.TAG, "QueryMusic : Genre : " + genre);
							Log.i(MainActivity.TAG, "QueryMusic : Path : " + path);
						}
					}
				} while (mediaCursor.moveToNext());

				mediaCursor.close();
				
				if(mMusicList == null || mMusicList.isEmpty())
				{
                    return MusicRepository.NO_MUSIC;
                }

                return MusicRepository.SUCCESSFUL_MUSIC_QUERY;
            } else
			{
                return MusicRepository.NO_MUSIC;
            }

		} catch (Exception e) {
			e.printStackTrace();
            return MusicRepository.QUERY_ERROR;
        }
    }
}
