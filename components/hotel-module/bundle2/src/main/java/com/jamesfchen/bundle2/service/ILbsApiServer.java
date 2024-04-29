package com.jamesfchen.bundle2.service;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamesfchen.bundle2.location.ILbsApi;
import com.jamesfchen.bundle2.location.ILbsListener;
import com.jamesfchen.bundle2.location.model.AppCellInfo;
import com.jamesfchen.bundle2.location.model.AppLocation;
import com.jamesfchen.bundle2.location.model.L7;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


public class ILbsApiServer extends ILbsApi.Stub {
    long count = 0;
    public RemoteCallbackList<ILbsListener> listenerlist = new RemoteCallbackList<ILbsListener>();

    @Override
    public void registerListener(ILbsListener listener) throws RemoteException {
        listenerlist.register(listener);
    }

    @Override
    public void unregisterListener(ILbsListener listener) throws RemoteException {
        listenerlist.unregister(listener);
    }

    @Override
    public void sendImportMockJson() throws RemoteException {
        mockRequestLocation();
        //        Type jsonType = new TypeToken<List<L7_trip>>() {}.getType();
//        List<L7_trip> L7_1s = new Gson().fromJson(ResourceUtils.readAssets2String("l7_list_trip.json"), jsonType);
//        for (L7_trip l7_1 : L7_1s) {
//            addMarker(l7_1.getLat(), l7_1.getLon(), l7_1.getCid(), l7_1.getLac(),"南京");
//        }
    }

    private void mockRequestLocation() {
        if (listenerlist == null) return;
        Type type = new TypeToken<List<L7>>() {
        }.getType();
        List<L7> l7_list = new Gson().fromJson(ResourceUtils.readAssets2String("l7_list.json"), type);
        try {
            Thread.sleep(1500);
            for (L7 l7 : l7_list) {
                double lat = l7.appLocation.lat;
                double lon = l7.appLocation.lon;
                long cid = l7.appCellInfos.get(0).get(0).cid;
                long lac = l7.appCellInfos.get(0).get(0).lac;
                //startservice 回调(onHandleIntent) 和bindservice回调(onServiceConnected) 都是异步，所以sleep模拟onHandleIntent处理耗时,
//                Thread.sleep(500);
                final int N = listenerlist.beginBroadcast();
                for (int i = 0; i < N; i++) {
                    ILbsListener l = listenerlist.getBroadcastItem(i);
                    if (l == null) continue;

                    AppCellInfo appCellInfo = new AppCellInfo();
                    appCellInfo.cid = cid;
                    appCellInfo.lac = lac;
                    appCellInfo.isRegistered = true;
                    appCellInfo.isMockData = true;
                    AppLocation appLocation = new AppLocation();
                    appLocation.lat = lat;
                    appLocation.lon = lon;
                    appLocation.isMockData = true;
                    ++count;
                    l.onLocationChanged(appLocation, Collections.singletonList(appCellInfo), count);
                }
                listenerlist.finishBroadcast();
            }
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }

    }
}
