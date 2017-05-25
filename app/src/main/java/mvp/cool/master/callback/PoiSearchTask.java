package mvp.cool.master.callback;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/25.
 */

public class PoiSearchTask implements PoiSearch.OnPoiSearchListener{

    private static PoiSearchTask mInstance;
    private PoiSearch mSearch;
    private Context mContext;

    private PoiSearchTask(Context context){
        this.mContext = context;
    }

    public static PoiSearchTask getInstance(Context context){
        if(mInstance == null){
            synchronized (PoiSearchTask.class) {
                if(mInstance == null){
                    mInstance = new PoiSearchTask(context);
                }
            }
        }
        return mInstance;
    }


    public void onSearch(String key, String city,double lat,double lng){
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        PoiSearch.Query query = new PoiSearch.Query(key, "", city);
        query.setPageSize(10);
        mSearch = new PoiSearch(mContext, query);
        mSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat , lng),10000));//设置周边搜索的中心点以及半径
        //设置异步监听
        mSearch.setOnPoiSearchListener(this);
        //查询POI异步接口
        mSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if(rCode == 1000) {
            ArrayList<PoiItem> items = poiResult.getPois();
            for (PoiItem item : items) {
                //获取经纬度对象
                LatLonPoint llp = item.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();
                //获取标题
                String title = item.getTitle();
                //获取内容
                String text = item.getSnippet();
                List<Photo> list = item.getPhotos();
                KLog.i(item.getBusinessArea());
                KLog.i(item.getAdCode());
                KLog.i(item.getAdName());
                KLog.i(item.getDirection());
                KLog.i(item.getDistance());
                KLog.i(item.getEmail());
                KLog.i(item.getParkingType());
                KLog.i("标题:"+title);
                KLog.i("内容:"+text);
                KLog.i("照片:"+list.size());
                KLog.i("照片内容:"+list.get(0).getUrl());
                KLog.i(item.getCityCode());
                KLog.i(item.getPoiId());
                KLog.i(item.getTypeCode());
                KLog.i(item.getWebsite());
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
