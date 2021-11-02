package com.example.ltbase.base_utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.ltbase.base_callback.OnNetworkStateListener;
import com.example.ltbase.base_handler.BaseHandler;

/**
 * 作者：王健 on 2021/8/24
 * 邮箱：845040970@qq.com
 * 描述：网络状态监听
 */
public class NetworkStateUtils {
    private OnNetworkStateListener onNetworkStateListener;
    private final Context context;
    private ConnectivityManager.NetworkCallback callback;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ConnectivityManager connectivityManager;
    public NetworkStateUtils(Context context) {
        this.context=context;
        showConnectivityManager();
    }
    private void showConnectivityManager() {
        callback = new ConnectivityManager.NetworkCallback(){
            //可用网络接入
            //当我们的网络的某个能力发生了变化回调，会回调多次
            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                //一般在此处获取网络类型然后判断网络类型
            }
            //网络可用的回调
            @Override
            public void onAvailable(@NonNull Network network) {
                //和 onLost 成对出现
                if(null!=onNetworkStateListener){
                    onNetworkStateListener.onNetWorkConnect();
                    LogUtils.d("网络可用");
                }
            }
            //网络断开
            @Override
            public void onLost(@NonNull Network network) {
                //如果通过ConnectivityManager#getActiveNetwork()返回null，表示当前已经没有其他可用网络了。
                //TODO 延时操作是为了准确接收网络状态
                handler.postDelayed(new MyThread(),1000);
            }
        };
    }



    //注册回调
    public  void registerNetworkCallback(Context context) {
        //获取 ConnectivityManager
         connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null!=connectivityManager){
            //获取 NetworkRequest 的 Builder
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            //强制使用蜂窝数据网络-移动数据
            //builder.addTransportType(TRANSPORT_CELLULAR);
            //强制使用wifi网络
            //builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);

            //注册
            NetworkRequest networkRequest = builder.build();
            connectivityManager.registerNetworkCallback(networkRequest,callback);
        }


    }
    //注销回调
    public void unregisterNetworkCallback(Context context) {
        //获取 ConnectivityManager
         connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null!=connectivityManager){
            //注销
            connectivityManager.unregisterNetworkCallback(callback);
            connectivityManager=null;
        }
        handler.removeCallbacksAndMessages(null);

    }

    public void setOnNetworkStateListener(OnNetworkStateListener onNetworkStateListener) {
        this.onNetworkStateListener = onNetworkStateListener;
    }


    class MyThread implements Runnable {
        @Override
        public void run() {
            //原来想要执行的代码
            if(!NetUtil.isWifiEnabled(context)&&!NetUtil.isNetworkAvailable(context)){
                if(null!=onNetworkStateListener){
                    onNetworkStateListener.onNetWorkDisconnect();
                    LogUtils.d("网络断开");
                }
            }

        }
    }

}
