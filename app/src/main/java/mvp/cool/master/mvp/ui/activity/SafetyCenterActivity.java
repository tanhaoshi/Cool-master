package mvp.cool.master.mvp.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.presenter.impl.SafetyCenterPresenterImpl;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/17.
 */

public class SafetyCenterActivity extends BaseActivity<SafetyCenterPresenterImpl>{

    @BindView(R.id.stepOne)
    Button stepOne;
    @BindView(R.id.autonym)
    TextView autonym;

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

    @OnClick({R.id.stepOne , R.id.autonym , R.id.base_iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.stepOne:
                startActivityFinish(VerifyActivity.class);
                break;
            case R.id.autonym:
                startActivityFinish(AutonymActivity.class);
                break;
            case R.id.base_iv_back:
                finish();
                break;
        }
    }
}
