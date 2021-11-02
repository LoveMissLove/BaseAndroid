package com.example.ltbase.base_callback;

import com.example.ltbase.base_bean.ErrorInfo;

import io.reactivex.rxjava3.functions.Consumer;


/**
 * 作者：王健 on 2021/8/4
 * 邮箱：845040970@qq.com
 * 描述：错误回调 ,加入网络异常处理
 */

public interface OnHttpError extends Consumer<Throwable> {

    @Override
    default void accept(Throwable throwable) throws Exception {
        onError(new ErrorInfo(throwable));
    }

    void onError(ErrorInfo error) throws Exception;
}
