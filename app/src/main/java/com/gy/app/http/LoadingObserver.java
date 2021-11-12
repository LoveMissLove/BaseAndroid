package com.gy.app.http;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.example.ltbase.R;
import com.example.ltbase.base_application.BaseApplication;
import com.example.ltbase.base_callback.OnLoadingLayoutRetryListener;
import com.example.ltbase.base_dialog.QMUITipDialogUtil;
import com.example.ltbase.base_http.ExceptionHelper;
import com.example.ltbase.base_http.MyException;
import com.example.ltbase.base_http.RefreshTokenException;
import com.example.ltbase.base_utils.LogUtils;
import com.example.ltbase.base_utils.ToastUtils;
import com.example.ltbase.base_widget.layout.LoadingLayout;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

/**
 * 作者：王健 on 2021/8/5
 * 邮箱：845040970@qq.com
 * 描述：自定义Observer
 */
public class LoadingObserver<T>implements Observer<T>{
    private  QMUITipDialogUtil qmuiTipDialogUtil;
    private final Activity mActivity;
    private final boolean isLoadingDialog;
    private LoadingLayout loadingLayout;
    private OnLoadingLayoutRetryListener onLoadingLayoutRetryListener;
    public LoadingObserver(Activity mActivity, boolean isLoadingDialog){
        this.mActivity=mActivity;
        this.isLoadingDialog=isLoadingDialog;
        initDialog();
    }
    public LoadingObserver(Activity mActivity, boolean isLoadingDialog, LoadingLayout loadingLayout,OnLoadingLayoutRetryListener onLoadingLayoutRetryListener){
        this.mActivity=mActivity;
        this.isLoadingDialog=isLoadingDialog;
        this.onLoadingLayoutRetryListener=onLoadingLayoutRetryListener;
        this.loadingLayout=loadingLayout;
        initDialog();
        showRetryListener();
    }
    private void initDialog() {
        if(null==qmuiTipDialogUtil){
            qmuiTipDialogUtil=new QMUITipDialogUtil();
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        showStartLoadingDialog();
        showLoadingLayout();
    }

    @Override
    public void onNext(@NonNull T t) {
        showEndLoadingDialog();
        showContentLayout();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        showEndLoadingDialog();
        try {
            if(null!=loadingLayout){
                loadingLayout.setErrorText(ErrorInfo(e,mActivity,false));
            }
            showErrorLayout();
            ToastUtils.showToast(ErrorInfo(e,mActivity,true));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    @Override
    public void onComplete() {
        showEndLoadingDialog();
    }

    /**
     * 描述：开始加载dialog
     */
    private void showStartLoadingDialog(){
        if(isLoadingDialog){
            qmuiTipDialogUtil.showDialog(mActivity,QMUITipDialogUtil.LOADING,"请稍后", false);
        }
    }
    /**
     * 描述：结束加载dialog
     */
    private void showEndLoadingDialog(){
        if(isLoadingDialog){
            qmuiTipDialogUtil.dismissDialog();
        }
    }
    /**
     * 描述：开始显示加载中布局
     */
    protected void showLoadingLayout(){
        if (null!=loadingLayout){
            loadingLayout.showLoading();
        }
    }
    /**
     * 描述：开始显示原布局
     */
    protected void showContentLayout(){
        if (null!=loadingLayout){
            loadingLayout.showContent();
        }
    }
    /**
     * 描述：开始显示错误布局
     */
    protected void showErrorLayout(){
        if (null!=loadingLayout){
            loadingLayout.showError();
        }
    }
    /**
     * 描述：开始显示空数据布局
     */
    protected void showEmptyLayout(){
        if (null!=loadingLayout){
            loadingLayout.showEmpty();
        }
    }
    /**
     * 描述：空数据布局与网络错误布局监听事件
     */
    protected void showRetryListener(){
        if(null!=loadingLayout){
            loadingLayout.setRetryListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=onLoadingLayoutRetryListener){
                        onLoadingLayoutRetryListener.onRetryListener();
                    }
                }
            });
        }
    }
    /**
     * 描述：自定义异常过滤
     * @return 异常信息
     * @param throwable 异常信息
     * @param mActivity 上下文
     * @param value 是否拼接errorCode+errorMsg
     * @throws Exception 异常信息
     */
    
    public String ErrorInfo(Throwable throwable,Activity mActivity,boolean value) throws Exception {
        String errorMsg = null;
        String errorCode = null;
        if (throwable instanceof UnknownHostException) {
            LogUtils.e("=======UnknownHostException");
            if (!ExceptionHelper.isNetworkConnected(BaseApplication.getInstance())) {
                errorMsg = mActivity.getString(R.string.network_error);
            } else {
                errorMsg = mActivity.getString(R.string.notify_no_network);
            }
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
            LogUtils.e("=======SocketTimeoutException||TimeoutException");
            //前者是通过OkHttpClient设置的超时引发的异常，后者是对单个请求调用timeout方法引发的超时异常
            errorMsg = mActivity.getString(R.string.time_out_please_try_again_later);
        } else if (throwable instanceof ConnectException) {
            LogUtils.e("=======ConnectException");

            errorMsg = mActivity.getString(R.string.the_network_is_not_helpful_exception);
        } else if (throwable instanceof HttpStatusCodeException) { //请求失败异常
            LogUtils.e("=======HttpStatusCodeException");
            String code = throwable.getLocalizedMessage();
            errorCode=code;

            if ("416".equals(code)) {
                errorMsg = mActivity.getString(R.string.the_requested_scope_is_invalid);
            }else if("404".equals(code)){
                errorMsg = mActivity.getString(R.string.the_requested_address_is_abnormal);
            } else {
                errorMsg = throwable.getMessage();
            }
        } else if (throwable instanceof JsonSyntaxException) { //请求成功，但Json语法异常,导致解析失败
            LogUtils.e("=======JsonSyntaxException");
            errorMsg = "数据解析失败,请稍后再试";
        }else if(throwable instanceof RefreshTokenException){
            LogUtils.e("=======RefreshTokenException");
            //TODO 处理token失效业务逻辑
            errorCode=throwable.getLocalizedMessage();
            errorMsg="Token失效";
        } else if (throwable instanceof ParseException) { // ParseException异常表明请求成功，但是数据不正确，处理非正确code下服务器返回的错误信息
            LogUtils.e("=======ParseException");
            errorCode = throwable.getLocalizedMessage();
            errorMsg = throwable.getMessage();
            if (TextUtils.isEmpty(errorMsg)) {
                errorMsg = errorCode;//errorMsg为空，显示errorCode
            }
            throw new MyException(errorCode,errorMsg);
        } else {
            LogUtils.e("=======无状态异常");
            errorMsg = throwable.getMessage();
        }
        if(value){
            return "errorMsg:"+errorMsg+",errorCode:"+errorCode;
        }else{
            return errorMsg;
        }

    }
}