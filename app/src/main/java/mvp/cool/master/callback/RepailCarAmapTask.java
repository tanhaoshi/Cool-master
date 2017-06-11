package mvp.cool.master.callback;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/9.
 */

public class RepailCarAmapTask implements PoiSearch.OnPoiSearchListener{

    private static RepailCarAmapTask mInstance;
    private PoiSearch mSearch;
    private Context mContext;
    private RepailCallBack mRepailCallBack;

    private RepailCarAmapTask(Context context){
        this.mContext = context;
    }

    public static RepailCarAmapTask getInstance(Context context){
        if(mInstance == null){
            synchronized (PoiSearchTask.class) {
                if(mInstance == null){
                    mInstance = new RepailCarAmapTask(context);
                }
            }
        }
        return mInstance;
    }

    public void onSearch(String key, String city,double lat,double lng){
        PoiSearch.Query query = new PoiSearch.Query(key, "", city);
        mSearch = new PoiSearch(mContext, query);
        mSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat , lng),10000));//设置周边搜索的中心点以及半径
        mSearch.setOnPoiSearchListener(this);
        mSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if(i == 1000) {
            ArrayList<PoiItem> items = poiResult.getPois();
            mRepailCallBack.callBackData(items);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void setRepailCallBackListener(RepailCallBack repailCallBack){
        this.mRepailCallBack = repailCallBack;
    }

    public interface RepailCallBack{
        void callBackData(List<PoiItem> list);
    }
}
