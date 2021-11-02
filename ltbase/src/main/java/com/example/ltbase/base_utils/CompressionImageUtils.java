package com.example.ltbase.base_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;


import com.example.ltbase.base_utils.file.FileManager;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;


/**
 * 作者：王健 on 2021/8/23
 * 邮箱：845040970@qq.com
 * 描述：图片压缩工具类
 */
public class CompressionImageUtils {
    /**
     * @param quality 压缩质量，0-100
     * @param imagePath 图片原始路径
     * @param cachePath 图片压缩后缓存路径
     * */
    public static String compressionImgPath(Context context,int quality,String imagePath,String cachePath){
        String compressionImgPath="";
        String mCachePath="";
        if(TextUtils.isEmpty(imagePath)){
            return "";
        }
       if(TextUtils.isEmpty(cachePath)){
           FileManager.getInstance(context);
           mCachePath= FileManager.getCacheImagePath();
       }else{
           mCachePath=cachePath;
       }
        File file = null;
        try {
            file = new Compressor(context)
                    .setQuality(quality)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(mCachePath)
                    .compressToFile(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    /**
     * @param quality 压缩质量，0-100
     * @param imagePath 图片原始路径
     * @param cachePath 图片压缩后缓存路径
     * @param maxHeight 压缩后图片最大高度
     * @param maxWidth 压缩后图片最大宽度
     * */
    public static String compressionImgPath(Context context,int quality,int maxHeight,int maxWidth, String imagePath,String cachePath){
        String compressionImgPath="";
        String mCachePath="";
        if(TextUtils.isEmpty(imagePath)){
            return "";
        }
        if(TextUtils.isEmpty(cachePath)){
            FileManager.getInstance(context);
            mCachePath= FileManager.getCacheImagePath();
        }else{
            mCachePath=cachePath;
        }
        File file = null;
        try {
            file = new Compressor(context)
                    .setQuality(quality)
                    .setMaxHeight(maxHeight)
                    .setMaxWidth(maxWidth)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(mCachePath)
                    .compressToFile(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
