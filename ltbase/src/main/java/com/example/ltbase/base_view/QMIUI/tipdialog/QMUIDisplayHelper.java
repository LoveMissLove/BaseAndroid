package com.example.ltbase.base_view.QMIUI.tipdialog;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by I Love You on 2019/5/27.
 */
public class QMUIDisplayHelper {
    /**
     * 单位转换: dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }

    /**
     * 单位转换: sp -> px
     *
     * @param sp
     * @return
     */
    public static int sp2px(Context context, int sp) {
        return (int) (getFontDensity(context) * sp + 0.5);
    }
    public static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
    /**
     * 获取 DisplayMetrics
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
