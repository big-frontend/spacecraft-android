// IMockApi.aidl
package com.jamesfchen.mockserver;

import com.jamesfchen.mockserver.IMockServerCallback;
// Declare any non-default types here with import statements

interface IMockApi {

    void register(IMockServerCallback callback);

}
