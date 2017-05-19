package mvp.cool.master.mvp.ui.activity;

import android.view.View;

import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class VerifyActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_verify;
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

    @OnClick({R.id.base_iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
        }
    }
}
