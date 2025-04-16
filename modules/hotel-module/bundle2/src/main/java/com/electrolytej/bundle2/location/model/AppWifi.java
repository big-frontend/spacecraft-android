package com.electrolytej.bundle2.location.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AppWifi implements Parcelable {
    public boolean isRegistered;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isRegistered ? (byte) 1 : (byte) 0);
    }

    public AppWifi() {
    }

    protected AppWifi(Parcel in) {
        this.isRegistered = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AppWifi> CREATOR = new Parcelable.Creator<AppWifi>() {
        @Override
        public AppWifi createFromParcel(Parcel source) {
            return new AppWifi(source);
        }

        @Override
        public AppWifi[] newArray(int size) {
            return new AppWifi[size];
        }
    };
}
