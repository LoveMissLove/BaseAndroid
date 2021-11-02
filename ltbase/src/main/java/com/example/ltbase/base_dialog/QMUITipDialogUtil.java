package com.example.ltbase.base_dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ltbase.R;
import com.example.ltbase.base_observer.ObserverQMUITipDialogTime;
import com.example.ltbase.base_utils.LogUtils;
import com.example.ltbase.base_view.QMIUI.tipdialog.QMUITipDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 作者：王健 on 2021/9/3
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class QMUITipDialogUtil {
    private  QMUITipDialog.Builder builder;
    private  QMUITipDialog.CustomBuilder customBuilder;
    private  QMUITipDialog dialog;
    private  Activity context;
    private String message="请稍后";
    private boolean cancel=false;//按系统返回键\点击屏幕是否可以取消
    public static final int LOADING=QMUITipDialog.Builder.ICON_TYPE_LOADING;
    public static final int SUCCESS=QMUITipDialog.Builder.ICON_TYPE_SUCCESS;
    public static final int FAIL=QMUITipDialog.Builder.ICON_TYPE_FAIL;
    public static final int INFO=QMUITipDialog.Builder.ICON_TYPE_INFO;
    private void initDialog(Activity mContext){
        builder=new QMUITipDialog.Builder(mContext);
        customBuilder=new QMUITipDialog.CustomBuilder(mContext);
        context=mContext;
    }
    /**
     * 描述：TipDialog
     * @param Type     dialog类型
     */
    public void showDialog(Activity context,int Type){
        initDialog(context);
        builder.setIconType(Type);
        builder.setTipWord(message);
        dialog=builder.create(cancel);
        show();
    }
    /**
     * 描述：TipDialog
     * @param Type     dialog类型
     * @param setMessage 显示信息
     */
    public void showDialog(Activity context,int Type,@Nullable String setMessage){
        initDialog(context);
        setMessage= TextUtils.equals("",setMessage) ?message:setMessage;
        builder.setIconType(Type);
        builder.setTipWord(setMessage);
        dialog=builder.create(cancel);
        show();
    }
    /**
     * 描述：TipDialog
     * @param Type     dialog类型
     * @param time     倒计时dialog消失时间
     */
    public void showDialog(Activity context,int Type,int time){
        initDialog(context);
        builder.setIconType(Type);
        builder.setTipWord(message);
        dialog=builder.create(cancel);
        show();
        showTimeDialog(time);
    }
    /**
     * 描述：TipDialog
     * @param Type     dialog类型
     * @param isCancel 按系统返回键\点击屏幕是否可以取消
     */
    public void showDialog(Activity context,int Type,boolean isCancel){
        initDialog(context);
        cancel=isCancel;
        builder.setIconType(Type);
        builder.setTipWord(message);
        dialog=builder.create(cancel);
        show();
    }
    /**
     * 描述：TipDialog
     * @param Type      dialog类型
     * @param setMessage 显示信息
     * @param isCancel 按系统返回键\点击屏幕是否可以取消
     */
    public void showDialog(Activity context,int Type,@Nullable String setMessage,boolean isCancel){
        initDialog(context);
        setMessage= TextUtils.equals("",setMessage) ?message:setMessage;
        cancel=isCancel;
        builder.setIconType(Type);
        builder.setTipWord(setMessage);
        dialog=builder.create(cancel);
        show();
    }
    /**
     * 描述：TipDialog
     * @param Type    dialog类型
     * @param setMessage 显示信息
     * @param time 倒计时dialog消失时间
     */
    public void showDialog(Activity context,int Type,@Nullable String setMessage,int time){
        initDialog(context);
        setMessage= TextUtils.equals("",setMessage) ?message:setMessage;
        builder.setIconType(Type);
        builder.setTipWord(setMessage);
        dialog=builder.create(cancel);
        show();
        showTimeDialog(time);
    }

    /**
     * 描述：TipDialog
     * @param Type    dialog类型
     * @param setMessage 显示信息
     * @param isCancel 按系统返回键\点击屏幕是否可以取消
     * @param time 倒计时dialog消失时间
     */
    public void showDialog(Activity context,int Type,@Nullable String setMessage,boolean isCancel,int time){
        initDialog(context);
        setMessage= TextUtils.equals("",setMessage) ?message:setMessage;
        cancel=isCancel;
        builder.setIconType(Type);
        builder.setTipWord(setMessage);
        dialog=builder.create(cancel);
        show();
        showTimeDialog(time);
    }
    public  QMUITipDialog showCustom(int layout){
        initDialog(context);
        customBuilder.setContent(layout);
        dialog=customBuilder.create();
        return dialog;
    }
    /**
     * 描述：设置显示内容
     * @param message 显示内容
     */
    public void setMessage(String message){
        this.message=message;
    }
    /**
     * 描述：执行延时消失
     * @param time 时间毫秒
     */
    public void showTimeDialog(int time) {
        Observable.timer(time,TimeUnit.MILLISECONDS).subscribe(new ObserverQMUITipDialogTime<Long>(){
            @Override
            public void onNext(@NonNull Long aLong) {
                super.onNext(aLong);
                if (null != dialog&&null!=context) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
    }
    public QMUITipDialog getDialog() {
        return dialog;
    }

    public void show(){
        if(null!=dialog&&null!=context){
            if(!context.isFinishing()||!context.isDestroyed()){
                dialog.show();
            }
        }
    }
    public void dismissDialog(){
        if(null!=dialog){
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
