package mvp.cool.master.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.layout.layoutmanager.DriverItemDecoration;
import mvp.cool.master.layout.layoutmanager.VerticalLayoutManager;
import mvp.cool.master.mvp.bean.OizlType;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;
import mvp.cool.master.mvp.ui.adapter.OizlTypeAdapter;
import mvp.cool.master.utils.CarRepairPopWindon;
import mvp.cool.master.utils.PhotoWindow;

public class CarRepairPayActivity extends BaseActivity implements
        CarRepairPopWindon.OngetOizlTypeListener
       ,OizlTypeAdapter.OnTextWatchMoneyListener{

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
    @BindView(R.id.recyclView)
    RecyclerView mRecyclerView;
    @BindView(R.id.textValue)
    TextView textValue;

    private CarRepairPopWindon mMenuView;

    private Map<Integer , View> mMap;

    private List<OizlType> mOizlTypeList;

    private MyHandler mMyHandler = new MyHandler(this);

    private PhotoWindow popPayView;

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
                jumpPobuWindown();
                break;
            case R.id.repairadd:
                addRepairPobuWindown();
                break;
        }
    }

    private void jumpPobuWindown(){
        if(popPayView == null) {
            popPayView = new PhotoWindow(this,2);
            popPayView.showAtLocation(CarRepairPayActivity.this.findViewById(R.id.extraKey),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }else{
            popPayView.showAtLocation(CarRepairPayActivity.this.findViewById(R.id.extraKey),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private void addRepairPobuWindown(){
        if(mMenuView == null){
            mMenuView = new CarRepairPopWindon(this ,mMap);
            mMenuView.setOngetTyoeListener(this);
            mMenuView.showAtLocation(CarRepairPayActivity.this.findViewById(R.id.payView),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }else{
            mMenuView.showAtLocation(CarRepairPayActivity.this.findViewById(R.id.payView),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    @Override
    public void getOizlTypeList(List<OizlType> list , Map<Integer,View> map) {
        mRecyclerView.setLayoutManager(new VerticalLayoutManager(App.getInstance()));
        mRecyclerView.addItemDecoration(new DriverItemDecoration(this ,DriverItemDecoration.VERTICAL_LIST));
        OizlTypeAdapter oizlTypeAdapter = new OizlTypeAdapter(list);
        oizlTypeAdapter.setOnTextWatchListenet(this);
        mRecyclerView.setAdapter(oizlTypeAdapter);
        mMap = map;
        mOizlTypeList = list;
        setMoneyAndChange(list);
    }

    @Override
    public void getChangeMoney(Double str, int position) {

        mOizlTypeList.get(position).setMoney(str);

        setMoneyAndChange(mOizlTypeList);
    }

    private void setMoneyAndChange(List<OizlType> list){
        Double value = 0.0;
        for(int i=0;i<list.size();i++){
            value = value +  list.get(i).getMoney();
        }
        Message message = new Message();
        message.obj = value;
        message.what = 0;
        mMyHandler.sendMessage(message);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<CarRepairPayActivity> mActivity;

        public MyHandler(CarRepairPayActivity activity) {
            mActivity = new WeakReference<CarRepairPayActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CarRepairPayActivity carRepairPayActivity = mActivity.get();
            if(carRepairPayActivity != null){
                switch (msg.what){
                    case 0:
                        carRepairPayActivity.textValue.setText(msg.obj+"");
                        break;
                }
            }
        }
    }

}
