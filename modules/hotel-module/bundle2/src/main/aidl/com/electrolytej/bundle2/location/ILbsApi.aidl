package com.electrolytej.bundle2.location;
import  com.electrolytej.bundle2.location.ILbsListener;

// Declare any non-default types here with import statements

interface ILbsApi {

    void registerListener(ILbsListener listener);
    void unregisterListener(ILbsListener listener);
    void sendImportMockJson();
}