package com.example.ltbase.base_utils;


import static com.example.ltbase.base_utils.LogUtils.TAG_FORMAT;
import static com.example.ltbase.base_utils.LogUtils.VERTICAL;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.ltbase.base_application.BaseApplication;


/**
 * 作者：王健 on 2021/8/23
 * 邮箱：845040970@qq.com
 * 描述：Toast工具类
 */

public class ToastUtils {

    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast toast = null;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 显示Toast
     *
     * @param message 显示Toast信息
     */
    public static void showToast( String message) {
        if (BaseApplication.getInstance() == null) {
            return;
        }

        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
        LogUtils.d(generateTag(getStackTraceElement(4)));

    }
    /**
     * 根据堆栈生成TAG
     * @return TAG|className.methodName(L:lineNumber)
     */
    @SuppressLint("DefaultLocale")
    private static String generateTag(StackTraceElement caller) {
        String tag = TAG_FORMAT;
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return "ToastUtils" + VERTICAL + tag;
    }
    /**
     * 获取堆栈
     * @param n
     * 		n=0		VMStack
     * 		n=1		Thread
     * 		n=3		CurrentStack
     * 		n=4		CallerStack
     * 		...
     * @return 返回堆栈
     */
    public static StackTraceElement getStackTraceElement(int n) {
        return Thread.currentThread().getStackTrace()[n];
    }
}
