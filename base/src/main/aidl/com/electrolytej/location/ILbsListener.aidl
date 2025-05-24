package com.electrolytej.location;
import com.electrolytej.location.AppLocation;
import com.electrolytej.location.AppCellInfo;
interface ILbsListener {
    void onLocationChanged(in AppLocation appLocation,in List<AppCellInfo> appCellInfos,long count);
    void onStatusChanged(String s, int i,in Bundle bundle);
    void onProviderEnabled(String s);
    void onProviderDisabled(String s);
}