package com.example.ltbase.base_http;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 异常处理帮助类
 */
public class ExceptionHelper {

    @SuppressWarnings("deprecation")
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }
}
