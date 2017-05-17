package mvp.cool.master.mvp.ui.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import mvp.cool.master.App;
import mvp.cool.master.inject.component.DaggerFragmentComponent;
import mvp.cool.master.inject.component.FragmentComponent;
import mvp.cool.master.inject.model.FragmentModule;
import mvp.cool.master.mvp.presenter.base.PresenterLife;
import mvp.cool.master.mvp.view.base.BaseView;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/15.
 */

public abstract class BaseFragment <P extends PresenterLife, V extends BaseView> extends Fragment implements BaseView{

    private Unbinder mUnbinder;
    protected P mPresenter;
    protected Bundle mBundle;
    protected FragmentComponent mFragmentComponent;

    private View rootView;//缓存Fragment view

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取bundle,并保存起来
        if (savedInstanceState != null) {
            mBundle = savedInstanceState.getBundle("bundle");
        } else {
            mBundle = getArguments() == null ? new Bundle() : getArguments();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBundle != null) {
            outState.putBundle("bundle", mBundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //做双层保险 避免重复创建
        if(rootView == null){
            rootView = inflater.inflate(getContentView(),container,false);
        }else{
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null){
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //在视图创建后 执行该方法
        super.onViewCreated(view, savedInstanceState);

        initInject();

        mUnbinder = ButterKnife.bind(this,view);

        initView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mUnbinder.unbind();

        if(mPresenter != null){
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if(rootView!=null) rootView = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initInject(){
        mFragmentComponent = DaggerFragmentComponent.builder().fragmentModule(new FragmentModule(this)).build();
        initComponent();
    }


    /**
     * ------------------> 方法 <---------------------
     */
    protected abstract void initComponent();

    protected abstract int getContentView();

    protected abstract void initView(View view);

    protected Intent getIntent(Class<?> cls){
        return new Intent(App.getInstance(),cls);
    }

    protected void startActivityFinish(Class<?> cls){
        startActivity(cls);
    }

    protected void startActivity(Class<?> cls){
        startActivity(getIntent(cls));
    }
}
