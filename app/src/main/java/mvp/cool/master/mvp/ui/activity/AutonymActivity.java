package mvp.cool.master.mvp.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.presenter.impl.AutonymPresenterImpl;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class AutonymActivity extends BaseActivity<AutonymPresenterImpl>{

    @BindView(R.id.bound_phone)
    TextView bound_phone;
    @BindView(R.id.au_autonym)
    TextView au_autonym;
    @BindView(R.id.autonym_approve)
    Button autonymApprove;

    @Override
    protected int getContentView() {
        return R.layout.activity_autonym;
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

    @OnClick({R.id.au_autonym , R.id.bound_phone , R.id.autonym_approve,R.id.base_iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.au_autonym:
                break;
            case R.id.bound_phone:
                startActivityFinish(SafetyCenterActivity.class);
                break;
            case R.id.autonym_approve:
                startActivityFinish(AutonuCommitActivity.class);
                break;
            case R.id.base_iv_back:
                finish();
                break;
        }
    }
}
