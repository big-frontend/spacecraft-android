// IMockApi.aidl
package com.hawksjamesf.mockserver;

import com.hawksjamesf.mockserver.IMockServerCallback;
// Declare any non-default types here with import statements

interface IMockApi {

    void register(IMockServerCallback callback);

}
