package mvp.cool.master.mvp.presenter.base;

import android.support.annotation.NonNull;

import mvp.cool.master.mvp.view.base.BaseView;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by tanhao on 2017/5/15.
 */

public interface PresenterLife {

    void onCreate();

    void onBindView(@NonNull BaseView baseView);

    void onDestroy();
}
