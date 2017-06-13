package mvp.cool.master.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;
import mvp.cool.master.utils.CarRepairPopWindon;

public class CarRepairPayActivity extends BaseActivity {

    private PoiItem mPoiItems;

    @BindView(R.id.oizlName)
    TextView oizlName;
    @BindView(R.id.oizlType)
    TextView oizlType;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.shopLog)
    ImageView shopLog;
    @BindView(R.id.repairadd)
    TextView repairAdd;

    private CarRepairPopWindon mMenuView;

    @Override
    protected int getContentView() {
        return R.layout.activity_car_repair_pay;
    }

    @Override
    protected void initView() {
        getIntentData();
        base_image.setVisibility(View.VISIBLE);
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    private void getIntentData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        if(bundle!=null){
            mPoiItems = bundle.getParcelable("listdata");
            initDataView();
        }
    }

    private void initDataView(){
        if(mPoiItems.getPhotos().size() > 0){
            Glide.with(this).load(mPoiItems.getPhotos().get(0).getUrl())
                    .crossFade()
                    .into(mImageView);

            Glide.with(this).load(mPoiItems.getPhotos().get(0).getUrl())
                    .crossFade()
                    .into(shopLog);
        }
        base_title.setText(mPoiItems.getTitle());
        oizlName.setText(mPoiItems.getTitle());
        oizlType.setText(mPoiItems.getTypeDes());
    }

    @OnClick({R.id.base_iv_back , R.id.oizlPay , R.id.repairadd})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.oizlPay:
                //jumpPobuWindown();
                break;
            case R.id.repairadd:
                addRepairPobuWindown();
                break;
        }
    }

    private void addRepairPobuWindown(){
        mMenuView = new CarRepairPopWindon(this);
        mMenuView.showAtLocation(CarRepairPayActivity.this.findViewById(R.id.payView),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
