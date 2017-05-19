package mvp.cool.master.mvp.ui.activity;

import android.support.v7.widget.RecyclerView;
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

public class MeLocationActivty extends BaseActivity{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.jump_location)
    Button jumpLocation;

    @Override
    protected int getContentView() {
        return R.layout.activity_melocation;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("地址管理");
        base_right.setVisibility(View.VISIBLE);
        base_right.setText("新增");
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.jump_location , R.id.base_iv_back , R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.jump_location:
                startActivityFinish(LocationDetailActivity.class);
                break;
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                startActivityFinish(LocationDetailActivity.class);
                break;
        }
    }
}
