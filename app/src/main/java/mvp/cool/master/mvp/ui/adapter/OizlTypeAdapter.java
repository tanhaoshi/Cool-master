package mvp.cool.master.mvp.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.OizlType;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/14.
 */

public class OizlTypeAdapter extends BaseQuickAdapter<OizlType , BaseViewHolder>{

    private OnTextWatchMoneyListener mOnTextWatchMoneyListener;

    public OizlTypeAdapter(List<OizlType> list){
        super(R.layout.adapter_oizltype_layout ,list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, OizlType item) {
        if(item!=null){
            helper.setText(R.id.type , item.getType());
            helper.setText(R.id.addOizl , item.getMoney()+"");

            EditText ediTextClean = (EditText) helper.getView(R.id.addOizl);

            ediTextClean.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Double value = Double.valueOf(s.toString());
                    mOnTextWatchMoneyListener.getChangeMoney(value,helper.getLayoutPosition());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public void setOnTextWatchListenet(OnTextWatchMoneyListener onTextWatchListenet){
        this.mOnTextWatchMoneyListener = onTextWatchListenet;
    }

    public interface OnTextWatchMoneyListener{
        void getChangeMoney(Double str , int position);
    }
}
