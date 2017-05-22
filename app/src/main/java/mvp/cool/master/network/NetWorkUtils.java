package mvp.cool.master.network;

import mvp.cool.master.Constant;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @version 1.0
 * @author TanHao
 * Created by Administrator on 2017/5/15.
 */

public class NetWorkUtils {

    private static volatile NetWorkUtils sNetWorkUtils;

    private NetService mNetService;

    private static volatile OkHttpClient sOkHttpClient;

    private Retrofit mRetrofit;

    private NetWorkUtils(){
                 mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(NetWorkUtils.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static NetWorkUtils getInstance(){
        if(sNetWorkUtils == null){
            synchronized(NetWorkUtils.class){
                if(sNetWorkUtils == null){
                    sNetWorkUtils = new NetWorkUtils();
                }
            }
        }
        return sNetWorkUtils;
    }

    public static OkHttpClient getOkHttpClient(){
        if(sOkHttpClient == null){
            synchronized (NetWorkUtils.class){
                if(sOkHttpClient == null){
                    sOkHttpClient = OkHttpUtil.getInstance().getOkHttpClient();
                }
            }
        }
        return sOkHttpClient;
    }

    private Observable.Transformer backTransformer(){
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)observable).subscribeOn(Schedulers.io())//生产事件在io
                        .unsubscribeOn(Schedulers.io())//解除Io线程的订阅 避免内存泄露
                        .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                        ;
            }
        };
    }

    /**
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     * @param service 服务接口
     * @return T
     */
    @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
    public <T> T createService(final Class<T> service){
        return mRetrofit.create(service);
    }

}
