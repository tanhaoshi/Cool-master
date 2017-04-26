package mvp.cool.master;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;

import com.example.DaoMaster;
import com.example.DaoSession;
import com.example.UserDao;
import com.example.liveDao;

/**
 * @author TanHao
 * @version  1.0
 * 2017年4月25日10:15:25
 */

public class App extends Application{

    public static App sApp;

    private boolean DEV_MODE = true;

    public static DaoSession sDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        if (DEV_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode 针对执行比较耗时的检查
                    .detectDiskReads()  //磁盘读写
                    .detectDiskWrites() //1.datectNetWork()用于检测UI线程是否有网络线程
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    //.penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen() //API等级11
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks() //检查activity内存泄露 VmPolicy虚拟机策略检测
                    .detectLeakedSqlLiteObjects()//用于检测数据库游标检测是否正确关闭 VmPolicy虚拟机策略检测
                    .detectLeakedClosableObjects() //用于资源使用没有正确关闭时 做出提醒 VmPolicy虚拟机策略检测
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        initDataBase();
    }

    public static App getInstance(){
        return sApp;
    }

    private void initDataBase(){

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constant.sql_Name_key, null);
        SQLiteDatabase db = helper.getWritableDatabase();

        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
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
