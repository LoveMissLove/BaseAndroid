package com.gy.app;

import static com.example.ltbase.base_dialog.QMUITipDialogUtil.FAIL;
import static com.example.ltbase.base_dialog.QMUITipDialogUtil.INFO;
import static com.example.ltbase.base_dialog.QMUITipDialogUtil.LOADING;
import static com.example.ltbase.base_dialog.QMUITipDialogUtil.SUCCESS;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_callback.OnRxViewClickListener;
import com.example.ltbase.base_dialog.QMUITipDialogUtil;
import com.example.ltbase.base_utils.RxViewUtils;
import com.example.ltbase.base_view.QMIUI.tipdialog.QMUITipDialog;

/**
 * 作者：王健 on 2021/9/3
 * 邮箱：845040970@qq.com
 * 描述：QMUITipDialogUtil 提示dialog
 */
public class LoadingTipDialogActivity extends BaseActivity implements OnRxViewClickListener {
    private Button btnLoading,btnSuccess,btnError,btnE,btnCancel,btnCustom;
    private QMUITipDialogUtil qmuiTipDialogUtil;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_loading_tip_dialog;
    }

    @Override
    protected void initView() {
        super.initView();
        qmuiTipDialogUtil=new QMUITipDialogUtil();
        btnLoading= F(R.id.btnLoading);
        btnSuccess= F(R.id.btnSuccess);
        btnError= F(R.id.btnError);
        btnE= F(R.id.btnE);
        btnCustom= F(R.id.btnCustom);
        btnCancel= F(R.id.btnCancel);
    }

    @Override
    protected void setListener() {
        super.setListener();
        RxViewUtils.showClick(btnLoading,this);
        RxViewUtils.showClick(btnSuccess,this);
        RxViewUtils.showClick(btnError,this);
        RxViewUtils.showClick(btnE,this);
        RxViewUtils.showClick(btnCancel,this);
        RxViewUtils.showClick(btnCustom,this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoading:
                qmuiTipDialogUtil.showDialog(context,LOADING,"加载中",true);
                if(null!=qmuiTipDialogUtil.getDialog()&&!qmuiTipDialogUtil.getDialog().isShowing()){
                    qmuiTipDialogUtil.showDialog(context,LOADING,"加载中",true);
                    qmuiTipDialogUtil.showDialog(context,LOADING,"加载中",true);
                    qmuiTipDialogUtil.showDialog(context,LOADING,"加载中",true);
                }
                break;
            case R.id.btnSuccess:
                qmuiTipDialogUtil.showDialog(context,SUCCESS,"加载成功",true,3000);
                break;
            case R.id.btnError:
                qmuiTipDialogUtil.showDialog(context,FAIL,"加载失败",true,3000);
                break;
            case R.id.btnE:
                qmuiTipDialogUtil.showDialog(context,INFO,"加载异常",true,3000);
                break;
            case R.id.btnCustom:
               QMUITipDialog dialog= qmuiTipDialogUtil.showCustom(R.layout.tipdialog_custom);
                ImageView img=dialog.findViewById(R.id.img);
                TextView textView=dialog.findViewById(R.id.tvMsg);
                img.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_img_error));
                textView.setText("我是自定义TipDialog");
                qmuiTipDialogUtil.show();
                qmuiTipDialogUtil.showTimeDialog(3000);
                break;
            case R.id.btnCancel:
                qmuiTipDialogUtil.dismissDialog();
                break;
            default:
        }
    }
}
