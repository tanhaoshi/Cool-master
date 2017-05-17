package mvp.cool.master.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @version 1.0
 * @author TanHao
 * Created by Administrator on 2017/5/17.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> mList;

    public FragmentAdapter(FragmentManager fragmentManager , List<Fragment> list){
        super(fragmentManager);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

}
