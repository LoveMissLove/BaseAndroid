package com.gy.app;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.ltbase.base_utils.TransfereeImageUtils;
import com.example.ltbase.base_view.QMIUI.tipdialog.QMUIDisplayHelper;
import com.gy.app.bean.FriendsCircleBean;

import java.util.List;

/**
 * 作者：王健 on 2021/10/25
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class FriendsCircleAdapter extends BaseQuickAdapter<FriendsCircleBean, BaseViewHolder> {
    private Context context;
    private TransfereeImageUtils transfereeImageUtils;
    private DividerGridItemDecoration divider ;
    public FriendsCircleAdapter(int layoutResId, List<FriendsCircleBean> mList, FriendsCircleActivity friendsCircleActivity, TransfereeImageUtils transfereeImageUtils) {
        super(layoutResId,mList);
        context=friendsCircleActivity;
        this.transfereeImageUtils = transfereeImageUtils;
        divider = new DividerGridItemDecoration(
                Color.TRANSPARENT,
                QMUIDisplayHelper.dp2px(context,8),
                QMUIDisplayHelper.dp2px(context,8)
        );
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, FriendsCircleBean friendsCircleBean) {
        final RecyclerView rvPhotos = baseViewHolder.getView(R.id.rv_photos);
        // 重置 divider
        rvPhotos.removeItemDecoration(divider);
        rvPhotos.addItemDecoration(divider);
        if (rvPhotos.getLayoutManager() == null)
            rvPhotos.setLayoutManager(new GridLayoutManager(context, 3));
        FriendsCircleImgAdapter photosAdapter = new FriendsCircleImgAdapter(R.layout.item_image, friendsCircleBean.getList(), (FriendsCircleActivity) context);
        photosAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                transfereeImageUtils.bindRecyclerView(rvPhotos,friendsCircleBean.getList(),position,R.id.iv_thum);
                transfereeImageUtils.show();
            }
        });
        rvPhotos.setAdapter(photosAdapter);

    }
}
