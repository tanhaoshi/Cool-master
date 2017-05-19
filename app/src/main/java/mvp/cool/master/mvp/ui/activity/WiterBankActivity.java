package mvp.cool.master.mvp.ui.activity;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class WiterBankActivity extends BaseActivity {

    @BindView(R.id.stepDown)
    Button stepDown;

    @Override
    protected int getContentView() {
        return R.layout.activity_witer_bank;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("填写银行卡信息");
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.stepDown})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.stepDown:
                startActivityFinish(MyBankActivity.class);
                break;
        }
    }
}
