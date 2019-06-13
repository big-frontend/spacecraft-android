package com.hawksjamesf.mockserver;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.NetworkUtils;
import com.hawksjamesf.common.util.TextUtil;
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

import androidx.annotation.Nullable;
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
 */
public class MockService extends IntentService {
    private static final String TAG = "MockService";

    private IMockApiImpl mBinder = new IMockApiImpl();
    IMockServerCallback callback;
    MockWebServer mockWebServer;
    DispatcherImpl dispatcher;
    MockRetrofit mockRetrofit;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MockService() {
        super("mock_service");
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.useHttps(sslContext("","").getSocketFactory(), false);
            mockWebServer.setBodyLimit(12);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
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
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.t(TAG).d("onBind");
        return mBinder;
    }

    //work thread
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //okhttp
        try {
//            mockWebServer.start();
            mockWebServer.start(50195);
            HttpUrl url;
            if (TextUtil.isEmpty(NetworkUtils.getIpAddressByWifi())) {
                url = mockWebServer.url("/");
            } else {
                url = mockWebServer.url(NetworkUtils.getIpAddressByWifi());
            }
            Logger.t(TAG).i(url.toString());
//            SPUtils.getInstance().clear();
//            SPUtils.getInstance().put(Constants.PRE_BASE_URL, mockWebServer.url("/").toString());

            if (callback != null) {
                callback.onStartMockServer();
            }
            mockWebServer.setDispatcher(dispatcher);
//            mockWebServer.enqueue();


        } catch (Exception e) {
            e.printStackTrace();
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
//    @Override
//    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        return Service.START_STICKY;
//    }
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
            MockService.this.callback = callback;
        }
    }

    String getLocalIP() {
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
                            return address.getHostAddress();
                        } else if (result == null) {
                            result = address;
                        }
                    }
                }
            }
            return (result != null ? result : InetAddress.getLocalHost()).getHostAddress();

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }
}
