package mvp.cool.master.mvp.ui.activity;

import android.view.View;

import mvp.cool.master.R;
import mvp.cool.master.mvp.presenter.impl.SafetyCenterPresenterImpl;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/17.
 */

public class SafetyCenterActivity extends BaseActivity<SafetyCenterPresenterImpl>{

    @Override
    protected int getContentView() {
        return R.layout.activity_safetycenter;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText(getResources().getString(R.string.mine_starlight));
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }
}
