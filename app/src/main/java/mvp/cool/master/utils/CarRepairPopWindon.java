package mvp.cool.master.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mvp.cool.master.R;
import mvp.cool.master.mvp.bean.OizlType;
import mvp.cool.master.mvp.ui.adapter.CarRepailrTypeAdapter;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/11.
 */

public class CarRepairPopWindon extends PopupWindow{

    private Context mContext;

    private View mMenuView;

    private RecyclerView mRecyclerView;

    private ImageView closeIma;

    private Button btn_yes;

    private CarRepailrTypeAdapter mCarRepailrTypeAdapter;

    private List<OizlType> mOizlTypeList;

    private ArrayList<OizlType> mArrayList;

    private OngetOizlTypeListener mOngetOizlTypeListener;

    private Map<Integer,View> mViewMap;

    private Map<Integer,View> mTypeMap;

    public CarRepairPopWindon(Context context , Map<Integer,View> viewMap){
        super(context);
        this.mContext = context;

        mArrayList = new ArrayList();

        mViewMap = new HashMap<>();

        mTypeMap = viewMap;

        initView();

        initListener();

        initData();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.carrepair_pop_layout,null);
        mRecyclerView = (RecyclerView)mMenuView.findViewById(R.id.popRecyclerView);
        closeIma = (ImageView)mMenuView.findViewById(R.id.closeIma);
        btn_yes = (Button)mMenuView.findViewById(R.id.btn_yes);

        this.setContentView(mMenuView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.take_photo_anim);
        ColorDrawable cd = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(cd);
    }

    private void initListener(){
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.relLayout).getTop();
                int y=(int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return false;
            }
        });

        closeIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initData(){
        mOizlTypeList = new ArrayList<>();
        mOizlTypeList.add(new OizlType("更换轮胎",202.0));
        mOizlTypeList.add(new OizlType("更换刹车片",230.0));
        mOizlTypeList.add(new OizlType("洗车",232.0));
        mOizlTypeList.add(new OizlType("充电",233.0));
        mOizlTypeList.add(new OizlType("打蜡",400.0));
        mOizlTypeList.add(new OizlType("更换离合片",123.0));
        mOizlTypeList.add(new OizlType("电路维修",400.0));
        mOizlTypeList.add(new OizlType("发动机改装",212.0));
        mOizlTypeList.add(new OizlType("喷涂油漆面",112.0));
        mCarRepailrTypeAdapter = new CarRepailrTypeAdapter(mOizlTypeList , mTypeMap);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        mRecyclerView.setAdapter(mCarRepailrTypeAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.layoutText);
                Drawable baground = textView.getBackground();
                ColorDrawable colorDrawable = (ColorDrawable) baground;
                int color = colorDrawable.getColor();
                if(color == mContext.getResources().getColor(R.color.white)){
                    mArrayList.add(mOizlTypeList.get(position));
                    mViewMap.put(position,textView);
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                    mOngetOizlTypeListener.getOizlTypeList(mArrayList , mViewMap);
                }else{
                    mArrayList.remove(mOizlTypeList.get(position));
                    mViewMap.remove(position);
                    mOngetOizlTypeListener.getOizlTypeList(mArrayList , mViewMap);
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            }
        });
    }

    public void setOngetTyoeListener(OngetOizlTypeListener listener){
        this.mOngetOizlTypeListener = listener;
    }

    public interface OngetOizlTypeListener{
         void getOizlTypeList(List<OizlType> str , Map<Integer ,View> map);
    }
}
