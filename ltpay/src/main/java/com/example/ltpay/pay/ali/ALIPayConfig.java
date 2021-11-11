package com.example.ltpay.pay.ali;

/**
 * 调用支付的必要条件
 **/
public class ALIPayConfig {
    private ALIPayCallBack callBack;
    private String mOrderInfo;
    private Boolean isAutoFinish;
    private Boolean isAliSandbox;
    private Boolean isALIDialog;
    private String ALI_APP_RES2;

    private ALIPayConfig(Builder builder) {
        this.mOrderInfo = builder.orderInfo;
        this.isAutoFinish = builder.isAutoFinish;
        this.callBack = builder.callBack;
        this.isAliSandbox = builder.isAliSandbox;
        this.ALI_APP_RES2 = builder.ALI_APP_RES2;
        this.isALIDialog = builder.isALIDialog;
    }

    public String getOrderInfo() {
        return mOrderInfo;
    }

    public ALIPayCallBack getCallBack() {
        return callBack;
    }

    public Boolean getAutoFinish() {
        return isAutoFinish;
    }

    public Boolean getIsALIDialog() { return isALIDialog; }

    public Boolean getIsAliSandbox() { return isAliSandbox; }

    public String getALI_APP_RES2() { return ALI_APP_RES2; }

    public static final class Builder {
        private ALIPayCallBack callBack;
        private String orderInfo;
        private Boolean isAutoFinish = false;
        private Boolean isAliSandbox = false;
        private Boolean isALIDialog = false;
        private String ALI_APP_RES2;

        //设置支付参数
        public Builder setPayInfo(String payInfo) {
            orderInfo = payInfo;
            return this;
        }

        //是否自动关闭结果界面
        public Builder setIsAutoFinish(Boolean isAutoFinish) {
            this.isAutoFinish = isAutoFinish;
            return this;
        }

        //是否开启支付宝调起过渡dialog
        public Builder setIsALIDialog(Boolean isALIDialog) {
            this.isALIDialog = isALIDialog;
            return this;
        }

        //是否启用沙箱环境,如果你的targetSdkVersion =30请修改清单文件内的支付宝可见性包名
        public Builder setIsAliSandbox(Boolean isAliSandbox) {
            this.isAliSandbox = isAliSandbox;
            return this;
        }

        //设置支付宝ALI_APP_RES2
        public Builder setALI_APP_RES2(String ALI_APP_RES2) {
            this.ALI_APP_RES2 = ALI_APP_RES2;
            return this;
        }

        //设置回调
        public Builder setCallBack(ALIPayCallBack callBack) {
            this.callBack = callBack;
            return this;
        }

        public ALIPayConfig Build() {
            return new ALIPayConfig(this);
        }
    }
}
