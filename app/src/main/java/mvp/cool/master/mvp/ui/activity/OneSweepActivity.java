package mvp.cool.master.mvp.ui.activity;

import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class OneSweepActivity extends BaseActivity implements QRCodeView.Delegate{

    @BindView(R.id.qrCode)
    TextView qrCode;
    @BindView(R.id.qrPhone)
    TextView qrPhone;
    @BindView(R.id.zxingview)
    ZXingView mZXingView;

    @Override
    protected int getContentView() {
        return R.layout.activity_one_sweep;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("消费二维码");
        qrPhone.setTextColor(getResources().getColor(R.color.deep_yellow));
        mZXingView.setDelegate(this);
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.qrCode , R.id.qrPhone,R.id.base_iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.qrCode:
                startActivity(QrCodeActivity.class);
                break;
            case R.id.qrPhone:
                startActivity(OneSweepActivity.class);
                break;
            case R.id.base_iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i("OneSweepActivity", "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mZXingView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("OneSweepActivity", "打开相机出错");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera();
        mZXingView.showScanRect();
        mZXingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

}
