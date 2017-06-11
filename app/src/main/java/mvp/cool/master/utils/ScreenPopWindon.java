package mvp.cool.master.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.Arrays;

import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.adapter.SortWinAdapter;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/6/11.
 */

public class ScreenPopWindon extends PopupWindow {

    private Context mContext;

    private View mMenuView;

    private String[] screen;

    private ListView mRecyclerView;

    private SortWinAdapter mSortWinAdapter;

    public ScreenPopWindon(Context context){

        super(context);
        mContext = context;

        initData();

        initView();

        initListener();

    }

    private void initData(){
        screen = new String[]{"1km范围内", "1km-3km", "3km以上"};
        mSortWinAdapter = new SortWinAdapter(Arrays.asList(screen));
    }

    private void initView(){

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.sort_popwindon,null);

        this.setContentView(mMenuView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable cd = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(cd);

        mRecyclerView = (ListView) mMenuView.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mSortWinAdapter);
    }

    private void initListener(){
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.recyclerView).getTop();
                int y=(int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y>height){
                        dismiss();
                    }
                }
                return true;
            }
        });

        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,screen[position], Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}
