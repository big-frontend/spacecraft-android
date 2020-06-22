package com.hawksjamesf.map.service;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hawksjamesf.map.ILbsApi;
import com.hawksjamesf.map.ILbsListener;
import com.hawksjamesf.map.model.AppCellInfo;
import com.hawksjamesf.map.model.AppLocation;
import com.hawksjamesf.map.model.L7;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


public class ILbsApiStub extends ILbsApi.Stub {
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
                    AppLocation appLocation = new AppLocation();
                    appLocation.lat = lat;
                    appLocation.lon = lon;
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
