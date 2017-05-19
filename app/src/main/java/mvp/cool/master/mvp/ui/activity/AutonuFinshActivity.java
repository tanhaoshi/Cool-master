package mvp.cool.master.mvp.ui.activity;

import android.view.View;

import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class AutonuFinshActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_autonu_finsh;
    }

    @Override
    protected void initView() {
        base_title.setText("安全中心");
        base_image.setVisibility(View.VISIBLE);
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }
}
