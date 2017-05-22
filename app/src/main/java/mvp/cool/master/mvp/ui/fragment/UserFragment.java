package mvp.cool.master.mvp.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mvp.cool.master.R;
import mvp.cool.master.layout.CircleImageView;
import mvp.cool.master.mvp.ui.activity.BillActivity;
import mvp.cool.master.mvp.ui.activity.MeBankActivity;
import mvp.cool.master.mvp.ui.activity.MeLocationActivty;
import mvp.cool.master.mvp.ui.activity.QrCodeActivity;
import mvp.cool.master.mvp.ui.activity.SafetyCenterActivity;
import mvp.cool.master.mvp.ui.fragment.base.BaseFragment;

import static mvp.cool.master.R.id.ivAvatar;

/**
 * @version 1.0
 * @author TanHaoShi
 * Created by Administrator on 2017/5/17.
 */

public class UserFragment extends BaseFragment{

    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(ivAvatar)
    CircleImageView mCircleImageView;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.meFollow)
    TextView tvFollow;
    @BindView(R.id.textFollow)
    TextView textFollow;
    @BindView(R.id.meFans)
    TextView meFans;
    @BindView(R.id.mePhone)
    TextView mePhone;
    @BindView(R.id.meSafety)
    TextView meSafety;
    @BindView(R.id.meBank)
    TextView meBank;
    @BindView(R.id.meBill)
    TextView meBill;
    @BindView(R.id.meLocation)
    TextView meLocation;
    @BindView(R.id.meCoupon)
    TextView meCoupon;
    @BindView(R.id.meShop)
    TextView meShop;
    @BindView(R.id.mePassword)
    TextView mePassword;
    @BindView(R.id.mine_recommond)
    TextView meRecommond;
    @BindView(R.id.mine_consume)
    TextView meConsume;

    @Override
    protected void initComponent() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void showProgress(boolean isTrue) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @OnClick({R.id.ivAvatar ,
              R.id.btnLogin ,
              R.id.meSafety ,
              R.id.meBank ,
              R.id.meBill ,
              R.id.meLocation ,
              R.id.meCoupon ,
              R.id.meShop ,
              R.id.mePassword ,
              R.id.mine_recommond ,
              R.id.mine_consume})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ivAvatar:
                break;
            case R.id.btnLogin:
                break;
            case R.id.meSafety:
                meSafetycenter();
                break;
            case R.id.meBank:
                startActivityFinish(MeBankActivity.class);
                break;
            case R.id.meBill:
                startActivityFinish(BillActivity.class);
                break;
            case R.id.meLocation:
                meSetLocation();
                break;
            case R.id.meCoupon:
                break;
            case R.id.meShop:
                break;
            case R.id.mePassword:
                break;
            case R.id.mine_recommond:
                break;
            case R.id.mine_consume:
                startActivityFinish(QrCodeActivity.class);
                break;
        }
    }

    private void meSafetycenter(){
        startActivityFinish(SafetyCenterActivity.class);
    }

    private void meSetLocation(){
        startActivityFinish(MeLocationActivty.class);
    }
}
