package com.example.ltbase.base_dialog;

import android.content.Context;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

/**
 * 作者：王健 on 2021/10/25
 * 邮箱：845040970@qq.com
 * 描述：dialog提示框
 */
public class QMUIDialogUtil {
    private Context mContext;
    public QMUIDialogUtil(Context context){
        mContext=context;
    }
    public void showDialogMessage(String title,String message,String btnLeftName,String btnRightName,QMUIDialogAction.ActionListener listenerLeft,QMUIDialogAction.ActionListener listenerRight){
        new com.qmuiteam.qmui.widget.dialog.QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setSkinManager(QMUISkinManager.defaultInstance(mContext))
                .addAction(btnLeftName, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(com.qmuiteam.qmui.widget.dialog.QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        listenerLeft.onClick(dialog,index);
                    }
                })
                .addAction(0, btnRightName, QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(com.qmuiteam.qmui.widget.dialog.QMUIDialog dialog, int index) {
                        listenerRight.onClick(dialog,index);
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }
}
