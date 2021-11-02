package com.example.ltpay.pay.wx;

import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.Map;

/**
 *调用微信的必要条件
 **/
public class WXEntryConfig {
    private final WXEntryCallBack callBack;
    private final SendAuth.Req req;
    //自动关闭微信结果
    private final Boolean isAutoFinish;
    private WXEntryConfig(Builder builder) {
        this.req = builder.req;
        this.isAutoFinish = builder.isAutoFinish;
        this.callBack = builder.callBack;
    }

    public SendAuth.Req getReq() {
        return req;
    }

    public WXEntryCallBack getCallBack() {
        return callBack;
    }

    public Boolean getAutoFinish() {
        return isAutoFinish;
    }

    public static final class Builder {
        private WXEntryCallBack callBack;
        private SendAuth.Req req;
        private Boolean isAutoFinish = false;
        //设置支付参数
        public Builder setEntryInfo(Map<String,String> payInfo) {
            req = new SendAuth.Req();
            req.scope = payInfo.get("scope");
            req.state = payInfo.get("state");
            return this;
        }
        //是否自动关闭结果界面
        public Builder setIsAutoFinish(Boolean isAutoFinish) {
            this.isAutoFinish = isAutoFinish;
            return this;
        }

        //设置回调
        public Builder setCallBack(WXEntryCallBack callBack) {
            this.callBack = callBack;
            return this;
        }
        public WXEntryConfig Build() {
            return new WXEntryConfig(this);
        }
    }
}
