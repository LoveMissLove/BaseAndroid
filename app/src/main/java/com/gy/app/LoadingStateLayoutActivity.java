package com.gy.app;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_bean.Response;
import com.example.ltbase.base_callback.OnLoadingLayoutRetryListener;
import com.example.ltbase.base_http.HttpUrl;
import com.example.ltbase.base_utils.RxViewUtils;
import com.example.ltbase.base_utils.ToastUtils;
import com.example.ltbase.base_widget.layout.LoadingLayout;
import com.gy.app.bean.AppVersionBean;
import com.google.gson.Gson;
import com.gy.app.http.HttpService;
import com.gy.app.http.LoadingObserver;

import io.reactivex.rxjava3.core.Observable;

/**
 * 作者：王健 on 2021/9/3
 * 邮箱：845040970@qq.com
 * 描述：多状态布局
 */
public class LoadingStateLayoutActivity extends BaseActivity {
    private Button btnLoading,btnEmpty,btnError,btnHttp,btnInternet,btnReset;
    private LoadingLayout loadingLayout;
    private LinearLayout SHOW_ALL;
    private TextView tvContent;
    private String url= HttpUrl.APP_VERSION;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading_state_layout;
    }

    @Override
    protected void initView() {
        btnLoading= F(R.id.btnLoading);
        btnEmpty= F(R.id.btnEmpty);
        btnError= F(R.id.btnError);
        btnHttp= F(R.id.btnHttp);
        btnInternet= F(R.id.btnInternet);
        btnReset= F(R.id.btnReset);
        SHOW_ALL= F(R.id.SHOW_ALL);
        tvContent= F(R.id.tvContent);
        loadingLayout=LoadingLayout.wrap(SHOW_ALL);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        RxViewUtils.showClick(btnLoading,this);
        RxViewUtils.showClick(btnEmpty,this);
        RxViewUtils.showClick(btnError,this);
        RxViewUtils.showClick(btnHttp,this);
        RxViewUtils.showClick(btnInternet,this);
        RxViewUtils.showClick(btnReset,this);
        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.showLoading();
                showGetHttp(url);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoading:
                loadingLayout.showLoading();
                showGetHttp(url);
                break;
            case R.id.btnEmpty:
                    loadingLayout.showLoading();
                    loadingLayout.showEmpty();
                break;
            case R.id.btnError:
                loadingLayout.showLoading();
                showGetHttp("");
                break;
            case R.id.btnHttp:
                showGetHttpLoadingLayout(url);
                break;
            case R.id.btnInternet:

                break;
            case R.id.btnReset:
                loadingLayout.showContent();

                break;
            default:
        }
    }
    private void showGetHttp(String url) {
        Observable.just(1)
                .flatMap(integer -> new HttpService<AppVersionBean>().get(url,AppVersionBean.class))
                .subscribe(new LoadingObserver<Response<AppVersionBean>>((Activity) context,false){
                    @Override
                    public void onNext(@NonNull Response<AppVersionBean> appVersionBeanResponse) {
                        super.onNext(appVersionBeanResponse);
                        tvContent.setText(new Gson().toJson(appVersionBeanResponse));
                        loadingLayout.showContent();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        try {
                            loadingLayout.setErrorText(ErrorInfo(e, (Activity) context,false));
                            loadingLayout.showError();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
    }
    private void showGetHttpLoadingLayout(String url) {
        Observable.just(1)
                .flatMap(integer -> new HttpService<AppVersionBean>().get(url,AppVersionBean.class))
                .subscribe(new LoadingObserver<Response<AppVersionBean>>((Activity) context, false, loadingLayout, new OnLoadingLayoutRetryListener() {
                    @Override
                    public void onRetryListener() {
                        ToastUtils.showToast("点击重试");
                    }
                }){
                    @Override
                    public void onNext(@NonNull Response<AppVersionBean> appVersionBeanResponse) {
                        super.onNext(appVersionBeanResponse);
                        tvContent.setText(new Gson().toJson(appVersionBeanResponse));
                        loadingLayout.showContent();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        try {
                            loadingLayout.setErrorText(ErrorInfo(e, (Activity) context,false));
                            loadingLayout.showError();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
    }
}
