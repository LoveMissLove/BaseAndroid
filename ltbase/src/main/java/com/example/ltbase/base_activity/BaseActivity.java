package com.example.ltbase.base_activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ltbase.R;
import com.example.ltbase.base_callback.OnNetworkStateListener;
import com.example.ltbase.base_dialog.action.ActivityAction;
import com.example.ltbase.base_dialog.action.BundleAction;
import com.example.ltbase.base_dialog.action.ClickAction;
import com.example.ltbase.base_dialog.action.HandlerAction;
import com.example.ltbase.base_dialog.action.KeyboardAction;
import com.example.ltbase.base_utils.NetworkStateUtils;
import com.example.ltbase.base_view.LoadingLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;

import rxhttp.RxHttpPlugins;


/**
 * 作者：王健 on 2018/9/29
 * 邮箱：845040970@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity implements OnNetworkStateListener, ActivityAction,ClickAction, KeyboardAction, BundleAction, HandlerAction {
    protected Activity context;
    private NetworkStateUtils networkStateUtils;
    private LoadingLayout loadingLayout;
    protected Toolbar mToolbar;
    protected Transferee transferee;
    protected TransferConfig config;
    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // 注册网络监听
        if (null == networkStateUtils) {
            networkStateUtils = new NetworkStateUtils(this);
            networkStateUtils.registerNetworkCallback(this);
            networkStateUtils.setOnNetworkStateListener(this);
        }
        transferee = Transferee.getDefault(context);
        initActivity();
    }

    protected void initActivity() {
        initLayout();
        initView();
        initData();
        setListener();
    }

    /**
     * 获取布局 ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化布局
     */
    protected void initLayout() {
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            initSoftKeyboard();
            initToolbars();
            // 初始化沉浸式状态栏
            if (isStatusBarEnabled()) {
                getStatusBarConfig().init();

                // 设置标题栏沉浸
                ImmersionBar.setTitleBar(this, mToolbar);
            }
        }
    }

    /**
     * 初始化软键盘
     */
    protected void initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(v -> {
            // 隐藏软键，避免内存泄漏
            hideKeyboard(getCurrentFocus());
        });
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

    /**
     * 简化findViewById
     */
    public <T extends android.view.View> T F(@IdRes int id) {
        return findViewById(id);
    }


    protected void setListener() {
        showRetryListener();
    }


    /**
     * 初始化toolbar
     */
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
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont())
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.base_white)
                // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
                .autoDarkModeEnable(true, 0.2f);
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体深色模式
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
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
     * 描述：配合网络监听使用多状态布局
     *
     * @param loadingLayout 多状态布局
     */
    public void setLoadingLayout(LoadingLayout loadingLayout) {
        this.loadingLayout = loadingLayout;
    }

    /**
     * 描述：开始显示加载中布局
     */
    private void showLoadingLayout() {
        if (null != loadingLayout) {
            loadingLayout.showLoading();
        }
    }

    /**
     * 描述：开始显示原布局
     */
    private void showContentLayout() {
        if (null != loadingLayout) {
            loadingLayout.showContent();
        }
    }

    /**
     * 描述：开始显示错误布局
     */
    private void showErrorLayout() {
        if (null != loadingLayout) {
            loadingLayout.showError();
        }
    }

    /**
     * 描述：开始显示空数据布局
     */
    private void showEmptyLayout() {
        if (null != loadingLayout) {
            loadingLayout.showEmpty();
        }
    }

    /**
     * 描述：空数据布局与网络错误布局监听事件
     */
    private void showRetryListener() {
        if (null != loadingLayout) {
            loadingLayout.setRetryListener(v -> showRetryOrErrorListener());
        }
    }

    /**
     * 描述：空数据布局与网络错误布局监听事件回调所调界面
     */
    protected void showRetryOrErrorListener() {

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

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent);
    }

    @Override
    public void finish() {
        super.finish();
        // 隐藏软键，避免内存泄漏
        hideKeyboard(getCurrentFocus());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        //用来判断是否被强制挤下线

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transferee.destroy();
        transferee = null;
        //解除网络监听注册
        if (null != networkStateUtils) {
            networkStateUtils.unregisterNetworkCallback(this);
            networkStateUtils = null;
        }
        //关闭所有网络请求
        RxHttpPlugins.cancelAll();
        //移除全部消息列表
        removeCallbacks();
    }
    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public Activity getContext() {
        return context;
    }
}