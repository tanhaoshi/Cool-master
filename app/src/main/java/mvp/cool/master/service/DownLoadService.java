package mvp.cool.master.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.socks.library.KLog;

import mvp.cool.master.Constant;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/23.
 */

public class DownLoadService extends Service{

    private volatile boolean isTrue = false ;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0 :
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        isTrue = intent.getBooleanExtra("flag" , false);
        if(Constant.START_SERVICE.equals(intent.getStringExtra(Constant.SERVICE_FLAG))){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mHandler.obtainMessage(0).sendToTarget();
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            int i = 1;
            while (isTrue){
                try {
                    Thread.sleep(1000);
                    KLog.i("i:"+i++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
