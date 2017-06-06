package mvp.cool.master;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.example.DaoMaster;
import com.example.DaoSession;
import com.example.UserDao;
import com.example.liveDao;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;

/**
 * @author TanHaoShi
 * @version  1.0
 * 2017年5月17日10:15:25
 */

public class App extends Application{

    public static App sApp;

    public static DaoSession sDaoSession;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setDialogStyle();
        sApp = this;
        initDataBase();
    }

    public static App getInstance(){
        return sApp;
    }

    private void setDialogStyle(){
        StyledDialog.init(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActyManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initDataBase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constant.sql_Name_key, null);
        SQLiteDatabase db = helper.getWritableDatabase();

        DaoMaster daoMaster = new DaoMaster(db);

        sDaoSession = daoMaster.newSession();
    }

    public static UserDao getUserDao(){
        return sDaoSession.getUserDao();
    }

    public static liveDao getliveDao(){
        return sDaoSession.getLiveDao();
    }
}
