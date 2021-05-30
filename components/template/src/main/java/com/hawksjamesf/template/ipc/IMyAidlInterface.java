package com.hawksjamesf.template.ipc;

import android.os.IInterface;
import android.os.RemoteException;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/05/2021  Wed
 *
 * 手写IMyAidlInterface接口类
 */
public interface IMyAidlInterface extends IInterface {
    public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws RemoteException;

}
