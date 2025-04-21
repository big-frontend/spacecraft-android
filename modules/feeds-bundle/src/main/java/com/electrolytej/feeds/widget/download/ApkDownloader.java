package com.electrolytej.feeds.widget.download;

import androidx.annotation.MainThread;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApkDownloader {
    public static final String TAG = "ApkDownloader";

    public static ApkDownloader getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        public static ApkDownloader instance = new ApkDownloader();
    }
//
//    public static final int DOWNLOAD_LOCAL = 0;                //本地下载按钮在落地页
//    public static final int DOWNLOAD_LAND = 1;                //h5下载按钮在落地页
//    private static final String KEY_SIGNATURE = "signature";
//
//    private DownloadReceiver mDownloadReceiver;
//    private ApkInstallReceiver mApkInstallReceiver;
    private Map<DownloadKey, Set<OnDownloadListener>> mDownloadListeners = new ConcurrentHashMap<>();
//    private Map<String, Set<OnInstallListener>> mInstallListeners = new ConcurrentHashMap<>();
//
//    private ApkDownloader() {
//        mDownloadReceiver = new ApkDownloadReceiver();
//        registerInstallReceiver();
//
//    }
//
//    /**
//     *  todo:广告这种方式带优化，更推荐监听器方式，该能力需要下层到框架支持
//     */
//    @Deprecated
//    private class ApkDownloadReceiver extends DownloadReceiver {
//        ApkDownloadReceiver() {
//            super(.getApplication());
//        }
//
//        @Override
//        public void onReceive(DownloadStatus downloadStatus, DownloadConfig downloadConfig) {
//            if (mDownloadListeners.isEmpty()) return;
//            if (downloadConfig == null) {
//                Log.d(TAG, "downloadStatus:" + downloadStatus + " downloadConfig is null");
//                return;
//            }
//            DownloadExtra extra = (DownloadExtra) downloadConfig.object;
//            Log.d(TAG, downloadStatus+" "+extra.type+" downloadConfig:" + downloadConfig.progress +" size:"+mDownloadListeners.size() +" key:"+downloadConfig.url);
//            for (Map.Entry<DownloadKey, Set<OnDownloadListener>> entry : mDownloadListeners.entrySet()) {
//                DownloadKey key = entry.getKey();
//                String url = key.url;
//                String cardUrl = key.cardUrl;
//                if (downloadConfig.url == null || downloadConfig.url.isEmpty() || url == null || url.isEmpty() || extra == null) {
//                    Log.d(TAG, "downloadConfig.url or url or extra 为空");
//                    continue;
//                }
//                if ((ViewUtil.checkUrlIsDownLoad(downloadConfig.url)
//                        && ViewUtil.checkUrlIsDownLoad(url)
//                        && ViewUtil.checkUrlIsDownLoad(cardUrl)
//                        && (downloadConfig.url.equals(url)) || downloadConfig.url.equals(cardUrl))) {
//                    if (!ViewUtil.equals(url, cardUrl)) {
////                        Log.d(TAG, "downloadConfig.url与url不相等 " + downloadConfig.url + "  " + url);
//                        continue;
//                    }
//                } else if (extra.type == DOWNLOAD_LOCAL) {//直接下载
//                    if (!downloadConfig.url.equals(url)) {
////                        Log.d(TAG, "downloadConfig.url与url不相等 " + downloadConfig.url + "  " + url);
//                        continue;
//                    }
//                } else if (extra.type == DOWNLOAD_LAND) {//落地页下载
//                    if (extra.originUrl == null || extra.originUrl.isEmpty()) continue;
////                    if (!downloadConfig.url.equals(url)) {
//                    DownloadSyncInfo downloadSyncInfo = DownloadManager.getInstance().getDownloadSyncInfo(url);
//                    if (downloadSyncInfo == null || downloadSyncInfo.config == null) {
//                        continue;
//                    }
//                    if (!url.equals(extra.originUrl) && !downloadConfig.url.equals(downloadSyncInfo.config.url)) {
////                        Log.d(TAG, "downloadConfig.url与url不相等 " + downloadConfig.url + "  " + url);
//                        continue;
//                    }
////                    }
//                }
//                Log.d(TAG, downloadStatus + " " + extra.type + "\n" + url + "\n" + downloadConfig.url + "\n" + extra.originUrl);
//                Set<OnDownloadListener> set = entry.getValue();
//                if (set == null || set.isEmpty()) continue;
//                //刷行 ui
//                for (OnDownloadListener downloadListener : set) {
////                    if (w == null || w.get() == null) continue;
////                    OnDownloadListener downloadListener = w.get();
//                    switch (downloadStatus) {
//                        case DOWNLOAD_START: {
//                            downloadListener.onStart(downloadConfig);
//                            break;
//                        }
//                        case DOWNLOAD_COMPLETE: {
//                            downloadListener.onSuccess(downloadConfig);
//                            break;
//                        }
//                        case DOWNLOAD_FAIL: {
//                            downloadListener.onFailure(downloadConfig);
//                            break;
//                        }
//                        case DOWNLOAD_ING: {
//                            downloadListener.onDownloading(downloadConfig);
//                            break;
//                        }
//                        case DOWNLOAD_PAUSE: {
//                            downloadListener.onPause(downloadConfig);
//                            break;
//                        }
//                        case DOWNLOAD_WAIT: {
//                            break;
//                        }
//                        case DOWNLOAD_STOP: {
//                            downloadListener.onStop(downloadConfig);
//                            break;
//                        }
//                        default:
//                            Log.d(TAG, "downloadStatus:" + downloadStatus + " url:" + downloadConfig.url);
//                            break;
//                    }
//                }
//                //上报埋点
//                OnDownloadListener downloadListener = set.iterator().next();
////                if (w == null || w.get() == null) continue;
////                OnDownloadListener downloadListener = w.get();
//                switch (downloadStatus) {
//                    case DOWNLOAD_START: {
//                        downloadListener.onReportStart(downloadConfig);
//                        break;
//                    }
//                    case DOWNLOAD_COMPLETE: {
//                        downloadListener.onReportSuccess(downloadConfig);
//                        break;
//                    }
//                    case DOWNLOAD_FAIL: {
//                        downloadListener.onReportFailure(downloadConfig);
//                        break;
//                    }
//                }
//
//            }
//        }
//    }
//    class ApkInstallReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent == null || intent.getData() == null) {
//                return;
//            }
//            String action = intent.getAction();
//            String packageName = intent.getData().getEncodedSchemeSpecificPart();
//            if (StringUtils.isEmpty(packageName) || mInstallListeners.isEmpty()) {
//                return;
//            }
//            Log.d(TAG, "action：" + action + " key:" + packageName);
//            Iterator<Map.Entry<String, Set<OnInstallListener>>> iterator = mInstallListeners.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, Set<OnInstallListener>> entry = iterator.next();
//                String pkg = entry.getKey();
////                Log.d(TAG, "action：" + action + " packageName:" + key + " " + packageName);
//                Set<OnInstallListener> set = entry.getValue();
//                if (set == null || set.isEmpty()) continue;
//
//                for (OnInstallListener listener : set) {
//                    if (packageName.equals(pkg)) {
//                        if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
//                            listener.onPackageAdded(packageName);
//                        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
//                            listener.onPackageRemoved(packageName);
//                        } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
//                            listener.onPackageReplaced(packageName);
//                        }
//                    }
//                }
//
//                //上报埋点
//                OnInstallListener listener = set.iterator().next();
//                if (Intent.ACTION_PACKAGE_ADDED.equals(action) && packageName.equals(pkg)) {
//                    listener.onReportPackageAdded(packageName);
//                }
//            }
////            unregisterInstallReceiver();
//        }
//    }
//
    public interface OnDownloadListener {
//        default void onDownloading(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        default void onPause(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        default void onStop(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        /**
//         * start:无论失败成功都会在开始的时候回调
//         * @param downloadConfig
//         */
//        default void onStart(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        /**
//         * finish：无论失败成功都会在结束的时候回调
//         * @param downloadConfig
//         */
//        default void onFinish(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        /**
//         * failure:下载失败回调
//         * @param downloadConfig
//         */
//        default void onFailure(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        /**
//         * success:下载成功才会回调
//         * @param downloadConfig
//         */
//        default void onSuccess(@NonNull DownloadConfig downloadConfig) {
//        }
//
//
//        default void onReportStart(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        default void onReportFailure(@NonNull DownloadConfig downloadConfig) {
//        }
//
//        default void onReportSuccess(@NonNull DownloadConfig downloadConfig) {
//        }
    }

    public interface OnInstallListener {

        default void onPackageAdded(String packageName) {
        }

        default void onPackageRemoved(String packageName) {
        }


        default void onPackageReplaced(String packageName) {
        }

        default void onReportPackageAdded(String packageName) {
        }

    }


    @MainThread
    public void addOnDownloadListener(String url,String cardUrl, OnDownloadListener downloadListener) {
        if (url == null || url.isEmpty() || downloadListener == null) return;
        DownloadKey downloadKey = new DownloadKey(url.trim(), cardUrl, null);
        Set<OnDownloadListener> set = mDownloadListeners.get(downloadKey);
        if (set == null) set = new LinkedHashSet<>();
        set.add(downloadListener);
        mDownloadListeners.put(downloadKey, set);
    }

    public  Map<DownloadKey, Set<OnDownloadListener>> getDownloadListeners(){
        return mDownloadListeners;
    }

    @MainThread
    public void removeOnDownloadListener(String url,OnDownloadListener downloadListener) {
        if (url == null || url.isEmpty() || downloadListener == null) return;
        Set<OnDownloadListener> set =  mDownloadListeners.get(url);
        if (set ==null)return;
        Iterator<OnDownloadListener> iterator = set.iterator();
        while (iterator.hasNext()){
            OnDownloadListener listener = iterator.next();
            if (downloadListener == listener){
                iterator.remove();
            }
        }
    }
//
//    /**
//     * 直接下载
//     */
//    public void startDownload(DownloadConfig downloadConfig) {
//    }
//    /**
//     * 落地页下载或者直接下载或者 302直接下载，取决于下载的地址
//     */
//    public void startDownload(String uri) {
//        if (uri == null || uri.isEmpty()) return;
//    }
//
//    public void pauseDownload(DownloadConfig downloadConfig) {
//    }
//    public void pauseDownload(String url,String url2) {
//        DownloadSyncInfo downloadSyncInfo = getDownloadSyncInfo(url,url2);
//        if (downloadSyncInfo == null || downloadSyncInfo.config == null) return;
//    }
//    public void resumeDownload(String url,String url2) {
//        DownloadSyncInfo downloadSyncInfo = getDownloadSyncInfo(url,url2);
//        if (downloadSyncInfo == null || downloadSyncInfo.config == null) return;
//    }
//    public DownloadApkInfo getDownloadApkInfoByOriginUrl(String url){
//        DownloadApkInfo appInfo = DownloadManager.getInstance().getDownloadApkInfoByOriginUrl(url);
//        return appInfo;
//    }
//
//    private boolean aEqualsB(String a, String b) {
//        if (a == null && b == null) return true;
//        return a != null && a.equals(b);
//    }
//    private String parseSignature(String uri) {
//        String ret = "";
//        try {
//            JSONObject json = UrlUtil.getUriParamsHelper(uri);
//            if (json != null) {
//                ret = json.optString(KEY_SIGNATURE);
//            }
//        } catch (Exception e) {
//            Log.d(TAG, Log.getStackTraceString(e));
//        }
//        return ret;
//    }
//    private void addUriParams(final DownloadConfig downloadConfig,final String signature) {
//        if (downloadConfig == null) return;
//        HashMap<String, Object> map = new HashMap<>();
//        if (signature != null && !signature.isEmpty()) {
//            map.put(KEY_SIGNATURE, signature);
//        }
//        downloadConfig.mExtra = addUriParams(downloadConfig.mExtra,map);
//    }
//    private String addUriParams(final String uri,@NonNull HashMap<String, Object> map) {
//        String uri2 = uri;
//        try {
//            JSONObject json = UrlUtil.getUriParamsHelper(uri);
//            if (json != null && !map.isEmpty()) {
//                for (Map.Entry<String, Object> entry : map.entrySet()) {
//                    json.put(entry.getKey(), entry.getValue());
//                }
//                uri2 = UrlUtil.replaceUriHelper(uri, json.toString());
//            }
//        } catch (Exception e) {
//            Log.d(TAG, e.getLocalizedMessage());
//        }
//        return uri2;
//    }
//    public DownloadApkInfo getDownloadApkInfoByOriginUrl(String url,String url2){
//        DownloadApkInfo appInfo = DownloadManager.getInstance().getDownloadApkInfoByOriginUrl(url);
//        if (appInfo ==null){
//            appInfo = DownloadManager.getInstance().getDownloadApkInfoByOriginUrl(url2);
//        }
//        return appInfo;
//    }
//    public DownloadSyncInfo getDownloadSyncInfo(String url,String url2){
//        DownloadSyncInfo d  = DownloadManager.getInstance().getDownloadSyncInfo(url);
//        if (d == null){
//            d = DownloadManager.getInstance().getDownloadSyncInfo(url2);
//        }
//        return d;
//    }
//
//    public void addOnInstallListener(String packageName, OnInstallListener listener) {
//        if (packageName == null || packageName.isEmpty() || listener == null) return;
//        Set<OnInstallListener> l = mInstallListeners.get(packageName);
//        if (l == null) l = new LinkedHashSet<>();
//        l.add(listener);
//        mInstallListeners.put(packageName.trim(), l);
//    }
//
//    public void removeOnInstallListener(String packageName, OnInstallListener onInstallListener) {
//        if (packageName == null || packageName.isEmpty()) return;
//        Set<OnInstallListener> set =  mInstallListeners.get(packageName);
//        if (set ==null)return;
//        Iterator<OnInstallListener> iterator = set.iterator();
//        while (iterator.hasNext()){
//            OnInstallListener listener = iterator.next();
//            if (onInstallListener == listener){
//                iterator.remove();
//            }
//        }
//    }
//
//    public boolean installApk(String filePath,String packageName) {
//        if (TextUtils.isEmpty(packageName)) return false;
//        if (PackageUtil.isInstallApk(, packageName)) {
//            return true;
//        }
//        return ApkUtil.installApk(,filePath);
//    }
//    public void registerInstallReceiver() {
//        if (mApkInstallReceiver == null) mApkInstallReceiver = new ApkInstallReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_INSTALL);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
//        intentFilter.addDataScheme("package");
//        .getApplication().registerReceiver(mApkInstallReceiver, intentFilter);
//    }
//    public void unregisterInstallReceiver() {
//        if (mApkInstallReceiver != null) {
//            .getApplication().unregisterReceiver(mApkInstallReceiver);
//            mApkInstallReceiver = null;
//        }
//    }
}
