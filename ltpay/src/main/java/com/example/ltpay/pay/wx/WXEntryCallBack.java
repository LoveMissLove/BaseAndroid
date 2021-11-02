package com.example.ltpay.pay.wx;

/**
 * 微信回调
 **/
public interface WXEntryCallBack {
    //成功
    void entrySuccess(String code);

    //失败
    void entryFail(int errorCode, String errorMsg);
}
