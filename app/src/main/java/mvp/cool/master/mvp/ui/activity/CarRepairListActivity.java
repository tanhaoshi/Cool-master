package mvp.cool.master.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
import mvp.cool.master.mvp.ui.adapter.CarRepairListAdapter;
import mvp.cool.master.utils.ScreenPopWindon;
import mvp.cool.master.utils.SortPopWindon;

public class CarRepairListActivity extends BaseActivity {

    private List<PoiItem> mItemList = new ArrayList<>();
    @BindView(R.id.oizlRecycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.sortId)
    TextView sortId;
    @BindView(R.id.screenId)
    TextView screenId;
    private SortPopWindon mSortPopWindon;
    private ScreenPopWindon mScreenPopWindon;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_repair_list;
    }

    @Override
    protected void initView() {
        initTitle();

        getLocationBundle();
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.base_iv_back , R.id.base_tv_toolbar_right ,R.id.screenId , R.id.sortId})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.base_tv_toolbar_right:
                finish();
                break;
            case R.id.sortId:
                mSortPopWindon = new SortPopWindon(this);
                int xOffset = sortId.getWidth() - sortId.getMeasuredWidth() ;
                mSortPopWindon.showAsDropDown(sortId , xOffset , 0);
                break;
            case R.id.screenId:
                mScreenPopWindon = new ScreenPopWindon(this);
                int witdth = screenId.getWidth() - sortId.getMeasuredWidth() ;
                mScreenPopWindon.showAsDropDown(screenId , witdth , 0);
                break;
        }
    }

    private void initTitle(){
        base_title.setText("附近维修");
        base_image.setVisibility(View.VISIBLE);
        base_right.setVisibility(View.VISIBLE);
        base_right.setText("地图");
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
        CarRepairListAdapter carRepairListAdapter = new CarRepairListAdapter(this , mItemList);
        mRecyclerView.setAdapter(carRepairListAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startIntentActivity(position);
            }
        });
    }

    private void startIntentActivity(int position){
        Intent intent = new Intent(this,CarRepairPayActivity.class);
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
