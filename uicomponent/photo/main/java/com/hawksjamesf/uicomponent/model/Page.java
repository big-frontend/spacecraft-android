package com.hawksjamesf.uicomponent.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/02/2019  Mon
 */
public class Page implements Parcelable {
    public Bitmap thumbnailBitmap;
    public URL url;

    public Page(Bitmap thumbnailBitmap, URL url) {
        this.thumbnailBitmap = thumbnailBitmap;
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.thumbnailBitmap, flags);
        dest.writeSerializable(this.url);
    }

    protected Page(Parcel in) {
        this.thumbnailBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.url = (URL) in.readSerializable();
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel source) {
            return new Page(source);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };
}
