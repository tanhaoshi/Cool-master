package mvp.cool.master.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import mvp.cool.master.R;
import mvp.cool.master.mvp.ui.activity.base.BaseActivity;

public class QrCodeActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.qrCode)
    TextView qrCode;
    @BindView(R.id.qrPhone)
    TextView qrPhone;

    @Override
    protected int getContentView() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initView() {
        base_image.setVisibility(View.VISIBLE);
        base_title.setText("消费二维码");
        qrCode.setTextColor(getResources().getColor(R.color.deep_yellow));
        createChineseQRCode();
        //requestPermiSsiongs();
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initListener() {

    }

    private void createChineseQRCode() {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(QrCodeActivity.this.getResources(), R.mipmap.ic_launcher);
                return QRCodeEncoder.syncEncodeQRCode("http://github.com/tanhaoshi", BGAQRCodeUtil.dp2px(QrCodeActivity.this, 150), Color.parseColor("#ff0000"), logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(QrCodeActivity.this, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @OnClick({R.id.qrCode, R.id.qrPhone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qrCode:
                startActivity(QrCodeActivity.class);
                break;
            case R.id.qrPhone:
                startActivity(OneSweepActivity.class);
                break;
        }
    }

//    private void requestPermiSsiongs() {
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
//    }

}
