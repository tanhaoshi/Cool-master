package mvp.cool.master;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author TanHao
 * @version  1.0
 * 2017年4月25日10:15:25
 */

public class App extends Application{

    public static App sApp;

    private boolean DEV_MODE = true;

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
    }

    public static App getInstance(){
        return sApp;
    }
}
