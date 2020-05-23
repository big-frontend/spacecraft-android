#!/usr/bin/env bash
adb push $1 $2
adb shell am force-stop 'lab.galaxy.yahfa.demoApp'
adb  shell pm grant  "lab.galaxy.yahfa.demoApp android.permission.READ_EXTERNAL_STORAGE"
adb  shell pm grant "lab.galaxy.yahfa.demoApp  android.permission.WRITE_EXTERNAL_STORAGE"
adb shell am start -n 'lab.galaxy.yahfa.demoApp/.MainActivity'