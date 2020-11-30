package com.hawksjamesf.template.binder;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Keep;

import static com.hawksjamesf.template.binder.Constance.TRANSACTION_basicTypes;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2020  Wed
 * <p>
 * Proxy rule
 */
@Keep
public class BinderShadow {
    private IBinder mRemote;

    public BinderShadow(IBinder mRemote) {
        this.mRemote = mRemote;
    }

    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws RemoteException {
        Log.d("cjf", "shadow basicTypes:" + anInt + " " + aLong + " " + aBoolean + " " + aFloat + " " + aDouble + " " + aString);
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(Constance.DESCRIPTOR);
            _data.writeInt(anInt);
            _data.writeLong(aLong);
            _data.writeInt(((aBoolean)?(1):(0)));
            _data.writeFloat(aFloat);
            _data.writeDouble(aDouble);
            _data.writeString(aString);
            boolean status = mRemote.transact(TRANSACTION_basicTypes, _data, _reply, 0);
            Log.d("cjf","shadow status:"+status);
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }
}
