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
 * Created by Administrator on 2017/6/1.
 */

public class AmapSetMakerTask implements PoiSearch.OnPoiSearchListener{

    private static AmapSetMakerTask mInstance;
    private PoiSearch mSearch;
    private Context mContext;
    public AmapSetMakerTask.AmapSetMakerTaskImpl mAmapSetMakerTask;

    private AmapSetMakerTask(Context context){
        this.mContext = context;
    }

    public static AmapSetMakerTask getInstance(Context context){
        if(mInstance == null){
            synchronized (PoiSearchTask.class) {
                if(mInstance == null){
                    mInstance = new AmapSetMakerTask(context);
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
            mAmapSetMakerTask.getRimPoiSearch(items);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public void setAmapSerMakerListener(AmapSetMakerTask.AmapSetMakerTaskImpl amapSerMakerListener){
        this.mAmapSetMakerTask = amapSerMakerListener;
    }

    public interface AmapSetMakerTaskImpl{
        void getRimPoiSearch(List<PoiItem> list);
    }
}
