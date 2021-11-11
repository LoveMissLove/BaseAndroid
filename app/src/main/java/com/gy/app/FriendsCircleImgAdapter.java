package com.gy.app;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * 作者：王健 on 2021/10/25
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class FriendsCircleImgAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public FriendsCircleImgAdapter(int layoutResId,List<String> mList, FriendsCircleActivity friendsCircleActivity) {
        super(layoutResId,mList);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        ImageView imageView = baseViewHolder.getView(R.id.iv_thum);
        Glide.with(imageView)
                .load(s)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
