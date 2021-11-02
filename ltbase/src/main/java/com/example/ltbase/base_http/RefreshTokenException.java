package com.example.ltbase.base_http;


import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import rxhttp.wrapper.annotations.NonNull;
import rxhttp.wrapper.annotations.Nullable;

/**
 * 作者：王健 on 2021/8/23
 * 邮箱：845040970@qq.com
 * 描述：刷新token 或者重新登录界面
 */
public class RefreshTokenException extends IOException {
    private final String errorCode;

    private final String requestMethod; //请求方法，Get/Post等
    private final okhttp3.HttpUrl httpUrl; //请求Url及查询参数
    private final Headers responseHeaders; //响应头

    public RefreshTokenException(@NonNull String code, String message, Response response) {
        super(message);
        errorCode = code;

        Request request = response.request();
        requestMethod = request.method();
        httpUrl = request.url();
        responseHeaders = response.headers();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestUrl() {
        return httpUrl.toString();
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    @Nullable
    @Override
    public String getLocalizedMessage() {
        return errorCode;
    }

    @Override
    public String toString() {
        return getClass().getName() + ":" +
                "\n" + requestMethod + " " + httpUrl +
                "\n\nCode=" + errorCode + " message=" + getMessage() +
                "\n" + responseHeaders;
    }
}
