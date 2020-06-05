// ILbsListener.aidl
package com.hawksjamesf.map;

import  com.hawksjamesf.map.model.AppLocation;
import  com.hawksjamesf.map.model.AppCellInfo;

interface ILbsListener {

    void onLocationChanged(in AppLocation appLocation,in AppCellInfo appCellInfo,long count);
    void onStatusChanged(String s, int i,in Bundle bundle);
    void onProviderEnabled(String s);
    void onProviderDisabled(String s);
}