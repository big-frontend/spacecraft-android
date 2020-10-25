package com.hawksjamesf.av;

/**
 * Copyright ® 2019
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Mar/03/2019  Sun
 */
public class Constants {
    public static final String VIDEO_URL = "https://video.c-ctrip.com/videos/u0030l000000dbzlh5934.mp4";
    public static final String GIF_URL = "https://n.sinaimg.cn/tech/transform/138/w600h338/20190228/VRRf-htptaqf5558611.gif";

    public static final String RAW_PATH = "android.resource://%s/%s";
    public static final String ASSETS_PATH = "file:///android_asset/%s";

    /**
     * @param packageName:getContext().getPackageName()
     * @param resid:R.raw.wechatsight1
     * @return
     */
    public static String getRawPath(String packageName, int resid) {
        return String.format(RAW_PATH, packageName, resid);
    }

//    public static String getAssetsPath() {
//        return String.format(ASSETS_PATH, "video_sample_1");
//    }
}
