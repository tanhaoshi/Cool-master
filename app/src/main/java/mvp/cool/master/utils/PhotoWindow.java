package mvp.cool.master.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import mvp.cool.master.R;
import mvp.cool.master.layout.inputPWD.InputPwdView;
import mvp.cool.master.layout.inputPWD.MyInputPwdUtil;
import mvp.cool.master.mvp.ui.activity.CarRepairlPayDetailsActivity;
import mvp.cool.master.mvp.ui.activity.PayDetailsActivity;

public class PhotoWindow extends PopupWindow{

    private ExpandableListView elistview = null; // 定义树型组件
    private ExpandableListAdapter adapter = null; // 定义适配器对象
    private CheckBox childBox;
    private TextView childTextView;
    private String[] generalsTypes;
    private String[][] generals;
    private int[] image;

    private HashMap<String, Boolean> statusHashMap;

    private View mMenuView;

    private Button btn_pay;

    private Context mContext;

    ImageView closeIma;

    private MyInputPwdUtil myInputPwdUtil;

    private int type;

    public PhotoWindow(Context context , int type){
        super(context);
        mContext = context;
        this.type = type;

        initDatas();

        initViews();

        inputType();

        initListener();

    }

    private void initDatas() {
        generalsTypes = new String[]{"爱卡支付", "支付宝", "微信"};

        generals = new String[][]{
                {"油卡(恒通)750点", "油卡(立恒)1050点"},
                {"支付宝支付"},
                {"微信支付"}
        };

        image = new int[]{R.drawable.paya , R.drawable.alipay, R.drawable.wexinpay};
    }

    private void initViews(){
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popubview,null);
        btn_pay = (Button)mMenuView.findViewById(R.id.btn_cancel);
        closeIma = (ImageView)mMenuView.findViewById(R.id.closeIma);

        this.setContentView(mMenuView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.take_photo_anim);
        ColorDrawable cd = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(cd);

        elistview = (ExpandableListView) mMenuView.findViewById(R.id.list); // 取得组件
        adapter = new MyExpandableListAdapter(mContext); // 实例化适配器
        elistview.setAdapter(adapter); // 设置适配器
        int gourpsSum = adapter.getGroupCount();
        for (int i=0; i<gourpsSum; i++) {
            elistview.expandGroup(i);
        };
    }

    private void initListener(){
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.list).getTop();
                int y=(int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

        elistview.setOnChildClickListener(new OnChildClickListenerImpl());

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                show();
            }
        });

        closeIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

        private class MyExpandableListAdapter extends BaseExpandableListAdapter {
            private Context context = null;

            public MyExpandableListAdapter(Context context) {
                this.context = context;
                statusHashMap = new HashMap<String, Boolean>();
                for (int i = 0; i < generals.length; i++) {// 初始时,让所有的子选项均未被选中
                    for (int a = 0; a < generals[i].length; a++) {
                        statusHashMap.put(generals[i][a], false);
                    }
                }
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) { // 取得指定的子项
                return generals[groupPosition][childPosition];
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) { // 取得子项ID
                return childPosition;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.child_layout, null);
                }
                childTextView = (TextView) convertView.findViewById(R.id.id_treenode_label);
                childTextView.setText(getChild(groupPosition, childPosition).toString());
                childBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                Boolean nowStatus = statusHashMap.get(generals[groupPosition][childPosition]);//当前状态
                childBox.setChecked(nowStatus);
                return convertView;
            }

            @Override
            public int getChildrenCount(int groupPosition) { // 取得子项个数
                return generals[groupPosition].length;
            }

            @Override
            public Object getGroup(int groupPosition) { // 取得组对象
                return generalsTypes[groupPosition];
            }

            @Override
            public int getGroupCount() { // 取得组个数
                return generalsTypes.length;
            }

            @Override
            public long getGroupId(int groupPosition) { // 取得组ID
                return groupPosition;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
                View views = View.inflate(mContext ,R.layout.list_item,null);
                TextView groupText = (TextView)views.findViewById(R.id.id_treenode_label);
                ImageView imageView = (ImageView)views.findViewById(R.id.imageXian);
                groupText.setText(generalsTypes[groupPosition].toString());
                imageView.setImageResource(image[groupPosition]);
                return views;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }

        }

    private class OnChildClickListenerImpl implements ExpandableListView.OnChildClickListener {// 监听子项点击事件
        @Override
        public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
            int gourpsSum = adapter.getGroupCount();//组的数量
            for(int i = 0; i < gourpsSum; i++) {
                int childSum = adapter.getChildrenCount(i);//组中子项的数量
                for(int k = 0; k < childSum;k++) {
                    boolean isLast = false;
                    if (k == (childSum - 1)){
                        isLast = true;
                    }

                    CheckBox cBox = (CheckBox) adapter.getChildView(i, k, isLast, null, null).findViewById(R.id.checkbox);
                    cBox.toggle();//切换CheckBox状态！！！！！！！！！！
                    boolean itemIsCheck=cBox.isChecked();
                    TextView tView=(TextView) adapter.getChildView(i, k, isLast, null, null).findViewById(R.id.id_treenode_label);
                    String gameName=tView.getText().toString();
                    if (i == groupPosition && k == childPosition) {
                        statusHashMap.put(gameName, itemIsCheck);
                    } else {
                        statusHashMap.put(gameName, false);
                    }
                    ((BaseExpandableListAdapter) adapter).notifyDataSetChanged();//通知数据发生了变化
                }
            }
            return true;
        }
    }

    private void inputType(){
        myInputPwdUtil = new MyInputPwdUtil(mContext);
        myInputPwdUtil.getMyInputDialogBuilder().setAnimStyle(R.style.dialog_anim);
        myInputPwdUtil.setListener(new InputPwdView.InputPwdListener() {
            @Override
            public void hide() {
                myInputPwdUtil.hide();
            }

            @Override
            public void forgetPwd() {
                Toast.makeText(mContext, "忘记密码", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void finishPwd(String pwd) {
                Toast.makeText(mContext, pwd, Toast.LENGTH_SHORT).show();
                if(type == 1){
                    Intent intent = new Intent(mContext , PayDetailsActivity.class);
                    mContext.startActivity(intent);
                    myInputPwdUtil.hide();
                }else if(type == 2){
                    Intent intent = new Intent(mContext , CarRepairlPayDetailsActivity.class);
                    mContext.startActivity(intent);
                    myInputPwdUtil.hide();
                }
            }
        });
    }

    public void show(){
        myInputPwdUtil.show();
    }
}
