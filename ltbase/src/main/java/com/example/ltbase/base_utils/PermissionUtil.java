package com.example.ltbase.base_utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.ltbase.base_bean.PermissionBean;
import com.example.ltbase.base_callback.OnRequestEachPermissions;
import com.example.ltbase.base_callback.OnRequestPermissions;
import com.example.ltbase.base_observer.ObserverPermissions;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 作者：王健 on 2018/8/28
 * 邮箱：845040970@qq.com
 * 权限申请工具类
 */
public class PermissionUtil {
    /**
     * 判断是否拥有该权限
     * */
    public static boolean showIsPermissionsGranted(Activity activity,String permissionsGroup){
        return !lacksPermission(activity, permissionsGroup);
    }
    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
    /**
     *  permissions 要请求的权限，可以是多个
     *  request例子:
     * 不支持返回权限名;
     * 返回的权限结果:全部同意时值true,否则值为false
     */
    public void  showMoreRequestPermissions(Activity mActivity, OnRequestPermissions onRequestPermissions,String... permissionsGroup) {
        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) mActivity);
        Disposable disposable=  rxPermissions.request(permissionsGroup).subscribe(aBoolean -> {
                if(null!=onRequestPermissions){
                    onRequestPermissions.result(aBoolean);
                }
        });
        disposable.dispose();
    }
    /**
     *  permissions 要请求的权限，可以是多个
     *  requestEach例子:
     * 把每一个权限的名称和申请结果都列出来
     */
    public void  showMoreRequestEachPermissions(Activity mActivity, OnRequestEachPermissions onRequestEachPermissions, String... permissionsGroup) {
        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) mActivity);
        List<PermissionBean>mList=new ArrayList<>();
        List<PermissionBean>mDeniedList=new ArrayList<>();
        List<PermissionBean>mShouldShowRationaleList=new ArrayList<>();
        rxPermissions.requestEach(permissionsGroup).subscribe(new ObserverPermissions<Permission>(){
            @Override
            public void onNext(@NonNull Permission permission) {
                super.onNext(permission);
                LogUtils.i("权限名称:"+permission.name+",申请结果:"+permission.granted+"");
                mList.add(new PermissionBean(permission.name,permission.granted,permission.shouldShowRequestPermissionRationale));
            }

            @Override
            public void onComplete() {
                super.onComplete();
                if(mList.size()>0){
                    for (int i = 0; i <mList.size() ; i++) {
                        if(!mList.get(i).isGranted()){
                            mDeniedList.add(mList.get(i));
                        }
                    }
                    if(mDeniedList.isEmpty()){
                        if(null!=onRequestEachPermissions){
                            onRequestEachPermissions.onGranted();
                        }
                    }else{
                        for (PermissionBean deniedPermission : mDeniedList) {
                            //勾选了对话框中”Don’t ask again”的选项, 返回false
                            boolean flag = deniedPermission.isShouldShowRequestPermissionRationale();
                            if (!flag) {
                                //拒绝授权
                                mShouldShowRationaleList.add(deniedPermission);
                            }
                        }
                        if(mShouldShowRationaleList.isEmpty()){
                            //拒绝授权
                            if(null!=onRequestEachPermissions){
                                onRequestEachPermissions.onDenied(mDeniedList);
                            }
                        }else{
                            //拒绝授权（勾选了不在询问）
                            if(null!=onRequestEachPermissions){
                                onRequestEachPermissions.onShouldShowRationale(mDeniedList);
                            }
                        }

                    }
                }
            }
        });
    }


}
