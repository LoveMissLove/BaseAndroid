package com.gy.app;


import android.content.Context;
import android.net.Uri;
import android.os.Build;

import com.example.ltbase.base_bean.Response;
import com.example.ltbase.base_callback.OnDownloadListener;
import com.example.ltbase.base_callback.OnUploadListener;
import com.example.ltbase.base_http.Android10DownloadFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.entity.Progress;
import rxhttp.wrapper.param.RxHttp;

/**
 * 作者：王健 on 2021/8/5
 * 邮箱：845040970@qq.com
 * 描述：网络请求封装，可再次执行参数加密，添加token，添加header
 */
public class HttpService<T> {
    public static   String  app_type="1";//appType 1 Android，2 ios，3 微信小程序
    public static   String  header="app_type";//appType 1 Android，2 ios，3 微信小程序

    /**
     * 描述：不带参数的get请求
     * @return observable
     * @param url 请求地址
     * @param type 类型
     */
    public  Observable<Response<T>> get(String url, Class<T> type){
        return RxHttp.get(url)
                .addHeader(header,app_type)
                .asResponse(type).observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 描述：带参数的get请求
     * @return observable
     * @param url 请求地址
     * @param type 类型
     * @param bodyMap 请求参数
     */
    public Observable<Response<T>> get(String url,Class<T> type, Map<String, String> bodyMap) {
        return RxHttp.get(url)
                .addHeader(header,app_type)
                .addAll(bodyMap)
                .asResponse(type).observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 描述：不带进度的get请求下载
     * @return observable
     * @param url 请求地址
     * @param fileName 文件名
     * @param context  上下文
     */
    public  Observable<Uri> getFileDownload(String url, String fileName, Context context){
        return RxHttp.get(url)
                .addHeader(header,app_type)
                .asDownload(new Android10DownloadFactory(context,fileName)).observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 描述：带进度的get请求下载
     * @return observable
     * @param url 请求地址
     * @param fileName 文件名
     * @param context  上下文
     * @param onDownloadListener 下载进度监听
     */
    public  Observable<Uri> getFileDownloadProgress(String url, String fileName, Context context, OnDownloadListener onDownloadListener){
        return RxHttp.get(url)
                .addHeader(header,app_type)
                .asDownload(new Android10DownloadFactory(context, fileName), AndroidSchedulers.mainThread(), progress -> {
                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已下载的字节大小
                    long totalSize = progress.getTotalSize();     //要下载的总字节大小
                    if(null!=onDownloadListener){
                        onDownloadListener.onAccept(currentProgress,currentSize,totalSize);
                    }

                });
    }
    /**
     * 描述：post表单请求
     * @return observable
     * @param url 请求地址
     * @param type 类型
     * @param bodyMap 请求参数
     */
    public  Observable<Response<T>> post(String url, Class<T> type,Map<String, String> bodyMap){
        return RxHttp.postForm(url)
                .addHeader(header,app_type)
                .addAll(bodyMap)
                .asResponse(type).observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 描述：post json请求
     * @return observable
     * @param url 请求地址
     * @param type 类型
     * @param json json参数
     */
    public  Observable<Response<T>> postJson(String url, Class<T> type,String json){
        return RxHttp.postJson(url)
                .addHeader(header,app_type)
                .addAll(json)
                .asResponse(type).observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 描述：post Json数组请求
     * @return observable
     * @param url 请求地址
     * @param type 类型
     * @param mList 数组
     */
    public  Observable<Response<T>> postJsonArray(String url, Class<T> type,List<T>mList){
        return RxHttp.postJsonArray(url)
                .addHeader(header,app_type)
                .addAll(mList)
                .asResponse(type).observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 描述：post 表单上传文件，多参数,多文件对应一个fileKey
     * @return observable
     * @param url 请求地址
     * @param type 类型
     * @param bodyMap 请求参数
     * @param fileKey 文件key
     * @param fileList 文件列表
     */
    public  Observable<Response<T>> postFile(String url, Context context, Class<T> type, Map<String, String> bodyMap, String fileKey, List<File> fileList, OnUploadListener onUploadListener){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            List<Uri>uriList=new ArrayList<>();
            for (int i = 0; i <fileList.size() ; i++) {
                uriList.add(Uri.fromFile(fileList.get(i)));
            }
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addAll(bodyMap)
                    .addParts(context,fileKey,uriList)
                    .upload(AndroidSchedulers.mainThread(),new Consumer<Progress>() {
                        @Override
                        public void accept(Progress progress) throws Throwable {
                            int currentProgress = progress.getProgress(); //当前进度 0-100
                            long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                            long totalSize = progress.getTotalSize();     //要上传的总字节大小
                            if(null!=onUploadListener){
                                onUploadListener.onAccept(currentProgress,currentSize,totalSize);
                            }
                        }
                    })
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }else{
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addAll(bodyMap)
                    .addFiles(fileKey,fileList)
                    .upload(AndroidSchedulers.mainThread(),new Consumer<Progress>() {
                        @Override
                        public void accept(Progress progress) throws Throwable {
                            int currentProgress = progress.getProgress(); //当前进度 0-100
                            long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                            long totalSize = progress.getTotalSize();     //要上传的总字节大小
                            if(null!=onUploadListener){
                                onUploadListener.onAccept(currentProgress,currentSize,totalSize);
                            }
                        }
                    })
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }

    }
    /**
     * 描述：post 表单上传文件，多参数,多文件对应多个fileKey
     * @return observable
     * @param url 请求地址
     * @param context 上下文
     * @param type 类型
     * @param bodyMap 请求参数
     * @param fileMap 文件
     */
    public  Observable<Response<T>> postFile(String url,Context context, Class<T> type,Map<String, String> bodyMap,Map<String,File> fileMap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            Map<String,Uri>uriMap=new HashMap<>();
            for(Map.Entry<String, File> entry:fileMap.entrySet()){
                System.out.println("key="+entry.getKey()+"\tvalue="+entry.getValue());
                uriMap.put(entry.getKey(),Uri.fromFile(entry.getValue()));
            }
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addAll(bodyMap)
                    .addParts(context,uriMap)
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }else{
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addAll(bodyMap)
                    .addFiles(fileMap)
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }

    }
    /**
     * 描述：post 表单上传文件,多文件对应多个fileKey
     * @return observable
     * @param url 请求地址
     * @param context 上下文
     * @param type 类型
     * @param fileMap 文件
     */
    public  Observable<Response<T>> postFile(String url,Context context, Class<T> type,Map<String,File> fileMap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            Map<String,Uri>uriMap=new HashMap<>();
            for(Map.Entry<String, File> entry:fileMap.entrySet()){
                System.out.println("key="+entry.getKey()+"\tvalue="+entry.getValue());
                uriMap.put(entry.getKey(),Uri.fromFile(entry.getValue()));
            }
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addParts(context,uriMap)
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }else{
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addFiles(fileMap)
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }

    }
    /**
     * 描述：post 表单上传文件 一个fileKey对应多个文件
     * @return observable
     * @param url 请求地址
     * @param type 类型
     * @param fileKey 文件key
     * @param fileList 文件列表
     */
    public  Observable<Response<T>> postFile(String url,Context context, Class<T> type,String fileKey,List<File> fileList){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            List<Uri>uriList=new ArrayList<>();
            for (int i = 0; i <fileList.size() ; i++) {
                uriList.add(Uri.fromFile(fileList.get(i)));
            }
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addParts(context,fileKey,uriList)
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }else{
            return RxHttp.postForm(url)
                    .addHeader(header,app_type)
                    .addFiles(fileKey,fileList)
                    .asResponse(type).observeOn(AndroidSchedulers.mainThread());
        }

    }

}
