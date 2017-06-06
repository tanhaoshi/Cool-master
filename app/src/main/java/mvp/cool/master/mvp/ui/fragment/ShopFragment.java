package mvp.cool.master.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.fragment.base.BaseFragment;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/17.
 */

public class ShopFragment extends BaseFragment{

    private static ViewPager mViewPager;

    public static ShopFragment newInstance(ViewPager viewPager){
        ShopFragment fragment = new ShopFragment();
        Bundle bundle = new Bundle();
        mViewPager = viewPager;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void showProgress(boolean isTrue) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                mViewPager.setCurrentItem(0);
                break;
        }
    }

}
