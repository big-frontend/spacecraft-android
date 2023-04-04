// ILbsListener.aidl
package com.jamesfchen.map;

import  com.jamesfchen.map.model.AppLocation;
import  com.jamesfchen.map.model.AppCellInfo;

interface ILbsListener {

    void onLocationChanged(in AppLocation appLocation,in List<AppCellInfo> appCellInfos,long count);
    void onStatusChanged(String s, int i,in Bundle bundle);
    void onProviderEnabled(String s);
    void onProviderDisabled(String s);
}