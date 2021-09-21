package com.jamesfchen.myhome.screen.photo.model;

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
public class Photo implements Parcelable {
    public Uri uri;
    public Uri thumbnailUri;

    public Photo(Uri thumbnailUri, Uri uri) {
        this.thumbnailUri = thumbnailUri;
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.uri,flags);
        dest.writeParcelable(this.thumbnailUri,flags);
    }

    protected Photo(Parcel in) {
        this.uri = (Uri) in.readParcelable(Uri.class.getClassLoader());
        this.thumbnailUri = (Uri) in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
