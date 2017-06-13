package mvp.cool.master.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.OizlModel;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/12.
 */

public class PoitemAdapter extends BaseQuickAdapter<OizlModel , BaseViewHolder> {

    private List<OizlModel> mOizlModels;

    public PoitemAdapter(List<OizlModel> list){
        super(R.layout.poitem_layout , list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OizlModel item) {

        helper.setText(R.id.oizlModel , item.getOizlModel());
    }
}
