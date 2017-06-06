package mvp.cool.master.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.layout.EdiTextClean;
import mvp.cool.master.layout.MyStarBar;
import mvp.cool.master.mvp.bean.OizlModel;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;
import mvp.cool.master.mvp.ui.adapter.OizlModelAdapter;
import mvp.cool.master.utils.PhotoWindow;

public class NameTitlePayActivity extends BaseActivity {

    @BindView(R.id.starBar)
    MyStarBar mMyStarBar;
    @BindView(R.id.recyclView)
    RecyclerView mRecyclerView;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.oizlName)
    TextView oizlName;
    @BindView(R.id.oizlType)
    TextView oizlType;
    @BindView(R.id.addOizl)
    EdiTextClean addOizl;
    @BindView(R.id.oizlValue)
    EdiTextClean oizlValue;
    @BindView(R.id.textValue)
    TextView textValue;
    @BindView(R.id.oizlPay)
    Button oizlPay;

    private List<OizlModel> mModelList = new ArrayList<>();
    private MyHandler mMyHandler = new MyHandler(this);
    private HashMap<Integer,View>  mMap ;
    private PoiItem mPoiItems;
    private String money;
    private PhotoWindow mMenuView;

    private double total = 0.00;

    @Override
    protected int getContentView() {
        return R.layout.activity_name_title_pay;
    }

    @Override
    protected void initView() {
        getIntentData();
        base_image.setVisibility(View.VISIBLE);
        initData();
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {
        setStartBar();

        addOizl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(total > 0){

                }else{
                    Toast.makeText(NameTitlePayActivity.this,"请先选择石油类型!",Toast.LENGTH_SHORT).show();
                    addOizl.setEnabled(false);
                }
            }
        });

        addOizl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if("".equals(s.toString())){
                    s = "0";
                }

                if(s != null && !"".equals(s.toString())){
                    Double valueMoney = Double.valueOf(money);
                    Double valueAmount = Double.valueOf(s.toString());
                    Double countValue = valueAmount * valueMoney ;
                    Message message = new Message();
                    message.obj = countValue;
                    message.what = 0;
                    mMyHandler.sendMessage(message);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        oizlValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Message message = new Message();
                message.obj = s.toString();
                message.what = 1;
                mMyHandler.sendMessage(message);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setStartBar(){
        mMyStarBar.setStarMark(3.6f);
    }

    private void initData(){
        mModelList.add(new OizlModel("0#" , "6.29"));
        mModelList.add(new OizlModel("93#" , "6.39"));
        mModelList.add(new OizlModel("92#" , "6.09"));
        mModelList.add(new OizlModel("95#" , "6.19"));
        mModelList.add(new OizlModel("97#" , "6.59"));
        mModelList.add(new OizlModel("94#" , "6.111"));
        mModelList.add(new OizlModel("100#" ,"1.27"));
        mModelList.add(new OizlModel("99#" , "2.76"));
        initAdapter();
    }

    private void initAdapter(){
        mMap = new HashMap<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getInstance());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        OizlModelAdapter oizlModelAdapter = new OizlModelAdapter(mModelList);
        mRecyclerView.setAdapter(oizlModelAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMap.put(position , view);
                Iterator iter = mMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry element = (Map.Entry) iter.next();
                    Integer strKey = (Integer) element.getKey();
                    if(strKey == position){
                        mMap.get(strKey).setBackgroundColor(getResources().getColor(R.color.yellow));
                    }else{
                        mMap.get(strKey).setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }

                mModelList.get(position).getOizlModel();
                money = mModelList.get(position).getOizlMoney();
                total = Double.valueOf(money);
                addOizl.setEnabled(true);
            }
        });
    }

    private void getIntentData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        mPoiItems = bundle.getParcelable("listdata");
        initDataView();
    }

    private void initDataView(){
        if(mPoiItems.getPhotos().size() > 0){
            Glide.with(this).load(mPoiItems.getPhotos().get(0).getUrl())
                    .crossFade()
                    .into(mImageView);
        }

        base_title.setText(mPoiItems.getTitle());
        oizlName.setText(mPoiItems.getTitle());
        oizlType.setText(mPoiItems.getTypeDes());
    }

    @OnClick({R.id.base_iv_back , R.id.oizlPay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.base_iv_back:
                finish();
                break;
            case R.id.oizlPay:
                jumpPobuWindown();
                break;
        }
    }

    private void jumpPobuWindown(){
        mMenuView = new PhotoWindow(NameTitlePayActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mMenuView.showAtLocation(NameTitlePayActivity.this.findViewById(R.id.payView),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private static class MyHandler extends Handler{

        private final WeakReference<NameTitlePayActivity> mActivity;

        public MyHandler(NameTitlePayActivity activity) {
            mActivity = new WeakReference<NameTitlePayActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            NameTitlePayActivity nameTitlePayActivity = mActivity.get();
            if(nameTitlePayActivity != null){
               switch (msg.what){
                   case 0:
                       nameTitlePayActivity.oizlValue.setText( msg.obj+"");
                       break;
                   case 1:
                       nameTitlePayActivity.textValue.setText( msg.obj+"");
                       break;
               }
            }
        }
    }
}
