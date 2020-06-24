package com.hawksjamesf.map.service;

import android.os.Bundle;
import android.os.RemoteException;

import com.hawksjamesf.map.ILbsListener;
import com.hawksjamesf.map.model.AppCellInfo;
import com.hawksjamesf.map.model.AppLocation;

import java.util.List;

public  class ILbsApiClient extends ILbsListener.Stub {

    @Override
    public void onLocationChanged(AppLocation appLocation, List<AppCellInfo> appCellInfos, long count) throws RemoteException {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) throws RemoteException {

    }

    @Override
    public void onProviderEnabled(String s) throws RemoteException {

    }

    @Override
    public void onProviderDisabled(String s) throws RemoteException {

    }
}

