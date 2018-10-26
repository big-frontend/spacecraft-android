package com.hawksjamesf.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks.jamesf
 * @since: 1/26/18
 */

public class LocationTracker implements LocationListener {

    private boolean enableGps;
    private boolean enableNetwork;
    private Context context;
    public static final int DEFAULTTIME = 2 * 60 * 1000;
    public static final int DEFAULTDISRANCE = 1000;
    private long minTime = DEFAULTTIME;
    private float minDistance = DEFAULTDISRANCE;
    private LocationManager locationManager;
    private boolean mListening;
    private Location mCurrentGpsLocation;
    private Location mCurrentNetWork;
    public Location betterLocation;
    public static final int TWO_MINUTE = 2 * 60 * 1000;


    LocationTracker(Context context, boolean enableGps, boolean enableNetwork, long minTime, float minDistance) {
        this.enableGps = enableGps;
        this.enableNetwork = enableNetwork;
        this.context = context;
        this.minTime = minTime;
        this.minDistance = minDistance;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }

    public void startTracker() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (enableGps) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
                mListening = true;

            }
        }
        if (enableNetwork) {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, this);
                mListening = true;
            }

        }
    }

    public void stopTracker() {
        if (mListening) {
            mListening = false;
            locationManager.removeUpdates(this);
            locationManager = null;
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            if (pickupBetterLocation(location, mCurrentGpsLocation)) {
                mCurrentGpsLocation = location;
            }
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {

            if (pickupBetterLocation(location, mCurrentNetWork)) {
                mCurrentNetWork = location;

            }
        }
    }

    public Location getLocation() {
        if (pickupBetterLocation(mCurrentGpsLocation, mCurrentNetWork)) {
            return mCurrentGpsLocation;
        } else {
            return mCurrentNetWork;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private boolean pickupBetterLocation(Location location, Location currentLocation) {
        if (currentLocation == null) {
            return true;
        }
        long timeDelta = location.getTime() - currentLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTE;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTE;
        boolean isNewer = timeDelta > 0;


        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int) (location.getAccuracy() - currentLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;

        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        String provider = location.getProvider();
        String currentProvider = currentLocation.getProvider();

        boolean isFromSameProvider;
        if (provider == null) {
            isFromSameProvider = currentProvider == null;
        } else {
            isFromSameProvider = provider.equals(currentProvider);
        }


        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;

    }

    public static class Builder {
        private boolean enableGps;
        private boolean enableNetwork;
        private Context context;
        private long minTime;

        public Builder setMinTime(long minTime) {
            this.minTime = minTime;
            return this;
        }

        public Builder setMinDistance(float minDistance) {
            this.minDistance = minDistance;
            return this;
        }

        private float minDistance;

        public Builder setEnableGps(boolean enableGps) {
            this.enableGps = enableGps;
            return this;
        }

        public Builder setEnableNetwork(boolean enableNetwork) {
            this.enableNetwork = enableNetwork;
            return this;
        }


        public Builder(Context context) {
            this.context = context;
        }

        public LocationTracker build() {
            return new LocationTracker(context, enableGps, enableNetwork, minTime, minDistance);
        }
    }
}
