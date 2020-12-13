package com.hawksjamesf.mockserver.service;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.SPUtils;
import com.hawksjamesf.mockserver.BuildConfig;
import com.hawksjamesf.mockserver.Constants;
import com.hawksjamesf.mockserver.DispatcherImpl;
import com.hawksjamesf.mockserver.IMockApi;
import com.hawksjamesf.mockserver.IMockServerCallback;
import com.orhanobut.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Enumeration;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 * <p>
 * MockService的启动一定要快与有网络请求的组件，不然有网络请求的组件在连接MockServer时会报错导致crash
 * <p>
 * Note:
 * IntentService is subject to all the background execution limits imposed with Android 8.0 (API level 26).
 * In most cases, you are better off using JobIntentService,which uses jobs instead of services when running on Android 8.0 or higher.
 */
@Keep
public class MockIntentService extends IntentService {
    private static final String TAG = Constants.TAG + "/MockIntentService";

    private IMockApiImpl mBinder = new IMockApiImpl();
    IMockServerCallback callback;
    MockWebServer mockWebServer;
    DispatcherImpl dispatcher;
    MockRetrofit mockRetrofit;

    public static void bindAndStartService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MockIntentService.class);
        /**
         * flag:
         * int: Operation options for the binding. May be 0, BIND_AUTO_CREATE, BIND_DEBUG_UNBIND, BIND_NOT_FOREGROUND, BIND_ABOVE_CLIENT, BIND_ALLOW_OOM_MANAGEMENT, or BIND_WAIVE_PRIORITY.
         *
         * May be 0, 使用场景为Service已经running，Activity只是需要重新连接Service就可以用flag=0
         * BIND_AUTO_CREATE,
         *
         * 1）
         * bind Service时会自动创建Service，当Activity处于foreground时，为了raise Service进程的优先级，必须标明BIND_ADJUST_WITH_ACTIVITY；为了兼容老版本，
         * 没有使用BIND_AUTO_CREATE将被BIND_WAIVE_PRIORITY|BIND_ADJUST_WITH_ACTIVITY
         *
         * BIND_DEBUG_UNBIND,
         *用于debug 调用unbindService
         *
         * BIND_NOT_FOREGROUND,
         * 不允许Service所在的进程被设置为foreground
         *
         *
         * BIND_ABOVE_CLIENT,
         * 客户端先死
         *
         * BIND_ALLOW_OOM_MANAGEMENT,
         * 允许被进入oom的列表
         *
         * BIND_WAIVE_PRIORITY.
         * 对于有Service的进程来说，放弃了优先级策略，被放入background lru列表，就跟普通的应用一样，不会被加入oom的列表
         *
         *
         * 2）
         * BIND_ADJUST_WITH_ACTIVITY
         * 如果binding 是Activity，那么Service所在的进程的重要性将是基于Activity是否是对用户可见，可见客户端能提升服务端的优先级
         *
         * BIND_IMPORTANT 服务端进程可以被提升到foreground
         *
         *
         * Value is either 0 or combination of BIND_AUTO_CREATE, BIND_DEBUG_UNBIND, BIND_NOT_FOREGROUND（不能为foreground）,BIND_IMPORTANT（为foreground）， BIND_ABOVE_CLIENT（客户端先死）, BIND_ADJUST_WITH_ACTIVITY（客户端帮助服务端提升优先级，基于客户端的优先级），BIND_ALLOW_OOM_MANAGEMENT（允许加入oom管理）, BIND_WAIVE_PRIORITY（放弃加入oom管理，变成普通进程）.
         *
         */
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_ADJUST_WITH_ACTIVITY);
        activity.startService(intent);

    }

    public static void unbindAndStopService(@NonNull Context activity, ServiceConnection connection) {
        Intent intent = new Intent(activity, MockIntentService.class);
        activity.unbindService(connection);
        activity.stopService(intent);

    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MockIntentService() {
        super("mock_service");
        setIntentRedelivery(true);
        mockRetrofit = new MockRetrofit.Builder(
                new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .build())
                .networkBehavior(NetworkBehavior.create())
//                .backgroundExecutor()
                .build();
//        mockRetrofit.create(WeatherApi.class)
    }

    private static SSLContext sslContext(String keystoreFile, String password)
            throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream in = new FileInputStream(keystoreFile);
        try {
            keystore.load(in, password.toCharArray());
        } finally {
            Util.closeQuietly(in);
        }
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, password.toCharArray());

        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
                keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(),
                new SecureRandom());

        return sslContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        dispatcher = DispatcherImpl.getInstance(getApplicationContext());
        Logger.t(TAG).d("onCreate");
        MockService.bindAndStartService(this, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.t(TAG).d("onBind");
        return mBinder;
    }

    @WorkerThread
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start(getLocalInetAddress(), 50195);
//            mockWebServer.useHttps(sslContext("", "").getSocketFactory(), false);
//            mockWebServer.setBodyLimit(12);
//        } catch (GeneralSecurityException | IOException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //okhttp
        try {
            HttpUrl url = mockWebServer.url("/");
            SPUtils.getInstance().clear();
            SPUtils.getInstance().put(Constants.PRE_BASE_URL, url.toString());
            if (callback != null) {
                callback.onStartMockServer();
            }
            Logger.t(TAG).i("onHandleIntent--->" + "url:" + url + " , " + getLocalIP() + ":" + mockWebServer.getPort());
            mockWebServer.setDispatcher(dispatcher);


        } catch (Exception e) {
            e.printStackTrace();
            Logger.t(TAG).e(e.getMessage());
        }
        //retrofit


    }

    /**
     * @param intent
     * @param flags   int: Additional data about this start request.
     *                Value is either 0 or combination of START_FLAG_REDELIVERY or START_FLAG_RETRY.
     *                0：
     *                <p>
     *                START_FLAG_REDELIVERY：
     *                <p>
     *                START_FLAG_RETRY：
     * @param startId
     * @return Value is START_STICKY_COMPATIBILITY, START_STICKY, START_NOT_STICKY or START_REDELIVER_INTENT.
     * <p>
     * START_STICKY- tells the system to create a fresh copy of the service, when sufficient memory is available, after it recovers from low memory. Here you will lose the results that might have computed before.
     * <p>
     * START_NOT_STICKY- tells the system not to bother to restart the service, even when it has sufficient memory.
     * <p>
     * START_REDELIVER_INTENT- tells the system to restart the service after the crash and also redeliver the intents that were present at the time of crash.
     */
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        int r = super.onStartCommand(intent, flags, startId);
        Logger.t(TAG).d("startId:"+startId);
        return r;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.t(TAG).d("onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public class IMockApiImpl extends IMockApi.Stub {

        @Override
        public void register(IMockServerCallback callback) throws RemoteException {
            MockIntentService.this.callback = callback;
        }
    }

    String getLocalIP() {
        return getLocalInetAddress() == null ? "" : getLocalInetAddress().getHostAddress();
    }

    InetAddress getLocalInetAddress() {
//        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//        Logger.t(TAG).i(NetworkUtils.getIpAddressByWifi()+"/"+NetworkUtils.getIPAddress(true)+"/"+NetworkUtils.getBroadcastIpAddress());

        InetAddress result = null;
        Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address.isSiteLocalAddress()) {
                            return address;
                        } else if (result == null) {
                            result = address;
                        }
                    }
                }
            }
            return (result != null ? result : InetAddress.getLocalHost());

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
