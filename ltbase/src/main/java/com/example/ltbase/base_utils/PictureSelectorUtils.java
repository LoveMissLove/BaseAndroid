package com.example.ltbase.base_utils;

import android.app.Activity;

import com.example.ltbase.R;
import com.example.ltbase.base_callback.OnPictureSelectorListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;

import java.util.List;

/**
 * 作者：王健 on 2021/8/25
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class PictureSelectorUtils {
    /**
     * 描述：
     * @param activity    上下文
     * @param maxSelectNum 最大选择数量
     * @param isCamera 是否开启拍照
     * @param isEnableCrop 是否开启裁剪
     * @param onPictureSelectorListener 结果回调
     */
    public static void showPictureSelectorImage(Activity activity,int maxSelectNum,boolean isCamera,boolean isEnableCrop,OnPictureSelectorListener onPictureSelectorListener){
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()

                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(maxSelectNum)//最大选择数量,默认9张
                .minSelectNum(1)//最小选择数量,默认9张
                .setPictureWindowAnimationStyle(new PictureWindowAnimationStyle(R.anim.anim_enter_from_bottom,R.anim.anim_exit_from_bottom))//相册启动动画
                .isCamera(isCamera)//列表是否显示拍照按钮
                .imageSpanCount(4)//列表每行显示个数
                .isGif(false)//是否显示gif
                .isEnableCrop(isEnableCrop)//是否开启裁剪
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 结果回调
                        if(null!=onPictureSelectorListener){
                            onPictureSelectorListener.onResult(result);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                        if(null!=onPictureSelectorListener){
                            onPictureSelectorListener.onCancel();
                        }
                    }
                });
    }
    /**
     * 描述：
     * @param activity    上下文
     * @param maxSelectNum 最大选择数量
     * @param isCamera 是否开启拍照
     * @param isEnableCrop 是否开启裁剪
     * @param result 传入已选图片
     * @param onPictureSelectorListener 结果回调
     */
    public static void showPictureSelectorImage(Activity activity, int maxSelectNum, boolean isCamera, boolean isEnableCrop, List<LocalMedia> result, OnPictureSelectorListener onPictureSelectorListener){
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(maxSelectNum)//最大选择数量,默认9张
                .minSelectNum(1)//最小选择数量,默认9张
                .setPictureWindowAnimationStyle(new PictureWindowAnimationStyle(R.anim.anim_enter_from_bottom,R.anim.anim_exit_from_bottom))//相册启动动画
                .isCamera(isCamera)//列表是否显示拍照按钮
                .imageSpanCount(4)//列表每行显示个数
                .selectionData(result)//是否传入已选图片
                .isGif(false)//是否显示gif
                .isEnableCrop(isEnableCrop)//是否开启裁剪
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 结果回调
                        if(null!=onPictureSelectorListener){
                            onPictureSelectorListener.onResult(result);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                        if(null!=onPictureSelectorListener){
                            onPictureSelectorListener.onCancel();
                        }
                    }
                });
    }
}
