package mvp.cool.master.mvp.ui.activity;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class CarRepairlPayDetailsActivity extends BaseActivity {

    @BindView(R.id.finshPay)
    Button finshPay;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_repairl_pay_details;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("付款详情");
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.finshPay , R.id.base_iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.finshPay:
                finish();
                break;
            case R.id.base_iv_back:
                finish();
                break;
        }
    }
}
