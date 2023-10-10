package lab.galaxy.yahfa;

import androidx.annotation.Keep;

import com.jamesfchen.vi.render.Hook_ExternalBeginFrameSourceAndroid_doFrame;
import com.jamesfchen.vi.startup.Hook_ActivityThread_installContentProviders;
import com.jamesfchen.vi.startup.Hook_ActivityThread_installProvider;
import com.jamesfchen.vi.startup.Hook_MainFragment_onHomeDrawCallback;
import com.jamesfchen.vi.startup.Hook_PerfApp_onCreate;
import com.jamesfchen.vi.startup.Hook_MainActivity_onCreate;
import com.jamesfchen.vi.startup.Hook_MainActivity_onResume;
import com.jamesfchen.vi.startup.Hook_MainActivity_onStart;
import com.jamesfchen.vi.startup.Hook_MainActivity_onWindowFocusChanged;
import com.jamesfchen.vi.startup.Hook_MainFragment_onCreateView;
import com.jamesfchen.vi.startup.Hook_MainFragment_onViewCreated;

@Keep
public class HookInfo {
    public static String[] hookItemNames = {
            //启动
            Hook_ActivityThread_installContentProviders.class.getName(),
            Hook_ActivityThread_installProvider.class.getName(),
            Hook_PerfApp_onCreate.class.getName(),
            Hook_MainActivity_onCreate.class.getName(),
            Hook_MainActivity_onStart.class.getName(),
            Hook_MainActivity_onResume.class.getName(),
            Hook_MainActivity_onWindowFocusChanged.class.getName(),
            Hook_MainFragment_onCreateView.class.getName(),
            Hook_MainFragment_onViewCreated.class.getName(),
            Hook_MainFragment_onHomeDrawCallback.class.getName(),
            //渲染
//            Hook_Choreographer_doCallbacks.class.getName(),
            Hook_ExternalBeginFrameSourceAndroid_doFrame.class.getName(),
    };
}
