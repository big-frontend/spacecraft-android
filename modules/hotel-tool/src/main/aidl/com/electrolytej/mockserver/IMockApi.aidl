// IMockApi.aidl
package com.electrolytej.mockserver;

import com.electrolytej.mockserver.IMockServerCallback;
// Declare any non-default types here with import statements

interface IMockApi {

    void register(IMockServerCallback callback);

}
