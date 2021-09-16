import subprocess, os

PACKAGE_NAME = 'ctrip.android.view.debug'
FILE_NAME = PACKAGE_NAME+".hprof"
FILE_PATH = "/data/local/tmp/" + FILE_NAME

# adb pull  ${FILE_PATH} .
# hprof-conv -z ${FILE_NAME} "mat-${FILE_PATH}"
os.popen('adb shell pkill -l 10 ' + PACKAGE_NAME).read()
os.popen("adb shell am dumpheap %s %s" % (PACKAGE_NAME, FILE_PATH)).read()
os.popen('adb pull %s %s' % (FILE_PATH,os.getcwd())).read()
os.popen('hprof-conv -z %s %s' % (FILE_NAME,'mat-'+FILE_NAME)).read()
a = os.popen('adb shell dumpsys meminfo %s' % PACKAGE_NAME).read()
with open('meminfo.txt','w') as f:
    f.write(a)