package mvp.cool.master.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by Administrator on 2017/6/2.
 */

public class RimOizlAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder>{

   private Context mContext;

    public RimOizlAdapter(Context context , List<PoiItem> list){
        super(R.layout.style_customview , list);
        mContext  = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        if(item != null){
            if(item.getPhotos().size() > 0){
                Glide.with(App.getInstance()).load(item.getPhotos().get(0).getUrl())
                        .crossFade()
                        .into((ImageView)helper.getView(R.id.oizlImage));
            }else{
                Glide.with(App.getInstance()).load(R.mipmap.ic_launcher)
                        .crossFade()
                        .into((ImageView)helper.getView(R.id.oizlImage));
            }

            helper.setText(R.id.oizlTitle , item.getTitle());

            Glide.with(App.getInstance()).load(R.drawable.oizlmobile)
                    .crossFade()
                    .into((ImageView)helper.getView(R.id.oizlPhone));

            helper.setText(R.id.oizlO2 , item.getTypeDes());

            helper.setText(R.id.oizlMobile , item.getTel());

            helper.setText(R.id.oizlAddares , item.getCityName() + item.getAdName() + item.getSnippet());

            if("中国石油曲沃第1加油站".equals(item.getTitle())){
                TextView textView = (TextView)helper.getView(R.id.flagText);
                textView.setTextColor(mContext.getResources().getColor(R.color.red));
                helper.setText(R.id.flagText , "合作");
            }

            if("中国石化城北加油站(席村北大街)".equals(item.getTitle())){
                TextView textView = (TextView)helper.getView(R.id.flagText);
                textView.setTextColor(mContext.getResources().getColor(R.color.red));
                helper.setText(R.id.flagText , "合作");
            }

            if("捌捌捌加油站".equals(item.getTitle())){
                TextView textView = (TextView)helper.getView(R.id.flagText);
                textView.setTextColor(mContext.getResources().getColor(R.color.red));
                helper.setText(R.id.flagText , "合作");
            }
        }
    }

}
