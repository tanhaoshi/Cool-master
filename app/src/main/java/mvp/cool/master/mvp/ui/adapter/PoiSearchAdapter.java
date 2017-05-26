package mvp.cool.master.mvp.ui.adapter;

import android.widget.ImageView;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import mvp.cool.master.App;
import mvp.cool.master.R;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/26.
 */

public class PoiSearchAdapter extends BaseQuickAdapter<PoiItem , BaseViewHolder>{

    public PoiSearchAdapter(List<PoiItem> list){
        super(R.layout.adapter_poisearch , list);
    }

    @Override
    protected void convert(BaseViewHolder holder, PoiItem item) {
        if(item != null){
            holder.setText(R.id.poiCar , item.getTitle());
            holder.setText(R.id.poiAddares ,item.getCityName()+item.getAdName() + item.getSnippet());
            double distance = (double)item.getDistance() / 1000;
            holder.setText(R.id.poiDistance , distance+"KM");
            if(item.getPhotos().size() > 0){
                Glide.with(App.getInstance()).load(item.getPhotos().get(0).getUrl())
                        .crossFade()
                        .into((ImageView)holder.getView(R.id.poiImage));
            }else{
                Glide.with(App.getInstance()).load(R.mipmap.ic_launcher)
                        .crossFade()
                        .into((ImageView)holder.getView(R.id.poiImage));
            }
        }
    }

}
