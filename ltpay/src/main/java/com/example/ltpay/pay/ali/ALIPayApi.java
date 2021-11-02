package com.example.ltpay.pay.ali;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
  *支付宝支付逻辑
 **/
public class ALIPayApi {
    private static ALIPayApi payApi;
    private ALIPayConfig config;
    private static final int SDK_PAY_FLAG = 1;
    private Activity activity;
    private ALIPayApi(Context mContext) {
        activity= (Activity) mContext;
    }


    public static ALIPayApi getInstance(Context mContext) {
        if (payApi == null) {
            payApi = new ALIPayApi(mContext);
        }
        return payApi;
    }
    //发起支付请求
    public void doALIPay(ALIPayConfig config,Activity activity) {
        this.config = config;
        String orderInfo=config.getOrderInfo();
        if(config.getIsAliSandbox()){
            if (TextUtils.isEmpty(config.getALI_APP_RES2())) {
                throw new NullPointerException("你还未设置支付宝ALI_APP_RES2");
            }
            //开启沙箱环境
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//支付宝沙箱环境，正式环境要删掉
            boolean rsa2 = (config.getALI_APP_RES2().length() > 0);
            Map<String, String> params = AliOrderInfoUtils.buildOrderParamMap(ALiClient.getALiAppId(), rsa2);
            String orderParam = AliOrderInfoUtils.buildOrderParam(params);

            String privateKey = config.getALI_APP_RES2();
            String sign = AliOrderInfoUtils.getSign(params, privateKey, rsa2);
            orderInfo= orderParam + "&" + sign;
        }
        String finalOrderInfo = orderInfo;
        if (TextUtils.isEmpty(finalOrderInfo)) {
            throw new NullPointerException("你还未设置支付宝OrderInfo");
        }
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(finalOrderInfo, config.getIsALIDialog());
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG) {
                @SuppressWarnings("unchecked")
                AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
                //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultMemo = payResult.getMemo();
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    config.getCallBack().paySuccess();
                } else {
                    config.getCallBack().payFail(Integer.parseInt(resultStatus), resultMemo);
                }
                if(config.getAutoFinish()){
                    activity.finish();
                }
            }
        };
    };
}
