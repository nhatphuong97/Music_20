package com.framgia.music_20.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class MoreData implements Parcelable {

    public static final Creator<MoreData> CREATOR = new Creator<MoreData>() {
        @Override
        public MoreData createFromParcel(Parcel in) {
            return new MoreData(in);
        }

        @Override
        public MoreData[] newArray(int size) {
            return new MoreData[size];
        }
    };
    private List<Song> mSongLists;
    private String mNextHref;

    public MoreData() {
        super();
    }

    protected MoreData(Parcel in) {
        mSongLists = in.createTypedArrayList(Song.CREATOR);
        mNextHref = in.readString();
    }

    public static Creator<MoreData> getCREATOR() {
        return CREATOR;
    }

    public List<Song> getSongArrayList() {
        return mSongLists;
    }

    public void setSongArrayList(List<Song> songArrayList) {
        mSongLists = songArrayList;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mSongLists);
        dest.writeString(mNextHref);
    }
}
