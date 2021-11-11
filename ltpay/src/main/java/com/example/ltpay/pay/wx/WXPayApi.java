package com.example.ltpay.pay.wx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 *微信支付支付逻辑
 **/
public class WXPayApi {

    private static WXPayApi payApi;

    private final IWXAPI api;

    private WXPayConfig config;

    private final WXPayHandler handler;

    private WXPayApi(Context mContext) {
        api = WXAPIFactory.createWXAPI(mContext.getApplicationContext(), WXClient.getWxAppId(), true);
        api.registerApp(WXClient.getWxAppId());
        handler = new WXPayHandler();
    }


    public static WXPayApi getInstance(Context mContext) {
        if (payApi == null) {
            payApi = new WXPayApi(mContext);
        }
        return payApi;
    }

    //处理回调
    public void handleIntent(Activity activity, Intent intent) {
        handler.setActivity(activity);
        api.handleIntent(intent, handler);
    }

    //发起支付
    public void doWXPay(WXPayConfig config) {
        this.config = config;
        if (!api.isWXAppInstalled()) {
            config.getCallBack().payFail(3, "Not Installed or Not Support");
        }
        PayReq payReq = config.getReq();
        //
        if (payReq.checkArgs()) {//判断支付参数是否有误
            //调起支付
            api.sendReq(config.getReq());
        } else {
            config.getCallBack().payFail(4, "Pay args not legal");
        }
    }

    //微信支付回调
    private class WXPayHandler implements IWXAPIEventHandler {

        private Activity activity;

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onReq(BaseReq baseReq) {

        }

        @Override
        public void onResp(BaseResp baseResp) {
            if (baseResp.errCode == 0) {
                config.getCallBack().paySuccess();
            } else {
                config.getCallBack().payFail(baseResp.errCode, baseResp.errStr);
            }
            //销毁
            if (activity != null && config.getAutoFinish()) {
                activity.finish();
            }
            config=null;
            activity = null;
        }
    }
}
