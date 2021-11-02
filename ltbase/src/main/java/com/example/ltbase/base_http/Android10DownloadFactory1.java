package com.example.ltbase.base_http;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
import rxhttp.wrapper.callback.UriFactory;

/**
 * 作者：王健 on 2021/8/26
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class Android10DownloadFactory1 extends UriFactory {
    private Context context;
    private String fileName;
    private String relativePath=Environment.DIRECTORY_DOWNLOADS;
    public Android10DownloadFactory1(@NonNull Context context, String fileName) {
        super(context);
        this.context=context;
        this.fileName=fileName;
    }

//    @Nullable
//    @Override
//    public Uri query() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//            return getInsertUri().getQuery()
//        }else{
//            File file=new File(Environment.getExternalStorageDirectory()+"/"+relativePath+"/"+fileName);
//            return Uri.fromFile(file);
//        }
//    }

    @NonNull
    @Override
    public Uri insert(@NonNull Response response) throws IOException {
        ContentValues values = new ContentValues();
        //1、配置文件名
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        //2、配置文件类型
        values.put(MediaStore.MediaColumns.MIME_TYPE, "*.apk");
        //3、配置存储目录
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
        //4、将配置好的对象插入到某张表中，最终得到Uri对象
        return context.getContentResolver().insert(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), values);
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Uri getInsertUri(){
        return MediaStore.Downloads.EXTERNAL_CONTENT_URI;
    }
//    private Uri showUri(Context context,String fileName,String relativePath){
//        if(TextUtils.isEmpty(fileName)||TextUtils.isEmpty(relativePath)){
//            return null;
//        }
//        String realRelativePath="";
//        if(relativePath.startsWith("/")){
//            relativePath=relativePath.substring(1);
//        }else{
//            realRelativePath=relativePath;
//        }
//    }
}
