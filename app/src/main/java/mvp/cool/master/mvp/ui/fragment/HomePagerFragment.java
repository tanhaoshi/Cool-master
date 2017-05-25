package mvp.cool.master.mvp.ui.fragment;

import android.Manifest;
import android.os.Build;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.callback.PoiSearchTask;
import mvp.cool.master.mvp.ui.fragment.base.BaseFragment;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/17.
 */
public class HomePagerFragment extends BaseFragment implements LocationSource, AMapLocationListener {

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

    private String strText;
    private String city;

    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器

    private Double amapLat;
    private Double amapLong;

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
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLat = amapLocation.getLatitude();//获取纬度
                amapLong = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                city = amapLocation.getCity();
                initFirastPoi();
                Toast.makeText(App.getInstance(),amapLocation.getAddress(),Toast.LENGTH_SHORT).show();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
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
        //初始化定位
        mLocationClient = new AMapLocationClient(App.getInstance());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void requestPermiSsiongs() {
        //Android6.0的时候必须动态写权限，还必须打开定位系统才能开始定位
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE}, 1);
            initLoc();
        }else{
            initLoc();
        }
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
    }
}

