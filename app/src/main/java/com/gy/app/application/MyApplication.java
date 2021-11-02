package com.gy.app.application;


import com.example.ltbase.base_application.BaseApplication;
import com.example.ltpay.PayConstants;
import com.example.ltpay.pay.ali.ALiClient;
import com.example.ltpay.pay.wx.WXClient;

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        WXClient.init(PayConstants.APP_ID);
        ALiClient.init(PayConstants.ALI_APP_ID);
    }
}
