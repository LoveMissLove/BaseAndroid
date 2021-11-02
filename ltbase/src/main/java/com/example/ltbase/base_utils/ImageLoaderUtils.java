package com.example.ltbase.base_utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * 作者：王健 on 2021/8/25
 * 邮箱：845040970@qq.com
 * 描述：GlideEngine辅助类
 */
public class ImageLoaderUtils {

    public static boolean assertValidRequest(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !isDestroy(activity);
        } else if (context instanceof ContextWrapper){
            ContextWrapper contextWrapper = (ContextWrapper) context;
            if (contextWrapper.getBaseContext() instanceof Activity){
                Activity activity = (Activity) contextWrapper.getBaseContext();
                return !isDestroy(activity);
            }
        }
        return true;
    }

    private static boolean isDestroy(Activity activity) {
        if (activity == null) {
            return true;
        }
        return activity.isFinishing() || activity.isDestroyed();
    }

}