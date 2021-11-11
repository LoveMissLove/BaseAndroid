package com.example.ltbase.base_manager;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.ltbase.base_bean.CustomResult;
import com.example.ltbase.base_callback.OnUpDateAppCallBack;
import com.example.ltbase.base_dialog.BaseDialog;
import com.example.ltbase.base_dialog.QMUITipDialogUtil;
import com.example.ltbase.base_dialog.UpDateAppMessageDialog;
import com.example.ltbase.base_utils.APKVersionCodeUtils;
import com.example.ltbase.base_utils.HProgressDialogUtils;
import com.example.ltbase.base_utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.PromptEntity;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.listener.IUpdateParseCallback;
import com.xuexiang.xupdate.proxy.IUpdateParser;
import com.xuexiang.xupdate.proxy.IUpdatePrompter;
import com.xuexiang.xupdate.proxy.IUpdateProxy;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.io.File;

public class UpDateAPPManager {
    public String mUpdateUrl3 = "https://gitee.com/xuexiangjys/XUpdate/raw/master/jsonapi/update_custom.json";
    private static boolean isForce=false;//是否强制更新，默认否
    private OnUpDateAppCallBack onUpDateAppCallBack;
    private QMUITipDialogUtil qmuiTipDialogUtil;
    public   void updateApp(Activity  context, String url,boolean isLoading) {
        if(isLoading){
            qmuiTipDialogUtil=new QMUITipDialogUtil();
            qmuiTipDialogUtil.showDialog(context,QMUITipDialogUtil.LOADING,"检查更新中",false);
        }
        XUpdate.newBuild(context)
                .updateUrl(url)
                .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .updateParser(new CustomUpdateParser(context))
                .updatePrompter(new CustomUpdatePrompter(context))
                .update();
    }
    private  class CustomUpdateParser implements IUpdateParser {
        private Context mContext;
        private boolean isHasUpdate=false;//是否有更新

        public CustomUpdateParser(Context context) {
            mContext = context;
        }

        @Override
        public UpdateEntity parseJson(String json) throws Exception {
            if(null!=qmuiTipDialogUtil){
                qmuiTipDialogUtil.dismissDialog();
            }
            LogUtils.e("解析返回的数据="+json);
            //这两句代码必须的，为的是初始化出来gson这个对象，才能拿来用
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            CustomResult result=gson.fromJson(json,CustomResult.class);
            LogUtils.e("获取解析后的实体json="+gson.toJson(result));
                if (result != null) {
                        if(result.getVersionCode()> APKVersionCodeUtils.getVersionCode(mContext)||result.isHasUpdate()){
                            isHasUpdate=true;
                        }
                        if(null!=onUpDateAppCallBack){
                            onUpDateAppCallBack.isHasUpdateCallBack(isHasUpdate);
                        }
//                        isForce=result.isIsIgnorable();//是否强制更新
                        return new UpdateEntity()
                                .setHasUpdate(isHasUpdate)//是否有新版本
                                .setForce(result.isIsIgnorable())//是否是强制更新
                                .setVersionCode(result.getVersionCode())//app版本code
                                .setVersionName(result.getVersionName())//app版本号
                                .setUpdateContent(result.getUpdateLog())//更新内容
                                .setDownloadUrl(result.getApkUrl())//apk下载地址
                                .setSize(result.getApkSize());//apk大小

            }

            return null;
        }

        @Override
        public void parseJson(String json, IUpdateParseCallback callback) throws Exception {
            callback.onParseResult(parseJson(json));
        }

        @Override
        public boolean isAsyncParser() {
            return false;
        }
    }
    private   class CustomUpdatePrompter implements IUpdatePrompter {

        private Context mContext;

        public CustomUpdatePrompter(Context context) {
            mContext = context;
        }

        @Override
        public void showPrompt(@NonNull UpdateEntity updateEntity, @NonNull IUpdateProxy updateProxy, @NonNull PromptEntity promptEntity) {
            showUpdatePrompt(updateEntity, updateProxy,mContext);
        }
    }
    /**
     * 显示自定义提示
     *
     * @param updateEntity
     * @param updateProxy
     */
    private  void showUpdatePrompt(final @NonNull UpdateEntity updateEntity, final @NonNull IUpdateProxy updateProxy,Context context) {
        String updateInfo = UpdateUtils.getDisplayUpdateInfo(context, updateEntity);
        UpDateAppMessageDialog.Builder upDateAppMessageDialog=new UpDateAppMessageDialog.Builder(context);
        if(updateEntity.isForce()){//是否强制更新
            upDateAppMessageDialog.setCancelable(false);
            upDateAppMessageDialog.setCanceledOnTouchOutside(false);
            upDateAppMessageDialog.setConfirm("立即下载");
            upDateAppMessageDialog.setCancel(null);
            upDateAppMessageDialog.setMessage(String.format("软件已更新到%s版本，"+"\n"+"请下载应用？",updateEntity.getVersionName()));
            upDateAppMessageDialog.show();
        }else{
            upDateAppMessageDialog.setCancelable(true);
            upDateAppMessageDialog.setCanceledOnTouchOutside(true);
            upDateAppMessageDialog.setConfirm("立即下载");
            upDateAppMessageDialog.setCancel("取消");
            upDateAppMessageDialog.setMessage(String.format("软件已更新到%s版本，"+"\n"+"是否下载应用？",updateEntity.getVersionName()));
            upDateAppMessageDialog.show();
        }
        upDateAppMessageDialog.setListener(new UpDateAppMessageDialog.OnListener() {
            @Override
            public void onConfirm(BaseDialog dialog) {
                updateProxy.startDownload(updateEntity, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(context, "下载进度", true);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        return true;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                    }
                });
            }

        });
    }

    public void setOnUpDateAppCallBack(OnUpDateAppCallBack onUpDateAppCallBack) {
        this.onUpDateAppCallBack = onUpDateAppCallBack;
    }
}

