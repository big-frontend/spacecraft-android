// ILbsApi.aidl
package com.jamesfchen.map;
import   com.jamesfchen.map.ILbsListener;

// Declare any non-default types here with import statements

interface ILbsApi {

    void registerListener(ILbsListener listener);
    void unregisterListener(ILbsListener listener);
    void sendImportMockJson();
}