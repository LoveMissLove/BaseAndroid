package com.example.ltpay.pay.wx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 *微信分享、登录
 **/
public class WXEntryApi {

    private static WXEntryApi entryApi;

    private final IWXAPI api;

    private WXEntryConfig config;

    private final WXEntryHandler handler;

    private WXEntryApi(Context mContext) {
        api = WXAPIFactory.createWXAPI(mContext.getApplicationContext(), WXClient.getWxAppId(), true);
        api.registerApp(WXClient.getWxAppId());
        handler = new WXEntryHandler();
    }


    public static WXEntryApi getInstance(Context mContext) {
        if (entryApi == null) {
            entryApi = new WXEntryApi(mContext);
        }
        return entryApi;
    }

    //处理回调
    public void handleIntent(Activity activity, Intent intent) {
        handler.setActivity(activity);
        api.handleIntent(intent, handler);
    }

    //发起操作
    public void doWXEntry(WXEntryConfig config) {
        this.config = config;
        if (!api.isWXAppInstalled()) {
            config.getCallBack().entryFail(3, "Not Installed or Not Support");
        }
        SendAuth.Req req = config.getReq();
        //调起微信
        api.sendReq(req);
    }

    //回调
    private class WXEntryHandler implements IWXAPIEventHandler {

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
                String code = ((SendAuth.Resp) baseResp).code;
                config.getCallBack().entrySuccess(code);
            } else {
                config.getCallBack().entryFail(baseResp.errCode, baseResp.errStr);
            }
            //销毁
            if (activity != null && config.getAutoFinish()) {
                activity.finish();
            }
            config=null;
            activity=null;
        }
    }
}
