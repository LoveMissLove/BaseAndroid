package com.example.ltbase.base_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.Nullable;

import com.example.ltbase.R;

/**
 * 作者：王健 on 2021/9/2
 * 邮箱：845040970@qq.com
 * 描述：AlertDialog
 */
public class AlertDialogUtil {
    public  AlertDialog.Builder builder;
    public  Dialog dialog;
    private  Activity context;
    private void initDialog(Activity activity){
        builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog_Alert);
        context=activity;
    }
    public void showAlertDialog(Activity activity,@Nullable String setMessage, String setNegativeButton, String setPositiveButton, DialogInterface.OnClickListener DialogInterface1, DialogInterface.OnClickListener   DialogInterface2){
        initDialog(activity);
        builder.setMessage(setMessage);
        builder.setNegativeButton(setNegativeButton, DialogInterface1);
        builder.setPositiveButton(setPositiveButton,DialogInterface2 );
        dialog=builder.create();
    }
    public void showAlertDialog(Activity activity,@Nullable String setTitle,@Nullable String setMessage, String setNegativeButton, String setPositiveButton, DialogInterface.OnClickListener DialogInterface1, DialogInterface.OnClickListener   DialogInterface2){
        initDialog(activity);
        builder.setTitle(setTitle);
        builder.setMessage(setMessage);
        builder.setNegativeButton(setNegativeButton, DialogInterface1);
        builder.setPositiveButton(setPositiveButton,DialogInterface2 );
        dialog=builder.create();
    }
    public void show(){
        if(null!=dialog&&null!=context){
            if(!context.isFinishing()||!context.isDestroyed()){
                dialog.show();
            }
        }
    }
    public Dialog getDialog(){
        return dialog;
    }
}
