package com.example.ltpay.pay.wx;

import android.text.TextUtils;

/**
 *微信端
 **/
public class WXClient {
    //微信APP_ID
    private static String WX_APP_ID = "";
    /**
     * 初始化
     *
     * @param appId 微信初始化
     */
    public static void init(String appId) {
        WX_APP_ID = appId;
        if (TextUtils.isEmpty(WX_APP_ID)) {
            throw new NullPointerException("微信APPID不能为NULL");
        }
    }
    public static String getWxAppId() {
        if (TextUtils.isEmpty(WX_APP_ID)) {
            throw new NullPointerException("你还未调用WXClient.init()");
        }
        return WX_APP_ID;
    }
}
