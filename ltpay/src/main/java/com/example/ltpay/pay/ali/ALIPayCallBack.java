package com.example.ltpay.pay.ali;

/**
 * 支付宝支付回调
 **/
public interface ALIPayCallBack {
    //支付成功
    void paySuccess();
    //支付失败
    void payFail(int errorCode, String errorMsg);
}
