package com.gy.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_activity.WebViewActivity;
import com.example.ltbase.base_bean.PermissionBean;
import com.example.ltbase.base_callback.OnLoadingLayoutRetryListener;
import com.example.ltbase.base_callback.OnPictureSelectorListener;
import com.example.ltbase.base_callback.OnRequestEachPermissions;
import com.example.ltbase.base_constant.PermissionConstant;
import com.example.ltbase.base_manager.UpDateAPPManager;
import com.example.ltbase.base_utils.GsonUtil;
import com.example.ltbase.base_utils.LogUtils;
import com.example.ltbase.base_utils.PermissionUtil;
import com.example.ltbase.base_utils.PictureSelectorUtils;
import com.example.ltbase.base_utils.RxViewUtils;
import com.example.ltbase.base_utils.ToastUtils;
import com.example.ltbase.base_view.PickerTime.TimeSelector;
import com.example.ltpay.PayConstants;
import com.example.ltpay.pay.ali.ALIPayApi;
import com.example.ltpay.pay.ali.ALIPayCallBack;
import com.example.ltpay.pay.ali.ALIPayConfig;
import com.example.ltpay.pay.wx.WXEntryApi;
import com.example.ltpay.pay.wx.WXEntryCallBack;
import com.example.ltpay.pay.wx.WXEntryConfig;
import com.example.ltpay.pay.wx.WXPayApi;
import com.example.ltpay.pay.wx.WXPayCallBack;
import com.example.ltpay.pay.wx.WXPayConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements OnLoadingLayoutRetryListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        TextView tvTitle = F(R.id.tv_title);
        tvTitle.setText("我是标题");
        ImageView imgBack = F(R.id.iv_left);
        Button btnPhoto = F(R.id.btnPhoto);
        Button btnHttp = F(R.id.btnHttp);
        Button btnPermission = F(R.id.btnPermission);
        Button btnLoadingLayout = F(R.id.btnLoadingLayout);
        Button btnNetWork = F(R.id.btnNetWork);
        Button btnUpDateApp = F(R.id.btnUpDateApp);
        Button btnLoadingTip = F(R.id.btnLoadingTip);
        Button btnWeb = F(R.id.btnWeb);
        Button btnPayMode = F(R.id.btnPayMode);
        Button btnTime=F(R.id.btnTime);
        Button btnTransfereeImg = F(R.id.btnTransfereeImg);
        RxViewUtils.showClick(btnTime,this);
        RxViewUtils.showClick(btnPhoto, this);
        RxViewUtils.showClick(btnHttp, this);
        RxViewUtils.showClick(btnPermission, this);
        RxViewUtils.showClick(btnLoadingLayout, this);
        RxViewUtils.showClick(btnNetWork, this);
        RxViewUtils.showClick(btnUpDateApp, this);
        RxViewUtils.showClick(btnLoadingTip, this);
        RxViewUtils.showClick(btnWeb, this);
        RxViewUtils.showClick(btnTransfereeImg, this);
        RxViewUtils.showClick(btnPayMode, this);
        RxViewUtils.showClick(imgBack, view -> finish());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRetryListener() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPhoto:
                PictureSelectorUtils.showPictureSelectorImage(this, 9, true, false, new OnPictureSelectorListener() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        ToastUtils.showToast(result.get(0).getPath());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.btnHttp:
                startActivity(new Intent(this, HttpActivity.class));
                break;
            case R.id.btnPermission:
                showPermission(PermissionConstant.CALL_PHONE, PermissionConstant.CAMERA);
                break;
            case R.id.btnUpDateApp:
                UpDateAPPManager upDateAPPManager = new UpDateAPPManager();
                upDateAPPManager.updateApp(context, "https://gitee.com/xuexiangjys/XUpdate/raw/master/jsonapi/update_custom.json", true);
                break;
            case R.id.btnLoadingLayout:
                startActivity(new Intent(context, LoadingStateLayoutActivity.class));
                break;
            case R.id.btnNetWork:
                startActivity(new Intent(context, NetWorkListenerActivity.class));

                break;
            case R.id.btnLoadingTip:
                startActivity(new Intent(context, LoadingTipDialogActivity.class));
                break;
            case R.id.btnWeb:
                startActivity(new Intent(context, WebViewActivity.class));
                break;
            case R.id.btnTransfereeImg:
                startActivity(new Intent(context, FriendsCircleActivity.class));
                break;
            case R.id.btnPayMode:
                showSimpleBottomSheetList(
                        true, true, false, "支付方式",
                        3, true, false);
                break;
            case R.id.btnTime:
                TimeSelector timeSelector=new TimeSelector(context, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        ToastUtils.showToast(time);
                    }
                },"2021-01-01 00:00","2021-12-30 00:00");
                timeSelector.show();
                break;
            default:
        }
    }

    private void showPay1() {
        ALIPayApi.getInstance(context).doALIPay( new ALIPayConfig.Builder()
                .setPayInfo("")
                .setALI_APP_RES2(PayConstants.ALI_APP_RES2)
                .setIsAliSandbox(true)
                .setIsALIDialog(true)
                .setIsAutoFinish(false)
                .setCallBack(new ALIPayCallBack() {
                    @Override
                    public void paySuccess() {
                        ToastUtils.showToast("支付成功");
                    }

                    @Override
                    public void payFail(int errorCode, String errorMsg) {
                        ToastUtils.showToast("errorCode：" + errorCode + "errorMsg：" + errorMsg);
                    }

                }).Build(), context);
    }

    private void showPay() {
        String url = "https://wxpay.wxutil.com/pub_v2/app/app_pay.php";
        String json = "{\"appid\":\"wx9641de1429cba6a0\",\"partnerid\":\"1900006771\",\"package\":\"Sign=WXPay\",\"noncestr\":\"8b68c7572e7b27898cb9d2de48084f95\",\"timestamp\":1604041908,\"prepayid\":\"wx30151148076154379322d5118c24ac0000\",\"sign\":\"MD5\"}";
        Map map = GsonUtil.getGson().fromJson(json, Map.class);
        WXPayApi.getInstance(context).doWXPay(new WXPayConfig.Builder().setPayInfo(map).setIsAutoFinish(true).setCallBack(new WXPayCallBack() {
            @Override
            public void paySuccess() {
                ToastUtils.showToast("支付成功");
            }

            @Override
            public void payFail(int errorCode, String errorMsg) {
                ToastUtils.showToast("errorCode：" + errorCode + "errorMsg：" + errorMsg);

            }
        }).Build());
    }

    private void showPay2() {
        Map<String, String> map = new HashMap<>();
        map.put("scope", "snsapi_userinfo");
        map.put("state", "wechat_sdk_demo_test");
        WXEntryApi.getInstance(context).doWXEntry(new WXEntryConfig.Builder().setEntryInfo(map).setIsAutoFinish(true).setCallBack(new WXEntryCallBack() {
            @Override
            public void entrySuccess(String code) {
                ToastUtils.showToast(code);
            }

            @Override
            public void entryFail(int errorCode, String errorMsg) {
                ToastUtils.showToast("errorCode：" + errorCode + "errorMsg：" + errorMsg);
            }
        }).Build());
    }

    private void showPermission(String... permissionsGroup) {
        PermissionUtil permissionUtil = new PermissionUtil();
        permissionUtil.showMoreRequestEachPermissions(this, new OnRequestEachPermissions() {
            @Override
            public void onShouldShowRationale(List<PermissionBean> deniedPermission) {
                ToastUtils.showToast("授权失败勾选了不在询问：" + GsonUtil.getGson().toJson(deniedPermission));
            }

            @Override
            public void onDenied(List<PermissionBean> deniedPermission) {
                ToastUtils.showToast("授权失败：" + GsonUtil.getGson().toJson(deniedPermission));
            }

            @Override
            public void onGranted() {
                ToastUtils.showToast("全部授权");
            }
        }, permissionsGroup);
    }
    // ================================ 生成不同类型的BottomSheet
    private void showSimpleBottomSheetList(boolean gravityCenter,
                                           boolean addCancelBtn,
                                           boolean withIcon,
                                           CharSequence title,
                                           int itemCount,
                                           boolean allowDragDismiss,
                                           boolean withMark) {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(context);
        builder.setGravityCenter(gravityCenter)//是否显示在中间
                .setSkinManager(QMUISkinManager.defaultInstance(context))
                .setTitle(title)//标题
                .setAddCancelBtn(addCancelBtn)//是否显示取消按钮
                .setAllowDrag(allowDragDismiss)
                .setNeedRightMark(withMark)//设置要被选中的 Item 的下标
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        LogUtils.e(tag);
                        if(position==0){
                            showPay1();
                        }else if(position==1){
                            showPay();
                        }else{
                            showPay2();
                        }
                    }
                });
        if(withMark){
            builder.setCheckedIndex(40);
        }
        builder.addItem("支付宝支付");
        builder.addItem("微信支付");
        builder.addItem("微信登录");
        builder.build().show();
    }
}