package mvp.cool.master.mvp.ui.activity;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/18.
 */

public class AutonuCommitActivity extends BaseActivity{

    @BindView(R.id.autonu_finsh)
    Button autonuFinsh;

    @Override
    protected int getContentView() {
        return R.layout.activity_autonucommit;
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

    @OnClick({R.id.autonu_finsh})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.autonu_finsh:
                startActivityFinish(AutonuFinshActivity.class);
                break;
        }
    }
}
