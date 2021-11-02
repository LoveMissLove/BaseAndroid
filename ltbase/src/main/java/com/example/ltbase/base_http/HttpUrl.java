package com.example.ltbase.base_http;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * 作者：王健 on 2021/8/4
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class HttpUrl {
    /**
     * 在这里修改全局的baseUrl，域名
     * */
    @DefaultDomain() //设置为默认域名
    public static String baseUrl = "https://pay.jzqyyw.com/";

    public static String APP_VERSION="version/android";
}
