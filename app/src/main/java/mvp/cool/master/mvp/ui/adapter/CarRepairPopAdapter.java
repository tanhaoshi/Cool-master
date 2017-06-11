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

public class CarRepairPopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<String> mList;

    private Context mContext;

    public CarRepairPopAdapter(Context context , List<String> list){
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StyleViewHolder styleViewHolder = new StyleViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_carrepair_pop, parent, false));
        return styleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof StyleViewHolder){
           ((StyleViewHolder)holder).tv.setText(mList.get(position).toString());
       }
    }

    @Override
    public int getItemCount() {
        return mList!=null? mList.size(): 0;
    }

     static class StyleViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public StyleViewHolder(View styleView) {
            super(styleView);
            tv = (TextView) styleView.findViewById(R.id.layoutText);
        }
    }
}
