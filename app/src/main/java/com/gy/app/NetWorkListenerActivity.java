package com.gy.app;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.example.ltbase.base_activity.BaseActivity;
import com.example.ltbase.base_bean.Response;
import com.example.ltbase.base_dialog.AlertDialogUtil;
import com.example.ltbase.base_http.HttpUrl;
import com.example.ltbase.base_http.LoadingObserver;
import com.example.ltbase.base_utils.ToastUtils;
import com.gy.app.bean.AppVersionBean;

import io.reactivex.rxjava3.core.Observable;

/**
 * 作者：王健 on 2021/9/3
 * 邮箱：845040970@qq.com
 * 描述：网络监听
 */
public class NetWorkListenerActivity extends BaseActivity {
    private AlertDialogUtil alertDialogUtil;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_network_listener;
    }

    @Override
    protected void initView() {
        super.initView();
        alertDialogUtil=new AlertDialogUtil();
        showAlertDialog();
    }

    @Override
    protected void showNetWorkConnect() {
        super.showNetWorkConnect();
        ToastUtils.showToast("网络连接了");
    }

    @Override
    protected void showNetWorkDisconnect() {
        super.showNetWorkDisconnect();
        ToastUtils.showToast("网络断开了");
        alertDialogUtil.show();

    }

    private void showAlertDialog() {
        alertDialogUtil.showAlertDialog(context,"网络状态提示","当前没有可以使用的网络，请设置网络！", "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 跳转到设置界面
            }
        });
    }
    private void showGetHttp() {
        Observable.just(1)
                .flatMap(integer -> new HttpService<AppVersionBean>().get(HttpUrl.APP_VERSION,AppVersionBean.class))
                .subscribe(new LoadingObserver<Response<AppVersionBean>>((Activity) context,!hasFocusDialog){
                    @Override
                    public void onNext(@NonNull Response<AppVersionBean> appVersionBeanResponse) {
                        super.onNext(appVersionBeanResponse);
//                        ToastUtils.showToast(new Gson().toJson(appVersionBeanResponse));
                    }
                });
    }
}
