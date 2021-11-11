package com.example.ltbase.base_utils;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.ltbase.base_observer.ObserverRxViewClicks;
import com.jakewharton.rxbinding2.view.RxView;
import java.util.concurrent.TimeUnit;

/**
 * 作者：王健 on 2021/8/25
 * 邮箱：845040970@qq.com
 * 描述：RxView.clicks防抖动
 */
@SuppressLint("CheckResult")
public class RxViewUtils {
    // 两次点击按钮之间的点击间隔不能少于500毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;
    /**
     * 描述：防抖、防重复点击
     * @param view   点击事件的view
     * @param onRxViewClickListener 回调事件
     */
    public static void showClick(View view, View.OnClickListener onRxViewClickListener){
        long SECONDS = 1;
        RxView.clicks(view).throttleFirst(SECONDS,TimeUnit.SECONDS).subscribe(new ObserverRxViewClicks<Object>() {
            @Override
            public void onNext(@NonNull Object o) {
                super.onNext(o);
                if(null!=onRxViewClickListener){
                    onRxViewClickListener.onClick(view);
                }
            }
        });
    }
    /**
     * 描述：防抖、防重复点击
     * @param view 点击事件的view
     * @param SECONDS  秒数
     * @param onRxViewClickListener 回调事件
     */
    public static void showClick(View view,long SECONDS, View.OnClickListener onRxViewClickListener){
        RxView.clicks(view).throttleFirst(SECONDS,TimeUnit.SECONDS).subscribe(new ObserverRxViewClicks<Object>() {
            @Override
            public void onNext(@NonNull Object o) {
                super.onNext(o);
                if(null!=onRxViewClickListener){
                    onRxViewClickListener.onClick(view);
                }
            }
        });
    }
    /**
     * 描述：防止多次重复点击
     * @return true可响应点击事件
     */
    public static boolean isFirstClick(){
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    /**
     * 描述：防止多次重复点击
     * @return true可响应点击事件
     * @param MIN_CLICK_DELAY_TIME 毫秒数
     */
    public static boolean isFirstClick(long MIN_CLICK_DELAY_TIME){
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
