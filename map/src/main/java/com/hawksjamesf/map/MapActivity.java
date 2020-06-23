package com.hawksjamesf.map;

import android.location.Location;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.hawksjamesf.map.service.LbsIntentServices;
import com.hawksjamesf.map.service.LbsServiceConnection;

import java.util.List;

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
    private LbsServiceConnection connection = new LbsServiceConnection();
    ILbsListener.Stub ibsListenerStub = new ILbsListener.Stub() {
        int getPid() {
            return android.os.Process.myPid();
        }

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


        public void onStatusChanged(String s, int i, Bundle bundle) throws RemoteException {
//            Log.d(TAG, "onStatusChanged:$s  $i $bundle")
        }

        public void onProviderEnabled(String s) throws RemoteException {
            Log.d(TAG, "onProviderEnabled:$s");
        }


        public void onProviderDisabled(String s) throws RemoteException {
            Log.d(TAG, "onProviderDisabled:$s");
        }
    };
    LatLng shanghai = new LatLng(31.02069, 121.780261);
    LatLng fuzhou = new LatLng(25.805453, 119.416011);
    LatLng myLocation = fuzhou;
    boolean is_import = false;
    boolean stopAutoSmoothToEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

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
                stopAutoSmoothToEnd =true;

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
            connection.sendImportMockJson();
        });
        mapViewModel.getAllLbsDatas().observe(this, p -> {
            bt_cellInfos.setText("不许点击喔：" + p.size());
            adapter.submitList(p);
            if (!stopAutoSmoothToEnd && p.size() > 5) {
                rv_cellinfos.smoothScrollToPosition(p.size() - 1);
            }
            stopAutoSmoothToEnd =false;
        });
        mapViewModel.getNeedUploadDatas().observe(this, p -> {
            if (p == null || p.size() == 0) return;
            Log.d(TAG, "getNeedUploadDatas: " + p.size());
            for (int i = 0; i < p.size(); i++) {
                LBS lbs = p.get(i);
                ReportApi.reportLocation(auth,lbs, new ReportApi.Callback() {
                    @Override
                    public void onFailure() {
                    }

                    @Override
                    public void onResponse() {
                        lbs.setNeedUpload(false);
                        mapViewModel.update(lbs);
                    }
                });

            }

        });

        srf_cellinfos.setOnRefreshListener(() -> srf_cellinfos.setRefreshing(false));
        connection.setListener(ibsListenerStub);
        //        Type jsonType = new TypeToken<List<L7_trip>>() {}.getType();
//        List<L7_trip> L7_1s = new Gson().fromJson(ResourceUtils.readAssets2String("l7_list_trip.json"), jsonType);
//        for (L7_trip l7_1 : L7_1s) {
//            addMarker(l7_1.getLat(), l7_1.getLon(), l7_1.getCid(), l7_1.getLac(),"南京");
//        }
        //如果您的应用在后台运行，它每小时只能接收几次位置信息更新
        LbsIntentServices.startAndBindService(this, connection);
//        LbsJobService.startService(this);
//        LbsJobIntentService.startService(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        mMapView.onDestroy();
        LbsIntentServices.stopAndUnbindService(this, connection);
        //        mapViewModel.clearAll();
        mapViewModel.clearMockData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        mMapView.onSaveInstanceState(outState);
    }
}