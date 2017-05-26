package mvp.cool.master.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.AddLocation;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/18.
 */

public class MeLocationAdapter extends BaseQuickAdapter<AddLocation , BaseViewHolder>{


    public MeLocationAdapter(List<AddLocation> addLocation){
        super(R.layout.adapter_melocation , addLocation);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddLocation item) {

    }

}
