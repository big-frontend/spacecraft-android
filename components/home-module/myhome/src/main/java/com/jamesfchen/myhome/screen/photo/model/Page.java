package com.jamesfchen.myhome.screen.photo.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Dec/02/2019  Mon
 */
public class Page implements Parcelable {
    public Bitmap thumbnailBitmap;
    public Uri uri;

    public Page(Bitmap thumbnailBitmap, Uri uri) {
        this.thumbnailBitmap = thumbnailBitmap;
        this.uri = uri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.thumbnailBitmap, flags);
        dest.writeParcelable(this.uri,flags);
    }

    protected Page(Parcel in) {
        this.thumbnailBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.uri = (Uri) in.readParcelable(Uri.class.getClassLoader());
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
