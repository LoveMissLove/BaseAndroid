package com.example.ltbase.base_callback;

/**
 * 作者：王健 on 2021/8/26
 * 邮箱：845040970@qq.com
 * 描述：文件下载进度监听
 */
public interface OnDownloadListener {
    /**
     * 描述：
     * @param currentProgress 当前进度 0-100
     * @param currentSize 当前已下载的字节大小
     * @param totalSize 要下载的总字节大小
     */
    void onAccept(int currentProgress,long currentSize,long totalSize);
}
