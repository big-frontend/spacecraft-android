
function getFieldValue(object, fieldName) {
    var field = object.class.getDeclaredField(fieldName);
    field.setAccessible(true)
    var fieldValue = field.get(object)
    if (null == fieldValue) {
        return null;
    }
    var FieldClazz = Java.use(fieldValue.$className)
    var fieldValueWapper = Java.cast(fieldValue, FieldClazz)
    return fieldValueWapper
}
function setFieldValue(object, fieldName, fieldValue) {
    var field = object.class.getDeclaredField(fieldName)
    field.setAccessible(true)
    field.set(object, fieldValue)
}

function main() {
    console.log("Enter the Script!");
    Java.perform(function () {
        console.log("Inside Java perform");
        var Response = Java.use("com.sankuai.meituan.retrofit2.Response");
        Response.$init.overload('java.lang.String', 'int', 'java.lang.String', 'java.util.List', 'java.lang.Object', 'com.sankuai.meituan.retrofit2.ResponseBody').implementation = function (a0, a1, a2, a3, a4, a5) {
            // console.log('log');
            // console.warn('warn');
            // console.error('error');
            return this.$init(a0, a1, a2, a3, a4, a5)
        };
        var ApplicationPackageManager = Java.use('android.app.ApplicationPackageManager')
        var origin_getPackageInfo = ApplicationPackageManager.getPackageInfo.overload("java.lang.String", "int")
        origin_getPackageInfo.implementation = function (...args) {

            var packageInfo = origin_getPackageInfo.call(this, ...args)
            // console.log('getpackageinfo before', args[0], args[1], getFieldValue(packageInfo, 'firstInstallTime'), getFieldValue(packageInfo, 'lastUpdateTime'))
            if ("com.sankuai.meituan" == args[0] && args[1] == 64) {
                //'android.content.pm.PackageInfo'
                var Long = Java.use('java.lang.Long')
                setFieldValue(packageInfo, 'firstInstallTime', Long.valueOf(1))
                setFieldValue(packageInfo, 'lastUpdateTime', Long.valueOf(1))

            }
            // console.log('getpackageinfo after', args[0], args[1], getFieldValue(packageInfo, 'firstInstallTime'), getFieldValue(packageInfo, 'lastUpdateTime'))
            return packageInfo

        };
        // var PackageManagerService = Java.use('com.android.server.pm.PackageManagerService')
        // PackageManagerService.getPackageInfo.overload("java.lang.String", "int", "int").implementation = function (...args) {
        //     Log.v("cjf", "PackageManagerService getPackageInfo")
        //     return this.getPackageInfo(args[0], args[1], args[2])
        // }
        // var IPackageManager = Java.use('android.content.pm.IPackageManager.Stub')
        // IPackageManager.getPackageInfo.overload("java.lang.String", "int", "int").implementation = function (...args) {
        //     Log.v("cjf", "IPackageManager getPackageInfo")
        //     return this.getPackageInfo(args[0], args[1], args[2])
        // }
    })


}
setImmediate(main);
//frida -U -f com.hawksjamesf.spacecraft.debug --no-pause -l index.js