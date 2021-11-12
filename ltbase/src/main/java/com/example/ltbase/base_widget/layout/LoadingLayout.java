package com.example.ltbase.base_widget.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;

import com.example.ltbase.R;
import com.example.ltbase.base_application.BaseApplication;

import java.util.HashMap;
import java.util.Map;


/**
 * 多状态布局
 */
public class LoadingLayout extends FrameLayout {

    /**
     * Inflater
     */
    private LayoutInflater mInflater;
    /**
     * 原布局
     */
    private int mContentId;
    /**
     * 加载中布局
     */
    private int mLoadingResId;
    /**
     * 无数据布局
     */
    private int mEmptyResId;
    /**
     * 加载出错布局
     */
    private int mErrorResId;
    /**
     * 刷新重试监听
     */
    private OnClickListener mRetryListener;
    /**
     * 布局集合
     */
    private Map<Integer, View> mLayouts;
    /**
     * 当前加载布局是否显示中
     */
    private boolean isShowLoading;


    public static LoadingLayout wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static LoadingLayout wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }

    public static LoadingLayout wrap(View view) {
        if (view == null) {
            throw new RuntimeException("LoadingLayout.wrap(View view)    view不能为null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        LoadingLayout layout = new LoadingLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }

    public LoadingLayout(Context context) {
        this(context, null, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化参数
     */
    @SuppressLint("UseSparseArrays")
    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mEmptyResId = R.layout._loading_layout_empty;
        mLoadingResId = R.layout._loading_layout_loading;
        mErrorResId = R.layout._loading_layout_error;
        mLayouts = new HashMap<>();
    }

    /**
     * 显示加载数据布局
     */
    public void showLoading() {
        show(mLoadingResId, true);
    }

    /**
     * 显示无数据布局
     */
    public void showEmpty() {
        show(mEmptyResId, true);
    }

    /**
     * 显示网络出错布局
     */
    public void showError() {
        show(mErrorResId, true);
    }

    /**
     * 显示原布局内容
     */
    public void showContent() {
        show(mContentId, true);
    }

    /**
     * 显示原布局内容
     */
    public void showContent(boolean isShow) {
        show(mContentId, isShow);
    }

    private void show(int layoutId, boolean isShow) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(isShow ? VISIBLE : GONE);
        //记录当前是否显示加载中布局
        isShowLoading = (isShow && layoutId == mLoadingResId);
    }

    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        return addLayout(layoutId);
    }

    private View addLayout(int layoutId) {
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);
        //加载出错布局
        if (layoutId == mErrorResId) {
            //重试监听
            OnClickListener listener = v -> {
                if (mRetryListener != null) {
                    mRetryListener.onClick(v);
                }
            };
            //图标
            View iv = layout.findViewById(R.id.error_image);
            if (iv != null) {
                iv.setOnClickListener(listener);
            }
            //文字
            View tv = layout.findViewById(R.id.error_text);
            if (tv != null) {
                tv.setOnClickListener(listener);
            }
            //刷新重试按钮
            View btn=layout.findViewById(R.id.retry_button);
            if(btn!=null){
                btn.setOnClickListener(listener);
            }
        }
        //无数据布局
        if (layoutId == mEmptyResId) {
            //重试监听
            OnClickListener listener = v -> {
                if (mRetryListener != null) {
                    mRetryListener.onClick(v);
                }
            };
            //图标
            View iv = layout.findViewById(R.id.empty_image);
            if (iv != null) {
                iv.setOnClickListener(listener);
            }
            //文字
            View tv = layout.findViewById(R.id.empty_text);
            if (tv != null) {
                tv.setOnClickListener(listener);
            }
        }
        return layout;
    }

    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }

    /**
     * 重新设置加载中布局
     */
    public LoadingLayout setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }

    /**
     * 重新设置无数据布局
     */
    public LoadingLayout setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }

    /**
     * 设置无数据布局页面图片资源
     */
    public LoadingLayout setEmptyImage(@DrawableRes int resId) {
        image(mEmptyResId, R.id.empty_image, resId);
        return this;
    }

    /**
     * 获取无数据布局页面图片控件
     */
    public ImageView getEmptyImageView() {
        return getImageView(mEmptyResId, R.id.empty_image);
    }

    /**
     * 设置无数据布局文字
     */
    public LoadingLayout setEmptyText(String value) {
        text(mEmptyResId, R.id.empty_text, value);
        return this;
    }

    /**
     * 设置无数据布局文字
     */
    public LoadingLayout setEmptyText(int resValue) {
        text(mEmptyResId, R.id.empty_text, BaseApplication.getInstance().getString(resValue));
        return this;
    }

    /**
     * 获取无数据布局文字控件
     */
    public TextView getEmptyTextView() {
        return getTextView(mEmptyResId, R.id.empty_text);
    }

    /**
     * 设置默认布局加载中显示文字
     */
    public LoadingLayout setLoadingText(String value) {
        text(mLoadingResId, R.id.loading_text, value);
        return this;
    }

    /**
     * 获取默认布局加载中显示文字控件
     */
    public TextView getLoadingTextView() {
        return getTextView(mLoadingResId, R.id.loading_text);
    }

    /**
     * 设置加载出错时显示图片
     */
    public LoadingLayout setErrorImage(@DrawableRes int resId) {
        image(mErrorResId, R.id.error_image, resId);
        return this;
    }

    /**
     * 获取加载出错时显示图片控件
     */
    public ImageView getErrorImageView() {
        return getImageView(mErrorResId, R.id.error_image);
    }

    /**
     * 设置加载出错时显示文字
     */
    public LoadingLayout setErrorText(String value) {
        text(mErrorResId, R.id.error_text, value);
        return this;
    }

    /**
     * 设置加载出错时显示文字
     */
    public LoadingLayout setErrorText(int resValue) {
        text(mErrorResId, R.id.error_text, BaseApplication.getInstance().getString(resValue));
        return this;
    }

    /**
     * 获取加载出错时显示文字控件
     */
    public TextView getErrorTextView() {
        return getTextView(mErrorResId, R.id.error_text);
    }

    public void setRetryListener(OnClickListener listener) {
        mRetryListener = listener;
    }

    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    private void text(int layoutId, int ctrlId, CharSequence value) {
        if (mLayouts.containsKey(layoutId)) {
            TextView view = mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setText(value);
            }
        } else {
            addLayout(layoutId);
            text(layoutId, ctrlId, value);
        }
    }

    private void image(int layoutId, int ctrlId, int resId) {
        if (mLayouts.containsKey(layoutId)) {
            ImageView view = mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setImageResource(resId);
            }
        } else {
            addLayout(layoutId);
            image(layoutId, ctrlId, resId);
        }
    }

    private TextView getTextView(int layoutId, int ctrlId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId).findViewById(ctrlId);
        } else {
            addLayout(layoutId);
            return getTextView(layoutId, ctrlId);
        }
    }

    private ImageView getImageView(int layoutId, int ctrlId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId).findViewById(ctrlId);
        } else {
            addLayout(layoutId);
            return getImageView(layoutId, ctrlId);
        }
    }

    public boolean isShowLoading() {
        return isShowLoading;
    }
}
