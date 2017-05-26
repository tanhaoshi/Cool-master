package mvp.cool.master.mvp.ui.activity;

import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class ShopActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
