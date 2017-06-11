package mvp.cool.master.mvp.ui.adapter;

import android.content.Context;
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
 * Created by Administrator on 2017/6/9.
 */

public class CarRepairListAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    private Context mContext;

    public CarRepairListAdapter(Context context , List<PoiItem> list){
        super(R.layout.adapter_repair_layout , list);
        mContext  = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        if (item != null) {
            if (item.getPhotos().size() > 0) {
                Glide.with(App.getInstance()).load(item.getPhotos().get(0).getUrl())
                        .crossFade()
                        .into((ImageView) helper.getView(R.id.oizlImage));
            } else {
                Glide.with(App.getInstance()).load(R.mipmap.ic_launcher)
                        .crossFade()
                        .into((ImageView) helper.getView(R.id.oizlImage));
            }

            helper.setText(R.id.oizlTitle, item.getTitle());

            Glide.with(App.getInstance()).load(R.drawable.oizlmobile)
                    .crossFade()
                    .into((ImageView) helper.getView(R.id.oizlPhone));

            helper.setText(R.id.oizlO2, item.getTypeDes());

            helper.setText(R.id.oizlMobile, item.getTel());

            helper.setText(R.id.oizlAddares, item.getCityName() + item.getAdName() + item.getSnippet());
        }
    }
}
