package com.example.ltbase.base_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.ltbase.R;
import com.example.ltbase.base_utils.RxViewUtils;


public class InfoDialog {

    private TextView mTvConfirm;
    private Dialog mDialog;
    private TextView mTitle;
    private TextView mTvCancel;
    private Activity mContext;
    private View mViewLine;
    private TextView mChildTitle;
    private boolean isCancelable=false;//点击手机返回按键是否允许对话框消失
    private boolean isCanceledOnTouchOutside=true;//点击对话框外部区域是否允许对话框消失
    public InfoDialog(Activity context) {
        this.mContext = context;
        initDialog();
    }

    public void showDialog(String title, String cancel, String textConfirm) {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
        mDialog.show();
        mChildTitle.setVisibility(View.GONE);
        mTitle.setText(title);
        mTvCancel.setText(cancel);
        mTvConfirm.setText(textConfirm);

    }
    public void showDialog(String title,String childTitle ,String cancel, String textConfirm) {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
        mDialog.show();
        mTitle.setText(title);
        mChildTitle.setText(childTitle);
        mTvCancel.setText(cancel);
        mTvConfirm.setText(textConfirm);
        if(TextUtils.isEmpty(cancel)){
            mTvCancel.setVisibility(View.GONE);
            mViewLine.setVisibility(View.GONE);
        }else{
            mTvCancel.setVisibility(View.VISIBLE);
            mViewLine.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(textConfirm)){
            mTvConfirm.setVisibility(View.GONE);
        }else{
            mTvConfirm.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(childTitle)){
            mChildTitle.setVisibility(View.GONE);
        }else{
            mChildTitle.setVisibility(View.VISIBLE);
        }
    }

    private void initDialog() {
        View view = View.inflate(mContext, R.layout.dialog_new_two_btn, null);
        mTvConfirm = view.findViewById(R.id.tv_confirm);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTitle = view.findViewById(R.id.tv_title);
        mChildTitle=view.findViewById(R.id.tv_child_title);
        mViewLine = view.findViewById(R.id.tv_view_line);
        mDialog = new Dialog(mContext, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        mDialog.setCancelable(isCancelable);
        RxViewUtils.showClick(mTvCancel, view12 -> {
            mDialog.dismiss();
            if (mCallBack != null) {
                mCallBack.cancel();
            }
        });
        RxViewUtils.showClick(mTvConfirm, view1 -> {
            mDialog.dismiss();
            if (mCallBack != null) {
                mCallBack.confirm();
            }
        });
    }

    public interface ClickConfirmCallBack {
        void confirm();

        void cancel();

    }

    private ClickConfirmCallBack mCallBack;

    public void setClickConfirmCallBack(ClickConfirmCallBack callback) {
        mCallBack = callback;
    }
    public void setCancelable(boolean isCancelable){
        this.isCancelable=isCancelable;
        mDialog.setCancelable(isCancelable);
    }
    public void setCanceledOnTouchOutside(boolean isCanceledOnTouchOutside){
        this.isCanceledOnTouchOutside=isCanceledOnTouchOutside;
        mDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
    }
    public void disDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
