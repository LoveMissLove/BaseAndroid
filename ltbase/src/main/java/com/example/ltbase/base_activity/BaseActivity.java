package com.example.ltbase.base_activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ltbase.R;
import com.example.ltbase.base_callback.OnNetworkStateListener;
import com.example.ltbase.base_manager.ActivityPageManager;
import com.example.ltbase.base_utils.LogUtils;
import com.example.ltbase.base_utils.NetworkStateUtils;
import com.example.ltbase.base_utils.RxViewUtils;
import com.example.ltbase.base_utils.TransfereeImageUtils;
import com.example.ltbase.base_view.LoadingLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;

import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.param.RxHttp;


/**
 * 作者：王健 on 2018/9/29
 * 邮箱：845040970@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity implements OnNetworkStateListener {
    protected Activity context;
    private InputMethodManager imm;
    private NetworkStateUtils networkStateUtils;
    private LoadingLayout loadingLayout;
    protected boolean hasFocusDialog;
    protected Toolbar mToolbar;
    protected Transferee transferee;
    protected TransferConfig config;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
        //将Activity添加到队列方便管理
        ActivityPageManager.getInstance().addActivity(this);
        // 注册网络监听
        if(null==networkStateUtils){
            networkStateUtils=new NetworkStateUtils(this);
            networkStateUtils.registerNetworkCallback(this);
            networkStateUtils.setOnNetworkStateListener(this);
        }
        transferee = Transferee.getDefault(this);
        setContentView(setLayoutId());
        //初始化沉浸式
        initToolbars();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        //状态栏字体颜色
        initImmersionBarDarkFont(isImmersionBarDarkFountEnabled());
        //初始化view
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
    }
    public <T extends android.view.View> T F(@IdRes int id){
        return findViewById(id);
    }
    protected abstract int setLayoutId();

    protected void initData() {
    }

    protected void initView() {
    }

    protected void setListener() {
        showRetryListener();
    }
    protected void initToolbars() {
        View view = findViewById(R.id.toolbar);
        if (view != null) {
            mToolbar = (Toolbar) view;
            mToolbar.setTitle("");
            mToolbar.setSubtitle("");
            mToolbar.setLogo(null);
            //去除内间距
            mToolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(mToolbar);
        }
    }
    /**
     * 描述：配合网络监听使用多状态布局
     * @param loadingLayout 多状态布局
     */
    public void setLoadingLayout(LoadingLayout loadingLayout){
        this.loadingLayout=loadingLayout;
    }
    @Override
    protected void onResume() {
        super.onResume();
//        //用来判断是否被强制挤下线

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;
        transferee.destroy();
        transferee=null;
        //解除网络监听注册
        if(null!=networkStateUtils){
            networkStateUtils.unregisterNetworkCallback(this);
            networkStateUtils=null;
        }
        //关闭所有网络请求
        RxHttpPlugins.cancelAll();
//        //移除activity
        ActivityPageManager.getInstance().removeActivity(this);
    }


    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }
    /**
     * 是否改变状态栏字体颜色
     */
    protected boolean isImmersionBarDarkFountEnabled() {
        return true;
    }

    @Override
    public void onNetWorkConnect() {
        showNetWorkConnect();
    }

    @Override
    public void onNetWorkDisconnect() {
        showNetWorkDisconnect();
    }

    /**
    * 描述：网络断开
    */

    protected void showNetWorkDisconnect() {
        this.runOnUiThread(this::showErrorLayout);

    }
    /**
     * 描述：网络可用
     */
    protected void showNetWorkConnect() {
        this.runOnUiThread(this::showContentLayout);
    }
    /**
     * 描述：开始显示加载中布局
     */
    private void showLoadingLayout(){
        if (null!=loadingLayout){
            loadingLayout.showLoading();
        }
    }
    /**
     * 描述：开始显示原布局
     */
    private void showContentLayout(){
        if (null!=loadingLayout){
            loadingLayout.showContent();
        }
    }
    /**
     * 描述：开始显示错误布局
     */
    private void showErrorLayout(){
        if (null!=loadingLayout){
            loadingLayout.showError();
        }
    }
    /**
     * 描述：开始显示空数据布局
     */
    private void showEmptyLayout(){
        if (null!=loadingLayout){
            loadingLayout.showEmpty();
        }
    }
    /**
     * 描述：空数据布局与网络错误布局监听事件
     */
    private void showRetryListener(){
        if(null!=loadingLayout){
            loadingLayout.setRetryListener(v -> showRetryOrErrorListener());
        }
    }
    /**
     * 描述：空数据布局与网络错误布局监听事件回调所调界面
     */
    protected void showRetryOrErrorListener(){

    }
    protected void initImmersionBar() {
        //设置共同沉浸式样式在BaseActivity里初始化
        ImmersionBar.with(this).navigationBarColor(R.color.base_colorPrimary).titleBar(mToolbar).init();
    }
    /**
     * 设置状态栏字体颜色,true 深色，false，浅色
     * */
    protected void initImmersionBarDarkFont(boolean b) {
        if(b){
            if(isImmersionBarEnabled()){
                if (ImmersionBar.isSupportStatusBarDarkFont()) {
                    ImmersionBar.with(this).statusBarDarkFont(true,0.2f).init();
                } else {
                    Toast.makeText(this, "当前设备不支持状态栏字体变色", Toast.LENGTH_SHORT).show();
                }
            }else{
                if (ImmersionBar.isSupportStatusBarDarkFont()) {
                    ImmersionBar.with(this).statusBarDarkFont(true,0.2f).barEnable(false).init();
                } else {
                    Toast.makeText(this, "当前设备不支持状态栏字体变色", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }
    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }
    /**
     * 描述：当前窗体得到或失去焦点的时候的时候会自动调用onWindowFocusChanged,hasFocus=true窗口消失后的逻辑
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
            //hasFocus=true窗口消失后的逻辑
           hasFocusDialog=hasFocus;
    }
    /**
     * 设置 app 字体不随系统字体设置改变
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res != null) {
            Configuration config = res.getConfiguration();
            if (config != null && config.fontScale != 1.0f) {
                config.fontScale = 1.0f;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
        }
        return res;
    }
}