package mvp.cool.master.mvp.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import mvp.cool.master.App;
import mvp.cool.master.R;
import mvp.cool.master.inject.component.ActivityComponent;
import mvp.cool.master.inject.component.DaggerActivityComponent;
import mvp.cool.master.inject.model.ActivityModule;
import mvp.cool.master.layout.TextViewAnimotion;
import mvp.cool.master.mvp.presenter.base.impl.BasePresenter;

/**
 * @version 1.0
 * @author  TanHaoShi
 * Created by Administrator on 2017/5/15.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{

    protected ActivityComponent component;
    protected Toolbar toolbar;
    protected ImageView iv_back;
    protected TextViewAnimotion title;
    protected ImageView tv_right;
    protected Toolbar base_toolbar;
    protected ImageView base_image;
    protected TextView base_title;
    protected TextView base_right;
    private Unbinder m;
    protected T mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The parent class is executed first
        setContentView(getContentView());
        ButterKnife.bind(this);
        initComponent();
        initTooBar();
        initView();
        initListener();
    }

    private void initTooBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        title = (TextViewAnimotion) findViewById(R.id.tv_toolbar_title);
        tv_right = (ImageView) findViewById(R.id.tv_toolbar_right);

        base_toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        base_image = (ImageView) findViewById(R.id.base_iv_back);
        base_title = (TextView) findViewById(R.id.base_tv_toolbar_title);
        base_right = (TextView) findViewById(R.id.base_tv_toolbar_right);
    }

    /**  ==================  所有父类继承的行为  ==================== **/
    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void inject();

    protected abstract void initListener();

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        m = ButterKnife.bind(this);
    }

    private void initComponent(){
        component = DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build();
        inject();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("BaseActivity",getClass().getSimpleName().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBasePresenter != null){
            mBasePresenter.onDestroy();
            mBasePresenter = null;
        }
        m.unbind();
    }

    protected Intent getIntent(Class<?> cls){
        return new Intent(App.getInstance(),cls);
    }

    protected void startActivityFinish(Class<?> cls){
        startActivity(cls);
        finish();
    }

    protected void startActivity(Class<?> cls){
        startActivity(getIntent(cls));
    }

}
