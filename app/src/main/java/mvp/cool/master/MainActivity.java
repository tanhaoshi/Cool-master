package mvp.cool.master;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //避免handler造成的内存泄露
    private static class MyHandler extends Handler {

        private final WeakReference<MainActivity> mWeakReference;

        private MyHandler(MainActivity mainActivty) {
            mWeakReference = new WeakReference<MainActivity>(mainActivty);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivty = mWeakReference.get();
            if(mainActivty != null){
                //
            }
        }
    }

    private final MyHandler mMyHandler = new MyHandler(this);

    public static Runnable sRunnable = new Runnable() {
        @Override
        public void run() {
            //
        }
    };
}
