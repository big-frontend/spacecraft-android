package com.hawksjamesf.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jul/01/2019  Mon
 */
public class AndroidParcelable implements Parcelable {
    public byte arg0;
    public char arg1;
    public String arg2;
    public boolean arg3;
    public int arg4;
    public short arg5;
    public long arg6;
    public float arg7;
    public double arg8;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.arg0);
        dest.writeInt(this.arg1);
        dest.writeString(this.arg2);
        dest.writeByte(this.arg3 ? (byte) 1 : (byte) 0);
        dest.writeInt(this.arg4);
        dest.writeInt(this.arg5);
        dest.writeLong(this.arg6);
        dest.writeFloat(this.arg7);
        dest.writeDouble(this.arg8);
    }

    public AndroidParcelable() {
    }

    protected AndroidParcelable(Parcel in) {
        this.arg0 = in.readByte();
        this.arg1 = (char) in.readInt();
        this.arg2 = in.readString();
        this.arg3 = in.readByte() != 0;
        this.arg4 = in.readInt();
        this.arg5 = (short) in.readInt();
        this.arg6 = in.readLong();
        this.arg7 = in.readFloat();
        this.arg8 = in.readDouble();
    }

    public static final Creator<AndroidParcelable> CREATOR = new Creator<AndroidParcelable>() {
        @Override
        public AndroidParcelable createFromParcel(Parcel source) {
            return new AndroidParcelable(source);
        }

        @Override
        public AndroidParcelable[] newArray(int size) {
            return new AndroidParcelable[size];
        }
    };
}
