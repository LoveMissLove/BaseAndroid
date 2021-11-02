package com.example.ltpay.pay.wx;

import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Map;

/**
 *调用支付的必要条件
 **/
public class WXPayConfig {
    private final WXPayCallBack callBack;
    private final PayReq req;
    //自动关闭微信结果
    private final Boolean isAutoFinish;
    private WXPayConfig(Builder builder) {
        this.req = builder.req;
        this.isAutoFinish = builder.isAutoFinish;
        this.callBack = builder.callBack;
    }

    public PayReq getReq() {
        return req;
    }

    public WXPayCallBack getCallBack() {
        return callBack;
    }

    public Boolean getAutoFinish() {
        return isAutoFinish;
    }

    public static final class Builder {
        private WXPayCallBack callBack;
        private PayReq req;
        private Boolean isAutoFinish = false;
        //设置支付参数
        public Builder setPayInfo(Map<String,Object> payInfo) {
            req = new PayReq();
            req.appId = payInfo.get("appid").toString();
            req.partnerId = payInfo.get("partnerid").toString();
            req.prepayId = payInfo.get("prepayid").toString();
            req.nonceStr = payInfo.get("noncestr").toString();
            req.timeStamp = payInfo.get("timestamp").toString();
            req.packageValue = payInfo.get("package").toString();
            req.sign = payInfo.get("sign").toString();
            return this;
        }
        //是否自动关闭结果界面
        public Builder setIsAutoFinish(Boolean isAutoFinish) {
            this.isAutoFinish = isAutoFinish;
            return this;
        }

        //设置回调
        public Builder setCallBack(WXPayCallBack callBack) {
            this.callBack = callBack;
            return this;
        }
        public WXPayConfig Build() {
            return new WXPayConfig(this);
        }
    }
}
