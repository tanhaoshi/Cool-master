package mvp.cool.master.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.Arrays;

import mvp.cool.master.R;
import mvp.cool.master.layout.layoutmanager.DividerGridItemDecoration;
import mvp.cool.master.mvp.ui.adapter.CarRepairPopAdapter;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/11.
 */

public class CarRepairPopWindon extends PopupWindow{

    private Context mContext;

    private View mMenuView;

    private RecyclerView mRecyclerView;

    private String[] arrays;

    private CarRepairPopAdapter mCarRepairAdapter;

    public CarRepairPopWindon(Context context){
        super(context);
        this.mContext = context;

        initView();

        initListener();

        initData();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.carrepair_pop_layout,null);
        mRecyclerView = (RecyclerView)mMenuView.findViewById(R.id.recyclerView);

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
                return true;
            }
        });
    }

    private void initData(){
        arrays = new String[]{"更换轮胎","更换刹车片","洗车","充电","喷涂油漆面","打蜡","更换离合片","电路维修","发动机改装"};
        mCarRepairAdapter = new CarRepairPopAdapter(mContext,Arrays.asList(arrays));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mRecyclerView.setAdapter(mCarRepairAdapter);
    }

}
