package com.jamesfchen.ibc
class RouterInfo{
    String name
    String canonicalName

    RouterInfo(String name, String canonicalName) {
        this.name = name
        this.canonicalName = canonicalName
    }

    @Override
    String toString() {
        return "RouterInfo{" +
                "name='" + name + '\'' +
                ", canonicalName='" + canonicalName + '\'' +
                '}';
    }
}