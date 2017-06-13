package mvp.cool.master.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.callback.AmapSetMakerTask;
import mvp.cool.master.mvp.bean.OizlModel;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;
import mvp.cool.master.mvp.ui.adapter.PoitemAdapter;

public class NearByOizlActivity extends BaseActivity implements LocationSource,AMapLocationListener
        ,AmapSetMakerTask.AmapSetMakerTaskImpl , AMap.InfoWindowAdapter{

    @BindView(R.id.map)
    MapView mMapView;

    private AMap mAMap;
    private MyLocationStyle mMyLocationStyle;
    private UiSettings mUiSettings;
    private Double amapLat;
    private Double amapLong;
    private AmapSetMakerTask mAmapSetMakerTask;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener = null;
    private List<PoiItem> mItemList = new ArrayList<>();
    private List<OizlModel> mModelList = new ArrayList<>();
    private boolean isCome = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_near_by_oizl;
    }

    @Override
    protected void initView() {
        initTitle();
        requestPermiSsiongs();
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    private void initTitle(){
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("附近加油");
        base_right.setVisibility(View.VISIBLE);
        base_right.setText("列表");
    }

    private void initLocationStyle(){
        mMyLocationStyle = new MyLocationStyle();
        mMyLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        mMyLocationStyle.interval(2000);
        mMyLocationStyle.radiusFillColor(getResources().getColor(R.color.touming));
        mMyLocationStyle.strokeColor(getResources().getColor(R.color.touming));
        mMyLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.isme)));
        mAMap.setMyLocationStyle(mMyLocationStyle);
        mAMap.setMyLocationEnabled(true);
        mMyLocationStyle.showMyLocation(true);
    }

    private void initUISetings(){
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(true);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                amapLat = amapLocation.getLatitude();//获取纬度
                amapLong = amapLocation.getLongitude();//获取经度
                cameraUpdate(amapLat , amapLong);
                startGetMarker("加油站",amapLocation.getCity());
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

                Toast.makeText(App.getInstance(), "请打开GPS与数据连接(网络设置)!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
           mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
         mListener = null;
    }

    private void initLoc() {
        mLocationClient = new AMapLocationClient(App.getInstance());
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setWifiActiveScan(true);
        mLocationOption.setMockEnable(false);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    private void cameraUpdate(double var1 , double var2){
        CameraUpdate mCameraUpdate = CameraUpdateFactory
                .newCameraPosition(new CameraPosition(new LatLng(var1,var2),18,0,0));
        mAMap.moveCamera(mCameraUpdate);
        initLocationStyle();
    }

    private void startGetMarker(String cureent,String city){
        mAmapSetMakerTask = AmapSetMakerTask.getInstance(App.getInstance());
        mAmapSetMakerTask.onSearch(cureent,city ,amapLat,amapLong);
        mAmapSetMakerTask.setAmapSerMakerListener(this);
    }

    @Override
    public void getRimPoiSearch(List<PoiItem> list) {
        mItemList = list;
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        for(int i = 0; i<= list.size(); i++){
            if(i<7){
                setGhlapMarketGreen(list.get(i),i);
            }else{
                setGhlapMarker(list.get(i),i);
            }
        }
    }

    private void setGhlapMarker(PoiItem poiItem , int count){
        //社会
        LatLng latLng = new LatLng( poiItem.getLatLonPoint().getLatitude() , poiItem.getLatLonPoint().getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(poiItem.getTitle()+"|"+poiItem.getTypeDes()).snippet(poiItem.getCityName()
                 + poiItem.getAdName() + poiItem.getSnippet() + "|" + poiItem.getTel());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMyBitmap(count+"")));
        Marker marker = mAMap.addMarker(markerOptions);
        initUISetings();
        mAMap.setInfoWindowAdapter(this);
    }

    private void setGhlapMarketGreen(PoiItem poiItem , int count){
        //合作商家
        LatLng latLng = new LatLng( poiItem.getLatLonPoint().getLatitude() , poiItem.getLatLonPoint().getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(poiItem.getTitle()+"|"+poiItem.getTypeDes()).snippet(poiItem.getCityName()
                 + poiItem.getAdName() + poiItem.getSnippet() + "|" + poiItem.getTel());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getGreenBitmap(count+"")));
        Marker marker = mAMap.addMarker(markerOptions);
        initUISetings();
        mAMap.setInfoWindowAdapter(this);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    View infoWindow = null;

    @Override
    public View getInfoContents(Marker marker) {
        if(infoWindow == null) {
            infoWindow = LayoutInflater.from(this).inflate(
                    R.layout.nearby_hezuo_style, null);
        }
        render(marker, infoWindow);
        return infoWindow;
    }

    public void render(Marker marker, final View view) {
        TextView windowTitle = (TextView)view.findViewById(R.id.oizlTitle);
        windowTitle.setText(marker.getTitle().substring(0 , marker.getTitle().indexOf("|")));
        TextView windowSnippet = (TextView)view.findViewById(R.id.spnniter);
        windowSnippet.setText(marker.getTitle().substring(marker.getTitle().indexOf("|")+1,marker.getTitle().length()));
        TextView windowMobile = (TextView)view.findViewById(R.id.oizlMobile);
        windowMobile.setText(marker.getSnippet().substring(marker.getSnippet().lastIndexOf("|")+1,marker.getSnippet().length()));
        TextView windowAddres = (TextView)view.findViewById(R.id.oizlAddares);
        windowAddres.setText(marker.getSnippet().substring(0,marker.getSnippet().lastIndexOf("|")));
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.view);
        initData(recyclerView);
        AMap.OnInfoWindowClickListener listener = new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
//                ViewGroup viewGroup = (ViewGroup)view.inflate(App.getInstance(),R.layout.style_customview,null);
//                final ConfigBean bean = StyledDialog.buildCustom(viewGroup, Gravity.CENTER);
//                final Dialog dialog = bean.show();
//                dialog.show();
                Intent intent = new Intent(NearByOizlActivity.this,NameTitlePayActivity.class);
                startActivity(intent);
            }
        };
        mAMap.setOnInfoWindowClickListener(listener);
    }

    protected Bitmap getMyBitmap(String pm_val) {
        Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                R.drawable.rimzillocation).getBitmap();
        bitmap = Bitmap.createBitmap(bitmap, 0 ,0, bitmap.getWidth(),
                bitmap.getHeight());
        Canvas canvas = new Canvas(bitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(32);
        textPaint.setColor(getResources().getColor(R.color.white));
        // 18 35
        canvas.drawText(pm_val, 50, 60,textPaint);// 设置bitmap上面的文字位置
        return bitmap;
    }

    private Bitmap getGreenBitmap(String pm_val){
        Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                R.drawable.rimzillgreen).getBitmap();
        bitmap = Bitmap.createBitmap(bitmap, 0 ,0, bitmap.getWidth(),
                bitmap.getHeight());
        Canvas canvas = new Canvas(bitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(32);
        textPaint.setColor(getResources().getColor(R.color.white));
        // 18 35
            canvas.drawText(pm_val, 50, 60,textPaint);// 设置bitmap上面的文字位置
        return bitmap;
    }

    @OnClick({R.id.base_tv_toolbar_right , R.id.base_iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_tv_toolbar_right:
                jumpOizlRim();
                break;
            case R.id.base_iv_back:
                finish();
                break;
        }
    }

    private void jumpOizlRim(){
        Intent intent = new Intent(this , RimOizlActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("location",(Serializable) mItemList);
        intent.putExtra("list",bundle);
        startActivity(intent);
    }

    private void requestPermiSsiongs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE}, 1);
            initLoc();
        }else{
            initLoc();
        }
    }

    private void initData(RecyclerView recyclerView){
        if(isCome){
            mModelList.add(new OizlModel("0#" , "6.29"));
            mModelList.add(new OizlModel("93#" , "6.39"));
            mModelList.add(new OizlModel("92#" , "6.09"));
            mModelList.add(new OizlModel("95#" , "6.19"));
            mModelList.add(new OizlModel("97#" , "6.59"));
            mModelList.add(new OizlModel("94#" , "6.111"));
            initAdapter(recyclerView);
            isCome = false;
        }
    }

    private void initAdapter(RecyclerView recyclerView){
        List<OizlModel> list = null;
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getInstance());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        if(mModelList.size()>5){
            list = new ArrayList<>();
            for(int i=0;i<4;i++){
                list.add(mModelList.get(i));
            }
            list.add(new OizlModel("......",""));
            PoitemAdapter poitemAdapter = new PoitemAdapter(list);
            recyclerView.setAdapter(poitemAdapter);
        }else{
            PoitemAdapter poitemAdapter = new PoitemAdapter(mModelList);
            recyclerView.setAdapter(poitemAdapter);
        }
    }

}
