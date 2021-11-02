package com.example.ltpay.pay.wx;

/**
 * 微信支付回调
 **/
public interface WXPayCallBack {
    //支付成功
    void paySuccess();

    //支付失败
    void payFail(int errorCode, String errorMsg);
}
