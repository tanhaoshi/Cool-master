package mvp.cool.master.mvp.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.services.core.PoiItem;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.callback.RimPoiSearchTask;
import mvp.cool.master.layout.layoutmanager.DriverItemDecoration;
import mvp.cool.master.layout.layoutmanager.VerticalLayoutManager;
import mvp.cool.master.mvp.ui.activity.CarRepairMapActivity;
import mvp.cool.master.mvp.ui.activity.NearByOizlActivity;
import mvp.cool.master.mvp.ui.adapter.VehicleRepailAdapter;
import mvp.cool.master.mvp.ui.fragment.base.BaseFragment;
import mvp.cool.master.utils.GildeImageLoader;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/17.
 */

public class RimFragmnet extends BaseFragment implements LocationSource, AMapLocationListener,RimPoiSearchTask.RimPoiSearchData{

    @BindView(R.id.rimLocation)
    TextView rimLocation;
    @BindView(R.id.rimRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rimOizl)
    TextView rimOizl;
    @BindView(R.id.repailCar)
    TextView repailCar;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private String strText;
    private String city ;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener = null;

    private Double amapLat;
    private Double amapLong;
    private RimPoiSearchTask mRimPoiSearchTask;
    private VehicleRepailAdapter mVehicleRepailAdapter;

    private int[] image = new int[]{R.drawable.beach , R.drawable.beijing, R.drawable.milu};
    private ArrayList<Integer> imagesList = new ArrayList<>();

    @Override
    protected void initComponent() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_rim;
    }

    @Override
    protected void initView(View view) {
        requestPermiSsiongs();
        initBananer();
    }

    @Override
    public void showProgress(boolean isTrue) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    private void requestPermiSsiongs() {
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.yellow),getResources().getColor(R.color.bank));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE}, 1);
            initLoc();
        }else{
            initLoc();
        }

        initListener();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                amapLat = amapLocation.getLatitude();//获取纬度
                amapLong = amapLocation.getLongitude();//获取经度
                rimLocation.setText(amapLocation.getAddress());
                strText = "汽修店";
                city =  amapLocation.getCity();
                startSearch();
                Toast.makeText(App.getInstance(),amapLocation.getAddress(),Toast.LENGTH_SHORT).show();
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

    @Override
    public void getRimPoiSearch(List<PoiItem> list) {
        initAdapter(list);
    }

    private void initAdapter(List<PoiItem> list){
        mRecyclerView.setLayoutManager(new VerticalLayoutManager(App.getInstance()));
        mRecyclerView.addItemDecoration(new DriverItemDecoration(getActivity() ,DriverItemDecoration.VERTICAL_LIST));
        mRecyclerView.setNestedScrollingEnabled(false);
        mVehicleRepailAdapter = new VehicleRepailAdapter(list);
        mRecyclerView.setAdapter(mVehicleRepailAdapter);
    }

    private void startSearch(){
        mRimPoiSearchTask =  RimPoiSearchTask.getInstance(App.getInstance());
        mRimPoiSearchTask.onSearch(strText,city,amapLat,amapLong);
        mRimPoiSearchTask.setRimPoiSearchListener(this);
    }

    @OnClick({R.id.rimOizl , R.id.repailCar})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rimOizl:
                getActivity().startActivity(new Intent(getActivity(), NearByOizlActivity.class));
                break;
            case R.id.repailCar:
                getActivity().startActivity(new Intent(getActivity() , CarRepairMapActivity.class));
                break;
        }
    }

    private void initBananer(){
        bannerData();
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(4500);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImages(imagesList).setImageLoader(new GildeImageLoader()).start();
    }

    private ArrayList bannerData(){
        for(int i:image){
            imagesList.add(i);
        }
        return imagesList;
    }

    private void initListener(){
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLoc();
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(mBanner != null){
                mBanner.startAutoPlay();
            }
        }else if(mBanner != null){
            mBanner.stopAutoPlay();
        }
    }
}
