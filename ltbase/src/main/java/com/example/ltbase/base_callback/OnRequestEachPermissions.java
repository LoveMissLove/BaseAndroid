package com.example.ltbase.base_callback;

import com.example.ltbase.base_bean.PermissionBean;

import java.util.List;

/**
 * 作者：王健 on 2021/8/25
 * 邮箱：845040970@qq.com
 * 描述：权限结果回调
 */
public interface OnRequestEachPermissions {
    /**
     * 描述：权限被拒绝并且勾选了不在询问
     * @param deniedPermission 权限列表
     */
    void onShouldShowRationale(List<PermissionBean> deniedPermission);
    /**
     * 描述：有权限被拒绝
     * @param deniedPermission 权限列表
     */
    
    void onDenied(List<PermissionBean>deniedPermission);
    /**
     * 描述：全部授权
     */
    
    void onGranted();
}
