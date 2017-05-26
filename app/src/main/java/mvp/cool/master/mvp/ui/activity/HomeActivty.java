package mvp.cool.master.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.mvp.presenter.impl.HomePresenterImpl;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;
import mvp.cool.master.mvp.ui.adapter.FragmentAdapter;
import mvp.cool.master.mvp.ui.fragment.HomePagerFragment;
import mvp.cool.master.mvp.ui.fragment.RimFragmnet;
import mvp.cool.master.mvp.ui.fragment.ShopFragment;
import mvp.cool.master.mvp.ui.fragment.UserFragment;

public class HomeActivty extends BaseActivity<HomePresenterImpl>{

    @BindView(R.id.ahb_navigation)
    AHBottomNavigation mNavigation;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private long oldOutTime;

    private final List<Fragment>  mFragmentList = new ArrayList<Fragment>();

    //private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        initTabs();
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {
        initListenner();
        initFragment();
        setViewpagerAdapter();
        //intentSerivce();
    }

    private void initTabs(){
        AHBottomNavigationItem first = new AHBottomNavigationItem(getResources().getString(R.string.tab_first),R.drawable.first);
        AHBottomNavigationItem rim = new AHBottomNavigationItem(getResources().getString(R.string.tab_rim),R.drawable.rim);
        AHBottomNavigationItem shop = new AHBottomNavigationItem(getResources().getString(R.string.tab_shop),R.drawable.shop);
        AHBottomNavigationItem me = new AHBottomNavigationItem(getResources().getString(R.string.tab_me),R.drawable.me);

        mNavigation.addItem(first);
        mNavigation.addItem(rim);
        mNavigation.addItem(shop);
        mNavigation.addItem(me);

        mNavigation.setAccentColor(ContextCompat.getColor(this, R.color.accent_bottom_navigation));
        mNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.inactive_bottom_navigation));

        mNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.bg_bottom_navigation));

        // 强制着色
        mNavigation.setForceTint(true);
        // 使用圆圈效果
        mNavigation.setColored(false);
        // Set current item programmatically
        mNavigation.setCurrentItem(0);
        HomePagerFragment.newInstance(mNavigation);
    }

    private void initListenner(){
        mNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                chooseTabs(position);
                return true;
            }
        });
    }

    private void chooseTabs(int position){
        switch (position) {
            case 0:
                mViewPager.setCurrentItem(position);
                mNavigation.setVisibility(View.VISIBLE);
                break;
            case 1:
                mViewPager.setCurrentItem(position);
                mNavigation.setVisibility(View.VISIBLE);
                break;
            case 2:
                mViewPager.setCurrentItem(position);
                mNavigation.setVisibility(View.GONE);
                ShopFragment.newInstance(mViewPager);
                break;
            case 3:
                mViewPager.setCurrentItem(position);
                mNavigation.setVisibility(View.VISIBLE);
        }
    }

    private void initFragment(){
        mFragmentList.add(new HomePagerFragment());
        mFragmentList.add(new RimFragmnet());
        mFragmentList.add(new ShopFragment());
        mFragmentList.add(new UserFragment());
    }

    private void setViewpagerAdapter(){
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager() , mFragmentList);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if( System.currentTimeMillis() - oldOutTime > 2000){
                oldOutTime = System.currentTimeMillis();
                Toast.makeText(App.getInstance(),"再次点击，退出应用",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KLog.i("HomeActivity() ~~~~~~ onDestroy()");
//        mIntent = new Intent(HomeActivty.this , DownLoadService.class);
//        mIntent.putExtra(Constant.SERVICE_FLAG , Constant.STOP_SERVICE);
//        mIntent.putExtra("flag" , false);
//        startService(mIntent);
//        stopService(mIntent);
    }

    private void intentSerivce(){
//        mIntent = new Intent(HomeActivty.this , DownLoadService.class);
//        mIntent.putExtra(Constant.SERVICE_FLAG ,Constant.START_SERVICE);
//        mIntent.putExtra("flag" , true);
//        startService(mIntent);
    }
}
