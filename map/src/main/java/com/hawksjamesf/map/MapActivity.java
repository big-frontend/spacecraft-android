package com.hawksjamesf.map;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.hawksjamesf.map.model.AppCellInfo;
import com.hawksjamesf.map.model.AppLocation;
import com.hawksjamesf.map.model.LBS;
import com.hawksjamesf.map.model.MapViewModel;
import com.hawksjamesf.map.service.ILbsApiClient;
import com.hawksjamesf.uicomponent.widget.HeadBubbleView;
import com.hawksjamesf.uicomponent.widget.HeartLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/10/2019  Thu
 */
public class MapActivity extends LBSActivity {
    private MapView mMapView;
    private TextureMapView textureMapView;
    private MyLocationStyle myLocationStyle;
    AMap map;
    MapViewModel mapViewModel;
    Button bt_cellInfos;
    Button bt_mylocation;
    Button bt_import;
    ILbsApiClient iLbsApiClient;

    ILbsApiClient.Listener ibsListener = new ILbsApiClient.Listener(){
        public void onLocationChanged(AppLocation appLocation, List<AppCellInfo> appCellInfos, long count) throws RemoteException {
            StringBuffer s = new StringBuffer();
            AppCellInfo appCellInfo = null;
            if (appCellInfos != null && appCellInfos.size() > 0) {
                for (int index = 0; index < appCellInfos.size(); index++) {
                    s.append("${appCellInfos?.get(index)?.lac},${appCellInfos?.get(index)?.cid}  ");
                    AppCellInfo theCell = appCellInfos.get(index);
                    if (theCell.isRegistered) {
                        appCellInfo = theCell;
                    }
                }
            }
            Log.d(TAG, "onLocationChanged:index:" + count + "\n" +
                    "${appLocation?.lat},${appLocation?.lon}\n" + s);
            boolean needUpload;
            if (appCellInfo == null) {
                needUpload = false;
            } else {
                needUpload = !appCellInfo.isMockData && !appLocation.isMockData;
            }
            mapViewModel.insert(needUpload, appCellInfo, appLocation);
            MapUtil.addMarker(map, appLocation, appCellInfo);
        }
    };
    AMapLocationListener aMapLocationListener =new AMapLocationListener() {
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    int locationType = amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    double latitude = amapLocation.getLatitude();//获取纬度
                    double longitude = amapLocation.getLongitude();//获取经度
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    String locationTypeStr = "";
                    switch (locationType) {
//                            case 0:{locationTypeStr = "定位失败";}
                        case 1: { locationTypeStr = "GPS定位结果";break; }
                        case 2: { locationTypeStr = "前次定位结果";break; }
//                            case 3:{locationTypeStr = "缓存定位结果";}
                        case 4: { locationTypeStr = "缓存定位结果";break; }
                        case 5: { locationTypeStr = "Wifi定位结果";break; }
                        case 6: { locationTypeStr = "基站定位结果";break; }
//                            case 7:{locationTypeStr = "离线定位结果";}
                        case 8: { locationTypeStr = "离线定位结果";break; }
                        case 9: { locationTypeStr = "最后位置缓存";break; }
                    }
                    Log.d(TAG, "onLocationChanged: locationType:" + locationTypeStr + " lat,lon:" + latitude + " , " + longitude + "");
                    List<AppCellInfo> appCellInfos = new ArrayList<>();
                    for (CellInfo cell : telephonyManager.getAllCellInfo()) {
                        appCellInfos.add(AppCellInfo.convertSysCellInfo(cell));
                    }
                    AppCellInfo appCellInfo = null;
                    if (appCellInfos.size() > 0) {
                        for (int index = 0; index < appCellInfos.size(); index++) {
                            AppCellInfo theCell = appCellInfos.get(index);
                            if (theCell.isRegistered) {
                                appCellInfo = theCell;
                            }
                        }
                    }
                    AppLocation appLocation = AppLocation.convertSysLocation(amapLocation);
                    boolean needUpload;
                    if (appCellInfo == null) {
                        needUpload = false;
                    } else {
                        needUpload = !appCellInfo.isMockData && !appLocation.isMockData;
                    }
                    mapViewModel.insert(needUpload, appCellInfo, appLocation);
                    MapUtil.addMarker(map, appLocation, appCellInfo);

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e(TAG, "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }

            }
        }
    };
    LatLng shanghai = new LatLng(31.02069, 121.780261);
    LatLng fuzhou = new LatLng(25.805453, 119.416011);
    LatLng myLocation = fuzhou;
    boolean is_import = false;
    boolean stopAutoSmoothToEnd = false;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        telephonyManager= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            realRequestLocationForAmap(getIntent());
        }
        //获取地图控件引用
//        mMapView = findViewById(R.id.map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//        mMapView.onCreate(savedInstanceState);
//        AMap map = mMapView.getMap();
        textureMapView = findViewById(R.id.map_texture);
        textureMapView.onCreate(savedInstanceState);
        map = textureMapView.getMap();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
                //参数1---要移动到的经纬度，参数2---地图的放缩级别zoom，参数3---地图倾斜度，参数4---地图的旋转角度
                new CameraPosition(myLocation, 4, 0, 0));
        map.moveCamera(cameraUpdate);
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.interval(2000);
        //设置定位蓝点的Style
//        settings.setScrollGesturesEnabled(true);
//        settings.setAllGesturesEnabled(true);
        UiSettings settings = map.getUiSettings();
        settings.setMyLocationButtonEnabled(false);
        settings.setZoomControlsEnabled(false);
        settings.setRotateGesturesEnabled(false);
//        settings.setZoomInByScreenCenter(false);
        map.setMyLocationEnabled(true);
        map.setMyLocationStyle(myLocationStyle);
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.showMyLocation(false);


        HeadBubbleView hbv_heartbeat = findViewById(R.id.hbv_heartbeat);
        List<HeadBubbleView.BrowseEntity> be = new ArrayList<HeadBubbleView.BrowseEntity>() {
            {
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_black_24dp, "启动1"));
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_black_24dp, "启动1"));
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_black_24dp, "启动1"));
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_black_24dp, "启动1"));
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_black_24dp, "启动1"));
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_border_black_24dp, "启动1"));
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_border_black_24dp, "启动1"));
                add(new HeadBubbleView.BrowseEntity(R.drawable.ic_favorite_border_black_24dp, "启动1"));
            }
        };
        hbv_heartbeat.setDatas(be);
        hbv_heartbeat.startAnimation(4000);
        hbv_heartbeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hbv_heartbeat.startAnimation(2000);

            }
        });
        HeartLayout heartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        findViewById(R.id.member_send_good).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartLayout.addFavor();
            }
        });
        RecyclerView rv_cellinfos = findViewById(R.id.rv_cellinfos);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                mapViewModel.delete(adapterPosition);
                stopAutoSmoothToEnd = true;

            }
        });
        itemTouchHelper.attachToRecyclerView(rv_cellinfos);
        MapAdapter adapter = new MapAdapter(this, map);
        rv_cellinfos.setAdapter(adapter);
        rv_cellinfos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        SwipeRefreshLayout srf_cellinfos = findViewById(R.id.srf_cellinfos);
        bt_cellInfos = findViewById(R.id.bt_cellInfos);
        bt_cellInfos.setOnClickListener(v -> Toast.makeText(MapActivity.this, "是不是傻,不让点你还敢点", Toast.LENGTH_LONG).show());
        bt_mylocation = findViewById(R.id.bt_mylocation);
        bt_mylocation.setOnClickListener(v -> {
            Location myLocation = map.getMyLocation();
            double mylatitude = myLocation.getLatitude();
            double mylongitude = myLocation.getLongitude();
            Log.d(TAG, "mylocation: " + mylatitude + "," + mylongitude);
            this.myLocation = new LatLng(mylatitude, mylongitude);
            map.animateCamera(CameraUpdateFactory.newCameraPosition(
                    //参数1---要移动到的经纬度，参数2---地图的放缩级别zoom，参数3---地图倾斜度，参数4---地图的旋转角度
                    new CameraPosition(this.myLocation, 9, 0, 0)));

        });
        bt_import = findViewById(R.id.bt_import);
        bt_import.setOnClickListener(view -> {
            if (is_import) {
                Toast.makeText(MapActivity.this, "蛋已经领取啦，咋还想领", Toast.LENGTH_SHORT).show();
                return;
            }
            is_import = true;
            Toast.makeText(MapActivity.this, "彩蛋,正在导入数据", Toast.LENGTH_SHORT).show();
            iLbsApiClient.sendImportMockJson();
        });
        mapViewModel.getAllLbsDatas().observe(this, p -> {
            bt_cellInfos.setText("不许点击喔：" + p.size());
            adapter.submitList(p);
            if (!stopAutoSmoothToEnd && p.size() > 5) {
                rv_cellinfos.smoothScrollToPosition(p.size() - 1);
            }
            stopAutoSmoothToEnd = false;
        });
        mapViewModel.getNeedUploadDatas().observe(this, p -> {
            if (p == null || p.size() == 0) return;
            Log.d(TAG, "getNeedUploadDatas: " + p.size());
            for (int i = 0; i < p.size(); i++) {
                LBS lbs = p.get(i);
//                ReportApi.reportLocation(auth, lbs, new ReportApi.Callback() {
//                    @Override
//                    public void onFailure() {
//                    }
//
//                    @Override
//                    public void onResponse() {
//                        lbs.setNeedUpload(false);
//                        mapViewModel.update(lbs);
//                    }
//                });

            }

        });

        srf_cellinfos.setOnRefreshListener(() -> srf_cellinfos.setRefreshing(false));
    }
    public static final int ONGOING_NOTIFICATION_ID = 100;
    public static final String channelId = "channelId";
    public static final String channelName = "channelName";
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void realRequestLocationForAmap(Intent intent) {
        mlocationClient = new AMapLocationClient(this);
        mlocationClient.setLocationListener(aMapLocationListener);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(20*1000);
        mlocationClient.setLocationOption(mLocationOption);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        Intent notificationIntent = new Intent(this, MapActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notificationManager.createNotificationChannel(chan);
        Notification notification = new Notification.Builder(this, channelId)
                .setContentTitle("this is title")
                .setContentText("this is text")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .setTicker("this is ticker")
                .build();
        mlocationClient.enableBackgroundLocation(12324,notification);
        mlocationClient.startLocation();

        iLbsApiClient=new ILbsApiClient(this);
        iLbsApiClient.startLocation();
//        iLbsApiClient.setListener(ibsListener);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        mMapView.onDestroy();
        //        mapViewModel.clearAll();
        mapViewModel.clearMockData();
        mlocationClient.stopLocation();
        iLbsApiClient.stopLocation();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        mMapView.onResume();
        Log.d(TAG, "sha1:" + sHA1(this));

    }

    public String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//        mMapView.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        mMapView.onSaveInstanceState(outState);
        mapViewModel.clearMockData();
    }
}
