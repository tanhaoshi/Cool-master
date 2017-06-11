package mvp.cool.master.utils;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import mvp.cool.master.App;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/30.
 */

public class LocationUtils {

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    public LocationUtils(AMapLocationListener locationListener){
        mLocationClient = new AMapLocationClient(App.getInstance());
        mLocationClient.setLocationListener(locationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setWifiActiveScan(true);
        mLocationOption.setMockEnable(false);
        mLocationClient.setLocationOption(mLocationOption);
    }

    public void startLocation(){
        mLocationClient.startLocation();
    }
}
