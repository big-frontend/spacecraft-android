package com.hawksjamesf.map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/10/2019  Thu
 */
public class MapActivity extends PermissionsActivity {
    private MapView mMapView;
    private TextureMapView textureMapView;
    private MyLocationStyle myLocationStyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //获取地图控件引用
//        mMapView = findViewById(R.id.map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//        mMapView.onCreate(savedInstanceState);
//        AMap map = mMapView.getMap();
        textureMapView = findViewById(R.id.map_texture);
        textureMapView.onCreate(savedInstanceState);
        AMap map = textureMapView.getMap();
        LatLng shanghai = new LatLng(31.02069, 121.780261);
        map.moveCamera(CameraUpdateFactory.changeLatLng(shanghai));
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.interval(2000);
        //设置定位蓝点的Style
//        settings.setScrollGesturesEnabled(true);
//        map.getUiSettings().setScrollGesturesEnabled(true);
//        settings.setAllGesturesEnabled(true);
        UiSettings settings = map.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
//        map.setMyLocationEnabled(true);
        map.setMyLocationStyle(myLocationStyle);
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.showMyLocation(true);

        String readAssets2String = ResourceUtils.readAssets2String("l7.json");
        Resp resp = new Gson().fromJson(readAssets2String, Resp.class);
        for (Resp.ResultsBean resultsBean : resp.getResults()) {
            double lat = resultsBean.getAppLocation().getLat();
            double lon = resultsBean.getAppLocation().getLon();
            int cid = resultsBean.getAppCellInfos().get(0).get(0).getCid();
            int lac = resultsBean.getAppCellInfos().get(0).get(0).getLac();
            LatLng latLng = new LatLng(lat, lon);
            Geocoder geocoder = new Geocoder(this, Locale.CHINESE);
            String add="";
            try {
                List<Address> fromLocation = geocoder.getFromLocation(lat, lon, 1);
                Address address = fromLocation.get(0);
                int maxAddressLineIndex = address.getMaxAddressLineIndex();
                if (maxAddressLineIndex >=2){
                    add = address.getAddressLine(0)+address.getAddressLine(1);
                }else {
                    add = address.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.addMarker(new MarkerOptions().position(latLng).title(add).snippet(latLng.toString() + "\ncid/lac:("+ cid +","+ lac+")"));
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        mMapView.onDestroy();
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