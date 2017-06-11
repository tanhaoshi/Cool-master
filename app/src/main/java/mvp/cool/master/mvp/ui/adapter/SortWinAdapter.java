package mvp.cool.master.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mvp.cool.master.R;

import static com.hss01248.dialog.StyledDialog.context;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/9.
 */

public class SortWinAdapter extends BaseAdapter {

    List<String> mList;

    public SortWinAdapter(List<String> list){
        mList = list;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0  ;
    }

    @Override
    public Object getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.sort_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(mList.get(position));
    }

    static class ViewHolder {

        @BindView(R.id.sortItem)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
