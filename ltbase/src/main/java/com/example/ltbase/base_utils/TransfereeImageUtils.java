package com.example.ltbase.base_utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ltbase.R;
import com.example.ltbase.base_bean.PermissionBean;
import com.example.ltbase.base_callback.OnRequestEachPermissions;
import com.example.ltbase.base_constant.PermissionConstant;
import com.example.ltbase.base_dialog.QMUIDialogUtil;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 作者：王健 on 2021/10/25
 * 邮箱：845040970@qq.com
 * 描述：可以帮助你完成从缩略视图到原视图的无缝过渡转变, 优雅的浏览普通图片、长图、gif图、视频等不同格式的多媒体。
 */
public class TransfereeImageUtils {
    private  Transferee transferee;
    private TransferConfig config;
    private Context mContext;
    private View customView;
    private boolean isSaveImg=true;
    protected static final int READ_EXTERNAL_STORAGE = 100;
    protected static final int WRITE_EXTERNAL_STORAGE = 101;
    public TransfereeImageUtils(Context context, Transferee transferee, TransferConfig config) {
        mContext=context.getApplicationContext();
        this.transferee=transferee;
        this.config=config;
    }
    public void bindImageView(ImageView imageView,String imgUrl,int index){
        config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.ic_launcher) // 资源加载前的占位图
                .setErrorPlaceHolder(R.mipmap.ic_launcher) // 资源加载错误后的占位图
                .setProgressIndicator(new ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                .setIndexIndicator(new NumberIndexIndicator()) // 资源数量索引指示器，可以实现 IIndexIndicator 扩展
                .setImageLoader(new GlideImageLoader(mContext) ) // 图片加载器，可以实现 ImageLoader 扩展
                .setBackgroundColor(Color.parseColor("#000000")) // 背景色
                .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
                .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
                .setCustomView(getCustomView()) // 自定义视图，将放在 transferee 的面板上
                .setNowThumbnailIndex(index) // 缩略图在图组中的索引
                .enableJustLoadHitPage(true) // 是否只加载当前显示在屏幕中的的资源，默认关闭
                .enableDragClose(true) // 是否开启下拉手势关闭，默认开启
                .enableDragHide(false) // 下拉拖拽关闭时，是否先隐藏页面上除主视图以外的其他视图，默认开启
                .enableDragPause(false) // 下拉拖拽关闭时，如果当前是视频，是否暂停播放，默认关闭
                .enableHideThumb(false) // 是否开启当 transferee 打开时，隐藏缩略图, 默认关闭
                .enableScrollingWithPageChange(false) // 是否启动列表随着页面的切换而滚动你的列表，默认关闭
                .setOnLongClickListener(new Transferee.OnTransfereeLongClickListener() { // 长按当前页面监听器
                    @Override
                    public void onLongClick(ImageView imageView, String imageUri, int pos) {

                    }
                })
                .bindImageView(imageView, imgUrl); // 绑定一个 ImageView, 所有绑定方法只能调用一个
    }
    public void bindListView(ListView listView, List<String> imgUrl,int index,int imgId){
        config = TransferConfig.build()
                .setSourceUrlList(imgUrl) // 资源 uri 集合， Uri 格式
                .setMissPlaceHolder(R.mipmap.ic_launcher) // 资源加载前的占位图
                .setErrorPlaceHolder(R.mipmap.ic_launcher) // 资源加载错误后的占位图
                .setProgressIndicator(new ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                .setIndexIndicator(new NumberIndexIndicator()) // 资源数量索引指示器，可以实现 IIndexIndicator 扩展
                .setImageLoader(new GlideImageLoader(mContext) ) // 图片加载器，可以实现 ImageLoader 扩展
                .setBackgroundColor(Color.parseColor("#000000")) // 背景色
                .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
                .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
                .setCustomView(getCustomView()) // 自定义视图，将放在 transferee 的面板上
                .setNowThumbnailIndex(index) // 缩略图在图组中的索引
                .enableJustLoadHitPage(true) // 是否只加载当前显示在屏幕中的的资源，默认关闭
                .enableDragClose(true) // 是否开启下拉手势关闭，默认开启
                .enableDragHide(false) // 下拉拖拽关闭时，是否先隐藏页面上除主视图以外的其他视图，默认开启
                .enableDragPause(false) // 下拉拖拽关闭时，如果当前是视频，是否暂停播放，默认关闭
                .enableHideThumb(false) // 是否开启当 transferee 打开时，隐藏缩略图, 默认关闭
                .enableScrollingWithPageChange(false) // 是否启动列表随着页面的切换而滚动你的列表，默认关闭
                .setOnLongClickListener(new Transferee.OnTransfereeLongClickListener() { // 长按当前页面监听器
                    @Override
                    public void onLongClick(ImageView imageView, String imageUri, int pos) {
                        ToastUtils.showToast("长按保存");
                        if (isSaveImg){
                            saveImageFile(imageUri); // 使用 transferee.getFile(imageUri) 获取缓存文件保存，视频不支持
                        }
                    }
                })
                .bindListView(listView, imgId); // 绑定一个 ListView， 所有绑定方法只能调用一个
    }
    public void bindRecyclerView(RecyclerView recyclerView, List<String> imgUrl, int index, int imgId){
        config = TransferConfig.build()
                .setSourceUrlList(imgUrl) // 资源 uri 集合， Uri 格式
                .setMissPlaceHolder(R.mipmap.ic_launcher) // 资源加载前的占位图
                .setErrorPlaceHolder(R.mipmap.ic_launcher) // 资源加载错误后的占位图
                .setProgressIndicator(new ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                .setIndexIndicator(new NumberIndexIndicator()) // 资源数量索引指示器，可以实现 IIndexIndicator 扩展
                .setImageLoader(new GlideImageLoader(mContext) ) // 图片加载器，可以实现 ImageLoader 扩展
                .setBackgroundColor(Color.parseColor("#000000")) // 背景色
                .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
                .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
                .setCustomView(getCustomView()) // 自定义视图，将放在 transferee 的面板上
                .setNowThumbnailIndex(index) // 缩略图在图组中的索引
                .enableJustLoadHitPage(true) // 是否只加载当前显示在屏幕中的的资源，默认关闭
                .enableDragClose(true) // 是否开启下拉手势关闭，默认开启
                .enableDragHide(false) // 下拉拖拽关闭时，是否先隐藏页面上除主视图以外的其他视图，默认开启
                .enableDragPause(false) // 下拉拖拽关闭时，如果当前是视频，是否暂停播放，默认关闭
                .enableHideThumb(false) // 是否开启当 transferee 打开时，隐藏缩略图, 默认关闭
                .enableScrollingWithPageChange(false) // 是否启动列表随着页面的切换而滚动你的列表，默认关闭
                .setOnLongClickListener(new Transferee.OnTransfereeLongClickListener() {
                    @Override
                    public void onLongClick(ImageView imageView, String imageUri, int pos) {
                        if (isSaveImg){
                            saveImageFile(imageUri); // 使用 transferee.getFile(imageUri) 获取缓存文件保存，视频不支持
                        }
                    }
                })
                .bindRecyclerView(recyclerView, imgId);  // 绑定一个 RecyclerView， 所有绑定方法只能调用一个
    }
    private  View getCustomView(){
        return customView;
    }
    public void setCustomView(View view){
        customView=view;
    }
    public void setSaveImg(boolean isSaveImg){
        this.isSaveImg=isSaveImg;
    }
    public void show(){
        transferee.apply(config).show();
    }
    /**
     * 描述：清除缓存
     */
    public  void clear(){
        transferee.clear();
        transferee.destroy();
    }
    /**
     * 保存图片到相册使用的方法
     */
    protected void saveImageFile(String imageUri) {

        String[] uriArray = imageUri.split("\\.");
        String imageName = String.format("%s.%s", String.valueOf(System.currentTimeMillis()), uriArray[uriArray.length - 1]);
        PermissionUtil permissionUtil=new PermissionUtil();
        permissionUtil.showMoreRequestEachPermissions((Activity) mContext, new OnRequestEachPermissions() {
            @Override
            public void onShouldShowRationale(List<PermissionBean> deniedPermission) {
                ToastUtils.showToast("授权失败勾选了不在询问："+ GsonUtil.getGson().toJson(deniedPermission));

            }

            @Override
            public void onDenied(List<PermissionBean> deniedPermission) {
                ToastUtils.showToast("授权失败："+ GsonUtil.getGson().toJson(deniedPermission));
            }

            @Override
            public void onGranted() {
                showMessagePositiveDialog(imageName,imageUri);
            }
        }, PermissionConstant.WRITE_EXTERNAL_STORAGE);

    }
    /**
     * 描述：保存图片确认框
     * @param imageName 图片名称
     * @param imageUri 图片地址
     */
    private void showMessagePositiveDialog(String imageName, String imageUri) {
        QMUIDialogUtil qmuiDialog=new QMUIDialogUtil(mContext);
        qmuiDialog.showDialogMessage("是否要保存图片", "", "取消", "确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {

            }
        }, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                Bitmap bitmap=BitmapFactory.decodeFile(transferee.getImageFile(imageUri).getPath());
                if(null==bitmap){
                    ToastUtils.showToast("bitmap为空");
                    return;
                }
                putBitmapToMedia(mContext,imageName, bitmap);
            }
        });
    }
    /**
     * 描述：保存图片到相册
     * @param context  上下文
     * @param fileName 图片名称
     * @param bm 图片资源
     */
    private static void putBitmapToMedia(Context context, String fileName, Bitmap bm) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {
            OutputStream out = context.getContentResolver().openOutputStream(uri);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            ToastUtils.showToast("图片保存成功!");
        } catch (IOException e) {
            ToastUtils.showToast("图片保存失败!");
            e.printStackTrace();
        }
    }
}
