package mvp.cool.master.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.OizlModel;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/5.
 */

public class OizlModelAdapter extends BaseQuickAdapter<OizlModel , BaseViewHolder>{

    public OizlModelAdapter(List<OizlModel> list){
        super(R.layout.adapter_oizl , list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OizlModel item) {
        helper.setText(R.id.oizlModel , item.getOizlModel());
        helper.setText(R.id.oizlMoney , item.getOizlMoney());
    }

}
