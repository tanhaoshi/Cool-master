package mvp.cool.master.mvp.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

public class HomeActivty extends BaseActivity<HomePresenterImpl> implements
        ActivityCompat.OnRequestPermissionsResultCallback{

    @BindView(R.id.ahb_navigation)
    AHBottomNavigation mNavigation;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private long oldOutTime;

    private final List<Fragment>  mFragmentList = new ArrayList<Fragment>();

    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    private boolean isNeedCheck = true;

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

    @Override
    protected void onResume() {
        super.onResume();
        if(isNeedCheck){
            checkPermissions(needPermissions);
        }
    }

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     *
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     * @param grantResults
     * @return
     * @since 2.5.0
     *
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     *
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     *  启动应用的设置
     *
     * @since 2.5.0
     *
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
