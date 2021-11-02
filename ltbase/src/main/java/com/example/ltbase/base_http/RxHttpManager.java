package com.example.ltbase.base_http;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.annotation.OkClient;
import rxhttp.wrapper.ssl.HttpsUtils;

/**
 * 作者：王健 on 2021/8/4
 * 邮箱：845040970@qq.com
 * 描述：网络请求管理
 */
public class RxHttpManager {
    @OkClient(name = "SimpleClient", className = "Simple")  //非必须
    public static OkHttpClient simpleClient = new OkHttpClient.Builder().build();
    /**
     * 描述：初始化OkHttpClient
     * @param :[context, isDebug=是否debug]
     */
    public static void init(Application context,boolean isDebug) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(10, TimeUnit.SECONDS)//设置读超时
                .writeTimeout(10, TimeUnit.SECONDS)//设置写超时
                .retryOnConnectionFailure(true)//是否自动重连
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
                .hostnameVerifier((hostname, session) -> true) //忽略host验证
                .addInterceptor(new RxHttpLogInterceptor())
                .build();
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        RxHttpPlugins.init(client).setDebug(isDebug);
    }
}
