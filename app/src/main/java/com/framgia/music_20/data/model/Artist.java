package com.framgia.music_20.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

    private int mID;
    private String mUsername;
    private String mAvatarUrl;

    public Artist() {
    }

    protected Artist(Parcel in) {
        mID = in.readInt();
        mUsername = in.readString();
        mAvatarUrl = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public static Creator<Artist> getCREATOR() {
        return CREATOR;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mID);
        dest.writeString(mUsername);
        dest.writeString(mAvatarUrl);
    }
}
