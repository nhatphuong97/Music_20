package com.framgia.music_20.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class MoreData implements Parcelable {

    private List<Song> mSongs;
    private String mNextHref;

    public MoreData() {
        super();
    }

    protected MoreData(Parcel in) {
        mSongs = in.createTypedArrayList(Song.CREATOR);
        mNextHref = in.readString();
    }

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

    public List<Song> getSongArrayList() {
        return mSongs;
    }

    public void setSongArrayList(List<Song> songArrayList) {
        mSongs = songArrayList;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    public static Creator<MoreData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mSongs);
        dest.writeString(mNextHref);
    }
}
