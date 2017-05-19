package mvp.cool.master.mvp.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class MeBankActivity extends BaseActivity {

    @BindView(R.id.addBank)
    LinearLayout addBank;

    @Override
    protected int getContentView() {
        return R.layout.activity_me_bank;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("我的银行卡");
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.addBank})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addBank:
                startActivityFinish(AddBankActivity.class);
                break;
        }
    }
}
