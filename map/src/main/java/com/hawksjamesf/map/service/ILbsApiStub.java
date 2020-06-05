package com.hawksjamesf.map.service;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.hawksjamesf.map.ILbsApi;
import com.hawksjamesf.map.ILbsListener;


public class ILbsApiStub extends ILbsApi.Stub {
    public RemoteCallbackList<ILbsListener> listenerlist = new RemoteCallbackList<ILbsListener>();
    @Override
    public void registerListener(ILbsListener listener) throws RemoteException {
        listenerlist.register(listener);
    }

    @Override
    public void unregisterListener(ILbsListener listener) throws RemoteException {
        listenerlist.unregister(listener);
    }
}
