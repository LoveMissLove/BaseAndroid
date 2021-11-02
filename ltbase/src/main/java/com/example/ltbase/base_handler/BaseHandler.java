package com.example.ltbase.base_handler;

import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * 作者：王健 on 2021/10/26
 * 邮箱：845040970@qq.com
 * 描述：解决handler内存泄漏问题
 */
public class BaseHandler extends Handler {

    final WeakReference<AppCompatActivity> act;
    final WeakReference<BaseHandlerCallBack> callBack;

    public BaseHandler(AppCompatActivity c, BaseHandlerCallBack b) {
        act = new WeakReference<>(c);
        callBack = new WeakReference<>(b);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        AppCompatActivity c = act.get();
        BaseHandlerCallBack b = callBack.get();
        if (c != null && b != null) {
            b.callBack(msg);
        }
    }

    public interface BaseHandlerCallBack {
        void callBack(Message msg);
    }
}