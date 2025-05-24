package com.electrolytej.location;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.electrolytej.location.ILbsApi;
import com.electrolytej.location.ILbsListener;

public class ILbsApiServer extends ILbsApi.Stub {

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
