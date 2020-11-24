package lab.galaxy.yahfa;

import com.hawksjamesf.yposedplugin.Hook_PackageManager_getPackageInfo;
import com.hawksjamesf.yposedplugin.NetClient_sendRequest;

public class HookInfo {
    public static final String TAG = "HookInfo";
    public static String[] hookItemNames = {
            Hook_PackageManager_getPackageInfo.class.getName(),
            NetClient_sendRequest.class.getName(),
    };
}
