package com.electrolytej.location;
import  com.electrolytej.location.ILbsListener;

// Declare any non-default types here with import statements

interface ILbsApi {

    void registerListener(ILbsListener listener);
    void unregisterListener(ILbsListener listener);
}