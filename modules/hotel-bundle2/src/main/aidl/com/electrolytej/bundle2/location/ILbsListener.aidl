package com.electrolytej.bundle2.location;
import com.electrolytej.bundle2.location.model.AppLocation;
import com.electrolytej.bundle2.location.model.AppCellInfo;
interface ILbsListener {
    void onLocationChanged(in AppLocation appLocation,in List<AppCellInfo> appCellInfos,long count);
    void onStatusChanged(String s, int i,in Bundle bundle);
    void onProviderEnabled(String s);
    void onProviderDisabled(String s);
}