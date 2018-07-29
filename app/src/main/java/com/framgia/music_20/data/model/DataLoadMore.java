package com.framgia.music_20.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class DataLoadMore implements Parcelable {

    private ArrayList<Song> mSongArrayList;
    private String mNextHref;

    protected DataLoadMore(Parcel in) {
        mSongArrayList = in.createTypedArrayList(Song.CREATOR);
        mNextHref = in.readString();
    }

    public static final Creator<DataLoadMore> CREATOR = new Creator<DataLoadMore>() {
        @Override
        public DataLoadMore createFromParcel(Parcel in) {
            return new DataLoadMore(in);
        }

        @Override
        public DataLoadMore[] newArray(int size) {
            return new DataLoadMore[size];
        }
    };

    public ArrayList<Song> getSongArrayList() {
        return mSongArrayList;
    }

    public void setSongArrayList(ArrayList<Song> songArrayList) {
        mSongArrayList = songArrayList;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    public static Creator<DataLoadMore> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mSongArrayList);
        dest.writeString(mNextHref);
    }
}
