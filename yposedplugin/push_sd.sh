#!/usr/bin/env bash
adb push $1 $2
adb shell am force-stop 'com.hawksjamesf.spacecraft'
adb  shell pm grant  "com.hawksjamesf.spacecraft android.permission.READ_EXTERNAL_STORAGE"
adb  shell pm grant "com.hawksjamesf.spacecraft  android.permission.WRITE_EXTERNAL_STORAGE"
adb shell am start -n 'lcom.hawksjamesf.spacecraft/.MainActivity'