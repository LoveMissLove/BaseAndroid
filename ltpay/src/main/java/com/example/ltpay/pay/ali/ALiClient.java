package com.example.ltpay.pay.ali;

import android.text.TextUtils;

/**
 *支付宝端
 **/
public class ALiClient {
    //支付宝APP_ID
    private static String ALI_APP_ID = "";
    /**
     * 初始化
     */
    public static void init(String appId) {
        ALI_APP_ID = appId;
        if (TextUtils.isEmpty(ALI_APP_ID)) {
            throw new NullPointerException("支付宝AppId不能为NULL");
        }
    }
    public static String getALiAppId() {
        if (TextUtils.isEmpty(ALI_APP_ID)) {
            throw new NullPointerException("你还未调用ALiClient.init()");
        }
        return ALI_APP_ID;
    }
}
