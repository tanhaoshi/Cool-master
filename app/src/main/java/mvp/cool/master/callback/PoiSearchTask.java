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
 * Created by Administrator on 2017/5/25.
 */

public class PoiSearchTask implements PoiSearch.OnPoiSearchListener{

    private static PoiSearchTask mInstance;
    private PoiSearch mSearch;
    private Context mContext;
    public PoiSearchData mPoiSearchData;

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
        PoiSearch.Query query = new PoiSearch.Query(key, "", city);
        mSearch = new PoiSearch(mContext, query);
        mSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat , lng),10000));//设置周边搜索的中心点以及半径
        mSearch.setOnPoiSearchListener(this);
        mSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if(rCode == 1000) {
            ArrayList<PoiItem> items = poiResult.getPois();
            mPoiSearchData.getPoiSearch(items);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    public void setPoiSearchListener(PoiSearchData poiSearchData){
        this.mPoiSearchData = poiSearchData;
    }

    public interface PoiSearchData{
        void getPoiSearch(List<PoiItem> list);
    }
}
