package com.hawksjamesf.template.binder;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.hawksjamesf.template.binder.Constance.DESCRIPTOR;
import static com.hawksjamesf.template.binder.Constance.TRANSACTION_basicTypes;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2020  Wed
 * <p>
 * Stub rule
 */

public class BinderEntry extends Binder {

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case FIRST_CALL_TRANSACTION: {
                Log.d("cjf", "entry FIRST_CALL_TRANSACTION");
                break;
            }
            case LAST_CALL_TRANSACTION: {
                Log.d("cjf", "entry LAST_CALL_TRANSACTION");
                break;

            }
            case PING_TRANSACTION: {
                Log.d("cjf", "entry PING_TRANSACTION");
                break;
            }
            case DUMP_TRANSACTION: {
                Log.d("cjf", "entry DUMP_TRANSACTION");
                break;
            }
            case INTERFACE_TRANSACTION: {
                Log.d("cjf", "entry INTERFACE_TRANSACTION");
                break;
            }
            case TWEET_TRANSACTION: {
                Log.d("cjf", "entry TWEET_TRANSACTION");
                break;
            }
            case LIKE_TRANSACTION: {
                Log.d("cjf", "entry LIKE_TRANSACTION");
                break;
            }
            case TRANSACTION_basicTypes: {
                Log.d("cjf", "entry TRANSACTION_basicTypes");
                data.enforceInterface(DESCRIPTOR);
                int _arg0;
                _arg0 = data.readInt();
                long _arg1;
                _arg1 = data.readLong();
                boolean _arg2;
                _arg2 = (0 != data.readInt());
                float _arg3;
                _arg3 = data.readFloat();
                double _arg4;
                _arg4 = data.readDouble();
                java.lang.String _arg5;
                _arg5 = data.readString();
                this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                reply.writeNoException();
                return true;
            }
            default:
                Log.d("cjf", "entry code:" + code);
                break;


        }
        return super.onTransact(code, data, reply, flags);
    }

    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) {
        Log.d("cjf", "entry basicTypes:" + anInt + " " + aLong + " " + aBoolean + " " + aFloat + " " + aDouble + " " + aString);
    }
}
