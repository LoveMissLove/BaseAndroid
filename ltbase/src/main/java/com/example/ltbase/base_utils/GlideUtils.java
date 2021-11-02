package com.example.ltbase.base_utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.ltbase.R;


/**
 * 作者：王健 on 2018/10/8
 * 邮箱：845040970@qq.com
 */
public class GlideUtils {
    /**
     *加载图片(默认)
     * @param context   上下文
     * @param url 图片地址
     * @param imageView 需要加载的imageview
     */
    public static void loadImage(Context context, Object url, ImageView imageView) {
        if(context!=null){
            DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();

            RequestOptions options = new RequestOptions()

                    //占位图
                    .placeholder(R.color.base_white)
                    //错误图
                    .error(R.mipmap.ic_img_error)
                    // .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions.with(drawableCrossFadeFactory)).into(imageView);

        }

    }
    /**
     *加载gif图片(默认)
     * @param context   上下文
     * @param url 图片地址
     * @param imageView 需要加载的imageview
     */
    public static void loadGifImage(Context context, Uri url, ImageView imageView) {
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();

        if(context!=null){
            RequestOptions options = new RequestOptions()
                    //占位图
                    .placeholder(R.mipmap.ic_img_error)
                    //错误图
                    .error(R.mipmap.ic_img_error)
                    // .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions.with(drawableCrossFadeFactory)).into(imageView);
        }

    }
    /**
     *加载图片头像(默认)
     * @param context   上下文
     * @param url 图片地址
     * @param imageView 需要加载的imageview
     * 当de.hdodenhof.circleimageview.CircleImageView 和 DrawableCrossFadeFactory组合使用时会在第一次加载图片出问题
     */
    public static void loadImageHead(Context context, Object url, ImageView imageView) {

        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();

        if(context!=null){
            RequestOptions options = new RequestOptions()
                    .centerInside()
                    //占位图
                    .placeholder(R.color.base_white)
                    //错误图
                    .error(R.mipmap.ic_user_head)
                    // .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(url).apply(options)
//                    .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                    .into(imageView);
        }

    }
    /**
     *加载圆形图片
     */
    public static void loadCircleImage(Context context, Object url, ImageView imageView) {
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();

        if(context!=null){
            RequestOptions options = new RequestOptions()
                    //设置圆形
                    .circleCrop()
                    .placeholder(R.color.base_white)
                    .error(R.mipmap.ic_launcher)
                    //.priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(url).apply(options).transition(DrawableTransitionOptions.with(drawableCrossFadeFactory)).into(imageView);
        }

    }
    /**
     *加载圆角图片
     * @param context  上下文
     * @param url 图片加载地址
     * @param imageView 需要加载的imageview
     * @param roundingRadius 圆角大小
     */
    public static void loadCustRoundCircleImage(Context context, Object url, ImageView imageView, int roundingRadius) {
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();

        if(context!=null){
            RequestOptions options=new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_img_error)
                    .error(R.mipmap.ic_img_error)
                    .optionalTransform(new RoundedCorners(roundingRadius))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(url).apply(options)
                    .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                    .into(imageView);
        }

    }
    public static String getEntryName(String picturePath) {
        if (!TextUtils.isEmpty(picturePath)) {
            int start = picturePath.lastIndexOf(".");//这样获取的就是名称，如果双引号里面是点的话获取的就是后缀名
            String format = "";
            if (start < 0) {
                format = picturePath;
            } else {
                format = picturePath.substring(start + 1);
            }
            return format;
        }
        return "";
    }

    public static void displayImage8(Context context, Object imageURL, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_failure)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        //.transform(new GlideRoundTransform(radius));
        Glide.with(context).load(imageURL).apply(options).into(imageView);
    }

    public static void displayImage9(Context context, Object imageURL, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_login_errey)
                .error(R.mipmap.ic_login_errey)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(imageURL).apply(options).into(imageView);
    }
}
