package com.electrolytej.location;

import android.location.Location;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

public final class AppLocation implements Parcelable {
    public double lat;
    public double lon;
    public double accu;
    public double altit;
    public double bearing;
    public double speed;
    @ColumnInfo(name="is_location_mock")
    public boolean isMockData =false;

    public static AppLocation convertSysLocation(Location location) {
        AppLocation appLocation = new AppLocation();
        if (location != null) {
            appLocation.lat = location.getLatitude();
            appLocation.lon = location.getLongitude();
            appLocation.accu = location.getAccuracy();
            appLocation.altit = location.getAltitude();
            appLocation.bearing = location.getBearing();
            appLocation.speed = location.getSpeed();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                location.getBearingAccuracyDegrees();
                location.getVerticalAccuracyMeters();
                location.getSpeedAccuracyMetersPerSecond();
            }
        }
        return appLocation;
    }

    @Override
    public String toString() {
        return "\"appLocation\":{" +
                "\"lat\":" + lat +
                ", \"lon\":" + lon +
                ", \"accu\":" + accu +
                ", \"altit\":" + altit +
                ", \"bearing\":" + bearing +
                ", \"speed\":" + speed +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isMockData ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lon);
        dest.writeDouble(this.accu);
        dest.writeDouble(this.altit);
        dest.writeDouble(this.bearing);
        dest.writeDouble(this.speed);
    }

    public AppLocation() {
    }

    protected AppLocation(Parcel in) {
        this.isMockData = in.readByte() != 0;
        this.lat = in.readDouble();
        this.lon = in.readDouble();
        this.accu = in.readDouble();
        this.altit = in.readDouble();
        this.bearing = in.readDouble();
        this.speed = in.readDouble();
    }

    public static final Parcelable.Creator<AppLocation> CREATOR = new Parcelable.Creator<AppLocation>() {
        @Override
        public AppLocation createFromParcel(Parcel source) {
            return new AppLocation(source);
        }

        @Override
        public AppLocation[] newArray(int size) {
            return new AppLocation[size];
        }
    };
}
