package com.example.ltbase.base_utils;

import com.google.gson.Gson;

/**
 * 作者：王健 on 2021/9/2
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class GsonUtil
{
    private static class GsonHolder{
        private static final Gson INSTANCE = new Gson();
    }

    /**
     * 获取Gson实例，由于Gson是线程安全的，这里共同使用同一个Gson实例
     */
    public static Gson getGson()
    {
        return GsonHolder.INSTANCE;
    }
}