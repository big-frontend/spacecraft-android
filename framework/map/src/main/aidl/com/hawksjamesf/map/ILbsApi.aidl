// ILbsApi.aidl
package com.hawksjamesf.map;
import   com.hawksjamesf.map.ILbsListener;

// Declare any non-default types here with import statements

interface ILbsApi {

    void registerListener(ILbsListener listener);
    void unregisterListener(ILbsListener listener);
    void sendImportMockJson();
}