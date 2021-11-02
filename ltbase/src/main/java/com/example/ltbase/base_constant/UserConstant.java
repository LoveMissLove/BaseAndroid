package com.example.ltbase.base_constant;

import android.os.Environment;

/**
 * 常量类
 * @author dzb
 */
public class UserConstant {
    /**
     * app名称标签
     */
    public static final String APP_NAME_TAG ="Water";
    /**
     * key
     */
    public static final String KEY = "KEY";
    /**
     * spkey
     */
    public static final String SP_KEY = APP_NAME_TAG;
    /**
     * sessionKey 会话标识
     */
    public static final String SP_SESSION_KEY = "sessionKey";
    /**
     * sessionKey 会话标识
     */
    public static final String SP_LIST = "login_list";
    /**
     * 是否第一次使用
     */
    public static final String SP_IS_FIRST = "isFirst";
    /**
     * 是否刷新用户信息
     */
    public static final String SP_IS_REFRESH_USER_INFO = "isRefreshUserInfo";
    /**
     * 是否是登录状态
     */
    public static final String SP_LOGIN = "isLogin";
    /**
     * 是否是夜间模式
     * */
    public static final String SP_ISNIGHT="isNight";
    /**
     * 登录名
     */
    public static final String SP_USER_NAME = "userName";
    /**
     * 登录密码
     */
    public static final String SP_USER_NAME_PASSWORD = "userPassWord";
    /**
     * 是否记住密码账号
     * */
    public static final String SP_USER_NAME_IS_NAME_PASSWORD="isUserNameAndUserPassword";
    /**
     * token 用户标识
     */
    public static final String SP_TOKEN_KEY = "tokenKey";
    /**
     * SharedPreferences 保存用户信息的key
     */
    public static final String SP_USER_KEY = "UserInfo";

    /**
     * 微信APPID
     */
    public static final String WEI_XIN_APP_ID = "wxfe875a1a54dec286";
    /**
     * 微信 AppSecret
     */
    static final String WEI_XIN_APP_SECRET = "9772f31b059e0dfd755df5127e410629";
    //微信授权结果
    /**
     * 同意授权
     */
    public static final int WX_AUTH_OK = 0;
    /**
     * 拒绝授权
     */
    public static final int WX_AUTH_DENIED = -4;
    /**
     * 取消授权
     */
    public static final int WX_AUTH_CANCEL = -2;
    /**
     * 授权失败
     */
    public static final int WX_AUTH_FAIL = -1;
    /**
     * 读取授权结果码
     */
    public static final String SP_WX_AUTH_CODE = "WX_AUTH_RESULT";
    /**
     * 读取授权openId
     */
    public static final String SP_WX_AUTH_OPEN_ID = "WX_AUTH_OPEN_ID";
    /**
     * 读取授权微信昵称
     */
    public static final String SP_WX_AUTH_NAME = "WX_AUTH_NAME";
    /**
     * 读取授权微信头像地址
     */
    public static final String SP_WX_AUTH_HEAD_URL = "WX_AUTH_HEAD_URL";
    /**
     * SD卡的文件路径
     */
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    /**
     * 下载目录
     */
    public static final String DOWNLOAD_PATH = SDCARD_ROOT + "/" + APP_NAME_TAG + "/download/";
    /**
     * 临时文件缓存目录
     */
    public static final String CACHE_PATH = SDCARD_ROOT + "/" + APP_NAME_TAG + "/cache/";
    /**
     * 图片存放目录
     */
    public static final String IMG_PATH = SDCARD_ROOT + "/" + APP_NAME_TAG + "/img/";
}