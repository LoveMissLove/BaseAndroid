package com.gy.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_bean.Response;
import com.example.ltbase.base_callback.OnDownloadListener;
import com.example.ltbase.base_http.HttpUrl;
import com.example.ltbase.base_http.LoadingObserver;
import com.example.ltbase.base_utils.HProgressDialogUtils;
import com.example.ltbase.base_utils.RxViewUtils;
import com.example.ltbase.base_utils.ToastUtils;
import com.gy.app.bean.AppVersionBean;
import com.google.gson.Gson;

import io.reactivex.rxjava3.core.Observable;


/**
 * 作者：王健 on 2021/8/27
 * 邮箱：845040970@qq.com
 * 描述：网络请求
 */
public class HttpActivity extends BaseActivity  {
    private Button btnGet,btnPost,btnDownLoad,btnDownLoadListener,btnUpLoad,btnUpLoadListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {
        btnGet= F(R.id.btnGet);
        btnPost= F(R.id.btnPost);
        btnDownLoad= F(R.id.btnDownLoad);
        btnDownLoadListener= F(R.id.btnDownLoadListener);
        btnUpLoad= F(R.id.btnUpLoad);
        btnUpLoadListener= F(R.id.btnUpLoadListener);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        RxViewUtils.showClick(btnGet,this);
        RxViewUtils.showClick(btnPost,this);
        RxViewUtils.showClick(btnDownLoad,this);
        RxViewUtils.showClick(btnDownLoadListener,this);
        RxViewUtils.showClick(btnUpLoad,this);
        RxViewUtils.showClick(btnUpLoadListener,this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGet:
                showGetHttp();

                break;
            case R.id.btnPost:
                ToastUtils.showToast("暂无接口测试");
                break;
            case R.id.btnDownLoad:
                showDownLoad();
                break;
            case R.id.btnDownLoadListener:
                showDownLoadListener();
                break;
            case R.id.btnUpLoad:
                ToastUtils.showToast("暂无接口测试");
                break;
            case R.id.btnUpLoadListener:
                ToastUtils.showToast("暂无接口测试");
                break;
            default:
        }
    }


    private void showGetHttp() {
        Observable.just(1)
                .flatMap(integer -> new HttpService<AppVersionBean>().get(HttpUrl.APP_VERSION,AppVersionBean.class))
                .subscribe(new LoadingObserver<Response<AppVersionBean>>((Activity) context,true){
                    @Override
                    public void onNext(@NonNull Response<AppVersionBean> appVersionBeanResponse) {
                        super.onNext(appVersionBeanResponse);
                        ToastUtils.showToast(new Gson().toJson(appVersionBeanResponse));
                    }
                });
    }
    private void showDownLoad() {
        Observable.just(1)
                .flatMap(integer -> new HttpService<Uri>().getFileDownload("https://xuexiangjys.oss-cn-shanghai.aliyuncs.com/apk/xupdate_demo_1.0.2.apk","module.apk",context))
                .subscribe(new LoadingObserver<Uri>((Activity) context,true){
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Uri uri) {
                        super.onNext(uri);
                        ToastUtils.showToast("下载完成");
                    }
                });
    }

    private void showDownLoadListener() {
        HProgressDialogUtils.showHorizontalProgressDialog(context,"下载进度",false);
        Observable.just(1)
                .flatMap(integer -> new HttpService<Uri>()
                        .getFileDownloadProgress("https://xuexiangjys.oss-cn-shanghai.aliyuncs.com/apk/xupdate_demo_1.0.2.apk", "module.apk", context, new OnDownloadListener() {
                    @Override
                    public void onAccept(int currentProgress, long currentSize, long totalSize) {
                        HProgressDialogUtils.setProgress(Math.round(currentProgress));
                    }
                })).subscribe(new LoadingObserver<Uri>((Activity) context,false){
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Uri appVersionBeanResponse) {
                super.onNext(appVersionBeanResponse);
                        HProgressDialogUtils.cancel();
            }
        });
    }
}
