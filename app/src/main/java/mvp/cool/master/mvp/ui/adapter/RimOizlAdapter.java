package mvp.cool.master.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.OizlModel;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/2.
 */

public class RimOizlAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder>{

    private List<OizlModel> mModelList = new ArrayList<>();

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

            helper.setText(R.id.oizlPhone , item.getTel());

            helper.setText(R.id.oizlAddares , item.getCityName() + item.getAdName() + item.getSnippet());

            RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.recyclerView);
            mModelList.add(new OizlModel("0#" , "6.29"));
            mModelList.add(new OizlModel("93#" , "6.39"));
            mModelList.add(new OizlModel("92#" , "6.09"));
            mModelList.add(new OizlModel("95#" , "6.19"));
            mModelList.add(new OizlModel("97#" , "6.59"));
            mModelList.add(new OizlModel("94#" , "6.111"));
            LinearLayoutManager layoutManager = new LinearLayoutManager(App.getInstance());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            initAdapter(recyclerView);
        }
    }

    private void initAdapter(RecyclerView recyclerView){
        List<OizlModel> list = null;
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getInstance());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        if(mModelList.size()>5){
            list = new ArrayList<>();
            for(int i=0;i<4;i++){
                list.add(mModelList.get(i));
            }
            list.add(new OizlModel("......",""));
            PoitemAdapter poitemAdapter = new PoitemAdapter(list);
            recyclerView.setAdapter(poitemAdapter);
        }else{
            PoitemAdapter poitemAdapter = new PoitemAdapter(mModelList);
            recyclerView.setAdapter(poitemAdapter);
        }
    }

}
