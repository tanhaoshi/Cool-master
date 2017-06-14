package mvp.cool.master.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mvp.cool.master.R;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/11.
 */

public class CarRepairPopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    List<String> mList;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;

    public CarRepairPopAdapter(Context context , List<String> list){
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_carrepair_pop, parent, false);

        view.setOnClickListener(this);

        StyleViewHolder styleViewHolder = new StyleViewHolder(view);

        return styleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof StyleViewHolder){
           ((StyleViewHolder)holder).tv.setText(mList.get(position).toString());

           ((StyleViewHolder)holder).itemView.setTag(position);
       }
    }

    @Override
    public int getItemCount() {
        return mList!=null? mList.size(): 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    static class StyleViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public StyleViewHolder(View styleView) {
            super(styleView);
            tv = (TextView) styleView.findViewById(R.id.layoutText);
        }
    }

}
