package com.gy.app;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_utils.ToastUtils;

/**
 * 作者：王健 on 2021/9/3
 * 邮箱：845040970@qq.com
 * 描述：网络监听
 */
public class NetWorkListenerActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_network_listener;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void showNetWorkConnect() {
        super.showNetWorkConnect();
        ToastUtils.showToast("网络连接了");
    }

    @Override
    protected void showNetWorkDisconnect() {
        super.showNetWorkDisconnect();
        ToastUtils.showToast("网络断开了");

    }

}
