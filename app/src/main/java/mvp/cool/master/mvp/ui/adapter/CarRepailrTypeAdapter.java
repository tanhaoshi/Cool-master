package mvp.cool.master.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.OizlType;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/13.
 */

public class CarRepailrTypeAdapter extends BaseQuickAdapter<OizlType,BaseViewHolder>{

    private List<OizlType> mOizlTypes;

    private Map<Integer,View> mViewMap;

    public CarRepailrTypeAdapter(List<OizlType> list , Map<Integer,View> map){
        super(R.layout.adapter_carrepair_pop , list);
        this.mOizlTypes = list;
        this.mViewMap = map;
    }

    @Override
    protected void convert(BaseViewHolder helper, OizlType item) {
        if(item != null){
            helper.setText(R.id.layoutText , item.getType());
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if(mViewMap != null){
            Iterator iter = mViewMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry element = (Map.Entry) iter.next();
                Integer strKey = (Integer) element.getKey();
                if(strKey == position) {
                    TextView textView = (TextView) holder.getView(R.id.layoutText);
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                }
            }
        }
    }
}
