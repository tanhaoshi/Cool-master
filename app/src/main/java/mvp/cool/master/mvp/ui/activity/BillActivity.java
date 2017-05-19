package mvp.cool.master.mvp.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class BillActivity extends BaseActivity {

   @BindView(R.id.billRecyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_bill;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("我的帐单");
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
