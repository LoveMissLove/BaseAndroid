package com.example.ltbase.base_application;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

import android.app.Application;
import android.util.Log;

import com.example.ltbase.BuildConfig;
import com.example.ltbase.base_http.RxHttpManager;
import com.example.ltbase.base_manager.ActivityManager;
import com.example.ltbase.base_service.OKHttpUpdateHttpService;
import com.example.ltbase.base_utils.LogUtils;
import com.example.ltbase.base_utils.ToastUtils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.util.HashMap;

public class BaseApplication extends Application {
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }
    private void init() {
        //日志是否打印
        LogUtils.setShowLog(BuildConfig.LOG_DEBUG);
        //初始化网络请求框架
        RxHttpManager.init(this, BuildConfig.LOG_DEBUG);
        //初始化版本更新
        showXUpdate();
        initX5Web();
        // Activity 栈管理初始化
        ActivityManager.getInstance().init(this);
    }

    private void showXUpdate() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(true)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(this))         //设置默认公共请求参数
                .param("appKey", getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                            ToastUtils.showToast(error.toString());
                            LogUtils.e(error.toString());
                        }
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);
    }
    private void initX5Web() {
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setTbsListener(
                new TbsListener() {
                    @Override
                    public void onDownloadFinish(int i) {
                        //下载结束时的状态，下载成功时errorCode为100,其他均为失败，外部不需要关注具体的失败原因
                        Log.d("QbSdk", "onDownloadFinish -->下载X5内核完成：" + i);
                    }

                    @Override
                    public void onInstallFinish(int i) {
                        //安装结束时的状态，安装成功时errorCode为200,其他均为失败，外部不需要关注具体的失败原因
                        Log.d("QbSdk", "onInstallFinish -->安装X5内核进度：" + i);
                    }

                    @Override
                    public void onDownloadProgress(int i) {
                        //下载过程的通知，提供当前下载进度[0-100]
                        Log.d("QbSdk", "onDownloadProgress -->下载X5内核进度：" + i);
                    }
                });

        QbSdk.PreInitCallback cb =
                new QbSdk.PreInitCallback() {
                    @Override
                    public void onViewInitFinished(boolean arg0) {
                        // x5內核初始化完成的回调，true表x5内核加载成功，否则表加载失败，会自动切换到系统内核。
                        Log.d("QbSdk", " 内核加载 " + arg0);
                    }

                    @Override
                    public void onCoreInitFinished() {
                        //内核初始化完毕
                        Log.d("QbSdk", "内核初始化完毕");
                    }
                };

        // x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        //Log.i("QbSdk", "是否可以加载X5内核: " + QbSdk.canLoadX5(this));
        Log.i("QbSdk", "app是否主动禁用了X5内核: " + QbSdk.getIsSysWebViewForcedByOuter());
    }
}
