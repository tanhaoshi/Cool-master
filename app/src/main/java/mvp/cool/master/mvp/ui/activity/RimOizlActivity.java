package mvp.cool.master.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.layout.layoutmanager.DriverItemDecoration;
import mvp.cool.master.layout.layoutmanager.VerticalLayoutManager;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;
import mvp.cool.master.mvp.ui.adapter.RimOizlAdapter;

public class RimOizlActivity extends BaseActivity {

    private List<PoiItem> mItemList = new ArrayList<>();
    @BindView(R.id.oizlRecycler)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_rim_oizl;
    }

    @Override
    protected void initView() {
        base_title.setText("附近加油");
        base_image.setVisibility(View.VISIBLE);
        base_right.setVisibility(View.VISIBLE);
        base_right.setText("地图");
        getLocationBundle();
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.base_iv_back , R.id.base_tv_toolbar_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                finish();
                break;
        }
    }

    private void getLocationBundle(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("list");
        mItemList = (ArrayList)bundle.getSerializable("location");
        initAdapter();
    }

    private void initAdapter(){
        mRecyclerView.setLayoutManager(new VerticalLayoutManager(App.getInstance()));
        mRecyclerView.addItemDecoration(new DriverItemDecoration(this ,DriverItemDecoration.VERTICAL_LIST));
        RimOizlAdapter rimOizlAdapter = new RimOizlAdapter(this , mItemList);
        mRecyclerView.setAdapter(rimOizlAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startIntentActivity(position);
            }
        });
    }

    private void startIntentActivity(int position){
        Intent intent = new Intent(this,NameTitlePayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("listdata",mItemList.get(position));
        intent.putExtra("data" , bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mItemList = null;
    }
}
