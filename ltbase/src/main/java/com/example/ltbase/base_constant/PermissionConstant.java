package com.example.ltbase.base_constant;

import android.Manifest;

/**
 * 权限常量类
 * READ_EXTERNAL_STORAGE 读的权限
 * WRITE_EXTERNAL_STORAGE 写入的权限
 * CAMERA 拍照的权限
 * CALL_PHONE 打电话的权限
 * ACCESS_FINE_LOCATION 定位权限
 * READ_SMS 获取短信的权限
 */
public class PermissionConstant {
    /**
     * READ_EXTERNAL_STORAGE 读的权限
     */
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    /**
     * WRITE_EXTERNAL_STORAGE 写入的权限
     */
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    /**
     * CALL_PHONE 打电话的权限
     */

    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    /**
     * CAMERA 拍照的权限
     */
    public static final String CAMERA = Manifest.permission.CAMERA;
    /**
     * ACCESS_FINE_LOCATION 定位权限
     */
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    /**
     * READ_SMS 获取短信的权限
     */
    public static final String CODE_SMS = Manifest.permission.READ_SMS;

    public static final String PHONE_STATE=Manifest.permission.READ_PHONE_STATE;
    /**
     * 网络请求权限
     */
    public static final String INTERNET=Manifest.permission.INTERNET;
    /**
     * 安装包权限
     * */
    public static final String REQUEST_INSTALL_PACKAGES=Manifest.permission.REQUEST_INSTALL_PACKAGES;
    /**
     * 悬浮窗权限
     * */
    public static final String SYSTEM_ALERT_WINDOW=Manifest.permission.SYSTEM_ALERT_WINDOW;
    /**
     * 录音权限
     * */
    public static final String RECORD_AUDIO=Manifest.permission.RECORD_AUDIO;
}
