package com.dnkilic.bong.player;

public class Music extends Media {
	private int mId;
	private String mArtist;
	private String mAlbum;
	private String mGenre;
	private String mDisplayName;

	public Music(int id, String artist, String album, String title, String genre, String path, String displayName) {
		super(path, title, Media.MUSIC);
		mId = id;
		mArtist = artist;
		mAlbum = album;
		mGenre = genre;
		mDisplayName = displayName;
	}

	public int getId() {
		return mId;
	}

	public String getArtist() {
		return mArtist;
	}	

	public String getAlbum() {
		return mAlbum;
	}	

	public String getGenre() {
		return mGenre;
	}

	public String getDisplayName() {
		return mDisplayName;
	}
}
