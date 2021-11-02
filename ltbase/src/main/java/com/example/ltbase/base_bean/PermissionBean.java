package com.example.ltbase.base_bean;

/**
 * 作者：王健 on 2021/9/2
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class PermissionBean {
    private String name;//权限名称
    private boolean granted;//权限是否授权
    private boolean shouldShowRequestPermissionRationale;//权限是否被选择拒绝且不在询问

    public PermissionBean(String name, boolean granted,boolean shouldShowRequestPermissionRationale) {
        this.name=name;
        this.granted=granted;
        this.shouldShowRequestPermissionRationale=shouldShowRequestPermissionRationale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }
    public boolean isShouldShowRequestPermissionRationale() {
        return shouldShowRequestPermissionRationale;
    }

    public void setShouldShowRequestPermissionRationale(boolean shouldShowRequestPermissionRationale) {
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }
}
