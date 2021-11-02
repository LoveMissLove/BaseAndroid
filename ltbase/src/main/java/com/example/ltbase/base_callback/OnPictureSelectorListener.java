package com.example.ltbase.base_callback;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 作者：王健 on 2021/8/25
 * 邮箱：845040970@qq.com
 * 描述：相册选择回调
 */
public interface OnPictureSelectorListener {
    void onResult(List<LocalMedia> result);
    void onCancel();
}
