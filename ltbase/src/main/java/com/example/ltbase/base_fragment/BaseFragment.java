package com.example.ltbase.base_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.ltbase.R;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;


public abstract class BaseFragment extends SimpleImmersionFragment {
    protected Activity mActivity;
    protected View mRootView;
    protected Toolbar toolbar;
    protected View statusBarView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBeforeView(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusBarView = view.findViewById(R.id.status_bar_view);
        toolbar = view.findViewById(R.id.toolbar);
        fitsLayoutOverlap();
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        //状态栏字体颜色
        initImmersionBarDarkFont(isImmersionBarDarkFountEnabled());
        initData();
        initView();
        setListener();
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //旋转屏幕为什么要重新设置布局与状态栏重叠呢？因为旋转屏幕有可能使状态栏高度不一样，如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap();
    }

    protected void initDataBeforeView(Bundle savedInstanceState) {

    }

    protected abstract int getLayoutId();


    @Override
    public void initImmersionBar() {
        //设置共同沉浸式样式在BaseActivity里初始化
        ImmersionBar.with(this).navigationBarColor(R.color.base_colorPrimary).init();
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
                    Toast.makeText(mActivity, "当前设备不支持状态栏字体变色", Toast.LENGTH_SHORT).show();
                }
            }else{
                if (ImmersionBar.isSupportStatusBarDarkFont()) {
                    ImmersionBar.with(this).statusBarDarkFont(true,0.2f).barEnable(false).init();
                } else {
                    Toast.makeText(mActivity, "当前设备不支持状态栏字体变色", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    private void fitsLayoutOverlap() {
        if (statusBarView != null) {
            ImmersionBar.setStatusBarView(this, statusBarView);
        } else {
            ImmersionBar.setTitleBar(this, toolbar);
        }
    }

}
