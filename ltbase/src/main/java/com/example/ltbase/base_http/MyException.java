package com.example.ltbase.base_http;

import android.app.Activity;

import com.example.ltbase.base_utils.LogUtils;

/**
 * 作者：王健 on 2021/8/10
 * 邮箱：845040970@qq.com
 * 描述：根据后台规定code状态执行不同状态下的业务逻辑
 */
public class MyException extends Exception{

    public MyException(String errorCode, String errorMsg) {
        showExceptionCallback(errorCode,errorMsg);
    }
    /**
     * 描述：描述：根据后台规定code状态执行不同状态下的业务逻辑
     * @param :[errorCode, errorMsg]
     */
    private void showExceptionCallback(String errorCode, String errorMsg) {
        LogUtils.e("接口异常："+"errorCode："+"，"+"errorMsg："+errorMsg);

    }
}
