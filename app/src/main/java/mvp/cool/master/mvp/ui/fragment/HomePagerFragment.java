package mvp.cool.master.mvp.ui.fragment;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
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
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.callback.PoiSearchTask;
import mvp.cool.master.layout.layoutmanager.DriverItemDecoration;
import mvp.cool.master.layout.layoutmanager.VerticalLayoutManager;
import mvp.cool.master.mvp.ui.adapter.PoiSearchAdapter;
import mvp.cool.master.mvp.ui.fragment.base.BaseFragment;
import mvp.cool.master.utils.GildeImageLoader;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/17.
 */
public class HomePagerFragment extends BaseFragment implements
        LocationSource, AMapLocationListener ,PoiSearchTask.PoiSearchData {

    @BindView(R.id.laundryHome)
    TextView laundryHome;
    @BindView(R.id.parkingHome)
    TextView parkingHome;
    @BindView(R.id.bankHome)
    TextView bankHome;
    @BindView(R.id.superMaketHome)
    TextView superMaketHome;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private String strText;
    private String city;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener = null;

    private Double amapLat;
    private Double amapLong;
    private PoiSearchTask mPoiSearchTask;
    private PoiSearchAdapter mPoiSearchAdapter;

    private int[] image = new int[]{R.drawable.beach , R.drawable.beijing, R.drawable.milu};
    private ArrayList<Integer> imagesList = new ArrayList<>();

    private static AHBottomNavigation mNavigation;

    public static HomePagerFragment newInstance(AHBottomNavigation ahBottomNavigation){
        HomePagerFragment fragment = new HomePagerFragment();
        mNavigation = ahBottomNavigation;
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_homepager;
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

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                amapLat = amapLocation.getLatitude();//获取纬度
                amapLong = amapLocation.getLongitude();//获取经度
                city = amapLocation.getCity();
                initFirastPoi();
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

    //定位
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

    @OnClick({R.id.laundryHome ,R.id.parkingHome ,R.id.bankHome ,R.id.superMaketHome })
    public void onClick(View view){
        switch (view.getId()){
            case R.id.laundryHome:
                laundryHome.setTextColor(getResources().getColor(R.color.white));
                laundryHome.setBackgroundColor(getResources().getColor(R.color.lanudry));
                parkingHome.setTextColor(getResources().getColor(R.color.parking));
                parkingHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_tingche));
                bankHome.setTextColor(getResources().getColor(R.color.bank));
                bankHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_bank));
                superMaketHome.setTextColor(getResources().getColor(R.color.superMaket));
                superMaketHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_supermark));
                strText = laundryHome.getText().toString();
                PoiSearchTask.getInstance(App.getInstance()).onSearch(strText,city ,amapLat,amapLong);
                break;
            case R.id.parkingHome:
                laundryHome.setTextColor(getResources().getColor(R.color.lanudry));
                laundryHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_xiyi));
                parkingHome.setTextColor(getResources().getColor(R.color.white));
                parkingHome.setBackgroundColor(getResources().getColor(R.color.parking));
                bankHome.setTextColor(getResources().getColor(R.color.bank));
                bankHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_bank));
                superMaketHome.setTextColor(getResources().getColor(R.color.superMaket));
                superMaketHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_supermark));
                strText = parkingHome.getText().toString();
                PoiSearchTask.getInstance(App.getInstance()).onSearch(strText,city ,amapLat,amapLong);
                break;
            case R.id.bankHome:
                laundryHome.setTextColor(getResources().getColor(R.color.lanudry));
                laundryHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_xiyi));
                parkingHome.setTextColor(getResources().getColor(R.color.parking));
                parkingHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_tingche));
                bankHome.setTextColor(getResources().getColor(R.color.white));
                bankHome.setBackgroundColor(getResources().getColor(R.color.bank));
                superMaketHome.setTextColor(getResources().getColor(R.color.superMaket));
                superMaketHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_supermark));
                strText = bankHome.getText().toString();
                PoiSearchTask.getInstance(App.getInstance()).onSearch(strText,city ,amapLat,amapLong);
                break;
            case R.id.superMaketHome:
                laundryHome.setTextColor(getResources().getColor(R.color.lanudry));
                laundryHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_xiyi));
                parkingHome.setTextColor(getResources().getColor(R.color.parking));
                parkingHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_tingche));
                bankHome.setTextColor(getResources().getColor(R.color.bank));
                bankHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_bank));
                superMaketHome.setTextColor(getResources().getColor(R.color.white));
                superMaketHome.setBackgroundColor(getResources().getColor(R.color.superMaket));
                strText = superMaketHome.getText().toString();
                PoiSearchTask.getInstance(App.getInstance()).onSearch(strText,city ,amapLat,amapLong);
                break;
        }
    }

    private void initFirastPoi(){
        laundryHome.setTextColor(getResources().getColor(R.color.white));
        laundryHome.setBackgroundColor(getResources().getColor(R.color.lanudry));
        parkingHome.setTextColor(getResources().getColor(R.color.parking));
        parkingHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_tingche));
        bankHome.setTextColor(getResources().getColor(R.color.bank));
        bankHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_bank));
        superMaketHome.setTextColor(getResources().getColor(R.color.superMaket));
        superMaketHome.setBackground(getResources().getDrawable(R.drawable.homepager_selector_supermark));
        strText = laundryHome.getText().toString();
        PoiSearchTask.getInstance(App.getInstance()).onSearch(strText,city ,amapLat,amapLong);
        mPoiSearchTask = PoiSearchTask.getInstance(App.getInstance());
        mPoiSearchTask.setPoiSearchListener(this);
    }

    @Override
    public void getPoiSearch(List<PoiItem> list) {
         initAdapter(list);
    }

    private void initAdapter(List<PoiItem> list){
        mRecyclerView.setLayoutManager(new VerticalLayoutManager(App.getInstance()));
        mRecyclerView.addItemDecoration(new DriverItemDecoration(getActivity() ,DriverItemDecoration.VERTICAL_LIST));
        mRecyclerView.setNestedScrollingEnabled(false);
        mPoiSearchAdapter = new PoiSearchAdapter(list);
        mRecyclerView.setAdapter(mPoiSearchAdapter);
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
            mNavigation.setVisibility(View.VISIBLE);
            mNavigation.setCurrentItem(0);
            if(mBanner != null){
                mBanner.startAutoPlay();
            }
        }else if(mBanner != null){
            mBanner.stopAutoPlay();
        }
    }
}

