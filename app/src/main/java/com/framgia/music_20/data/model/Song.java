package com.framgia.music_20.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private int mId;
    private String mGenre;
    private String mTitle;
    private String mStreamUrl;
    private String mArtworkUrl;
    private int mDuration;
    private Artist mArtist;

    public Song() {
    }

    public Song(Parcel in) {
        mId = in.readInt();
        mGenre = in.readString();
        mTitle = in.readString();
        mStreamUrl = in.readString();
        mArtworkUrl = in.readString();
        mDuration = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public static Creator<Song> getCREATOR() {
        return CREATOR;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getStreamUrl() {
        return mStreamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        mStreamUrl = streamUrl;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public Artist getArtist() {
        return mArtist;
    }

    public void setArtist(Artist artist) {
        mArtist = artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mGenre);
        dest.writeString(mTitle);
        dest.writeString(mStreamUrl);
        dest.writeString(mArtworkUrl);
        dest.writeInt(mDuration);
    }
}
