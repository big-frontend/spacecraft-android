package com.jamesfchen.bundle1.aidl;

import static android.os.IBinder.FIRST_CALL_TRANSACTION;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/30/2020  Mon
 */
public final class Constants {
    public static final String DESCRIPTOR = "com.hawksjamesf.template.IMyAidlInterface";
    public static final int TRANSACTION_basicTypes = FIRST_CALL_TRANSACTION + 1;
    public static final int TRANSACTION_basicTypesv2 = FIRST_CALL_TRANSACTION + 2;
}
