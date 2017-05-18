package mvp.cool.master.mvp.ui.activity;

import android.view.View;

import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

/**
 * @versio 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/18.
 */

public class LocationDetailActivity extends BaseActivity{

    @Override
    protected int getContentView() {
        return R.layout.activity_location_detail;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("新增收货地址");
        base_right.setText("保存");
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }
}
