package com.example.ltbase.base_http;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.ltbase.base_application.BaseApplication;


/**
 * 作者：王健 on 2021/8/4
 * 邮箱：845040970@qq.com
 * 描述：可在任意线程执行本类方法
 */

public class Tip {

    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Toast mToast;

    public static void show(int msgResId) {
        show(msgResId, false);
    }

    public static void show(int msgResId, boolean timeLong) {
        show(BaseApplication.getInstance().getString(msgResId), timeLong);
    }

    public static void show(CharSequence msg) {
        show(msg, false);
    }

    public static void show(final CharSequence msg, final boolean timeLong) {
        runOnUiThread(() -> {
            if (mToast != null) {
                mToast.cancel();
            }
            int duration = timeLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
            mToast = Toast.makeText(BaseApplication.getInstance(), msg, duration);
            mToast.show();
        });
    }

    private static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }
}
