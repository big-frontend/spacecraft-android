package com.hawksjamesf.template.ipc;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Keep;

import static com.hawksjamesf.template.ipc.Constance.TRANSACTION_basicTypes;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/25/2020  Wed
 * <p>
 * Proxy rule
 */
@Keep
public class BinderShadow implements IMyAidlInterface {
    private IBinder mRemote;

    public BinderShadow(IBinder mRemote) {
        this.mRemote = mRemote;
    }

    public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws RemoteException {
        Log.d("cjf", "shadow basicTypes:" + anInt + " " + aLong + " " + aBoolean + " " + aFloat + " " + aDouble + " " + aString);
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
            _data.writeInterfaceToken(Constance.DESCRIPTOR);
            _data.writeInt(anInt);
            _data.writeLong(aLong);
            _data.writeInt(((aBoolean) ? (1) : (0)));
            _data.writeFloat(aFloat);
            _data.writeDouble(aDouble);
            _data.writeString(aString);
            boolean status = mRemote.transact(TRANSACTION_basicTypes, _data, _reply, 0);
            Log.d("cjf", "shadow status:" + status);
            _reply.readException();
            _result = _reply.readInt();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
