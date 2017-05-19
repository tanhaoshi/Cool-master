package mvp.cool.master.mvp.ui.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.JsonBean;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;
import mvp.cool.master.utils.GetJsonDataUtil;

/**
 * @versio 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/18.
 */

public class LocationDetailActivity extends BaseActivity{

    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.Province)
    TextView province;
    @BindView(R.id.all_city)
    LinearLayout allCity;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private OptionsPickerView optionsPickerView;


    @Override
    protected int getContentView() {
        return R.layout.activity_location_detail;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("新增收货地址");
        base_right.setText("保存");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {
    }

    private void initJsonData(){

        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");

        ArrayList<JsonBean> jsonBean = parseData(JsonData);

        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return detail;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){
                        Toast.makeText(LocationDetailActivity.this,"开始解析数据",Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
            }
        }
    };

     @OnClick({R.id.all_city , R.id.base_iv_back , R.id.saveBtn})
     public void onClick(View view){
         switch (view.getId()){
             case R.id.all_city:
                 showLocation();
                 break;
             case R.id.base_iv_back:
                 finish();
                 break;
             case R.id.saveBtn:
                 finish();
                 break;
         }
     }

    private void showLocation(){
        optionsPickerView =  new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                location.setText(options1Items.get(options1).getPickerViewText());
                city.setText(options2Items.get(options1).get(options2));
                province.setText(options3Items.get(options1).get(options2).get(options3));
            }
        })  .setTitleText("城市选择")
                .setTitleSize(18)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(16)
                .setOutSideCancelable(false)// default is true
                .build();

        optionsPickerView.setPicker(options1Items, options2Items, options3Items);//添加数据
        optionsPickerView.show();
    }
}
