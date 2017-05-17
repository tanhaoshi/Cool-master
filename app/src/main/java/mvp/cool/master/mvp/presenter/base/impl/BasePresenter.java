package mvp.cool.master.mvp.presenter.base.impl;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import mvp.cool.master.callback.RequestCallBack;
import mvp.cool.master.mvp.presenter.base.PresenterLife;
import mvp.cool.master.mvp.view.base.BaseView;
import mvp.cool.master.utils.AppUtils;
import rx.Subscription;

/**
 * @version 1.0
 * @author TanHao
 * Created by admin on 2017/5/15.
 */

public class BasePresenter<V extends BaseView,T> implements PresenterLife,RequestCallBack<T> {

    protected Subscription mSubscription; //用来取消订阅

    protected WeakReference<V> mView; //使用弱引用 避免内存泄露

    @Override
    public void onCreate() {

    }

    @Override
    public void onBindView(@NonNull BaseView baseView) {
        mView = new WeakReference<V>((V) baseView);
    }

    @Override
    public void onDestroy() {
         if(mView != null){
             mView.clear();
             mView = null;
         }
        AppUtils.cancelSubscription(mSubscription);
        mSubscription = null;
    }

    @Override
    public void onStart(T data) {

    }

    @Override
    public void onSuccess(T data) {
        mView.get().hideProgress();
    }

    @Override
    public void onError(String errorMsg, boolean pullToRefresh) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onProgress(long downSize, long fileSize) {

    }

    @Override
    public void dowloadSuccess(String path, String fileName, long fileSize) {

    }

    @NonNull
    public V getView(){
        return mView==null ? null : mView.get();
    }

}
