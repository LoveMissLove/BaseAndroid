package com.example.ltbase.base_dialog.action;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.example.ltbase.base_utils.RxViewUtils;

/**
 * @author: LT
 * @date: 2021/11/10 14:47
 * @desc: 点击事件意图
 */
public interface ClickAction extends View.OnClickListener {

    <V extends View> V findViewById(@IdRes int id);

    default void setOnClickListener(@IdRes int... ids) {
        setOnClickListener(this, ids);
    }

    default void setOnClickListener(@Nullable View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
//            findViewById(id).setOnClickListener(listener);
            RxViewUtils.showClick(findViewById(id),listener);
        }
    }

    default void setOnClickListener(View... views) {
       setOnClickListener(this, views);
    }

    default void setOnClickListener(@Nullable View.OnClickListener listener, View... views) {
        for (View view : views) {
//            view.setOnClickListener(listener);
            RxViewUtils.showClick(view,listener);
        }
    }

    @Override
    default void onClick(View view) {
        // 默认不实现，让子类实现
    }
}