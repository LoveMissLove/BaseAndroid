package com.example.ltbase.base_widget.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * 水印
 * 可以自定义水印文字颜色、大小和旋转角度
 *         List<String> labels = new ArrayList<>();
 *         labels.add(PreferUtils.getString("XM","")+"  "+PreferUtils.getString("JYBH",""));
 *         labels.add(PreferUtils.getString("DMSM1", ""));
 *         labels.add(DateUtils.getCurrentTime());
 *         Watermark.getInstance().show(this, labels,-30,13);
 */
public class WatermarkView {

    private static WatermarkView sInstance;


    public static WatermarkView getInstance() {
        if (sInstance == null) {
            synchronized (WatermarkView.class) {
                sInstance = new WatermarkView();
            }
        }
        return sInstance;
    }

    /**
     * 显示水印，铺满整个页面
     *
     * @param activity 活动
     * @param labels     水印
     */
    public void show(Activity activity, List<String> labels, int degress, int fontSize) {
        WatermarkViewBg drawable = new WatermarkViewBg(activity, labels, degress, fontSize);


        ViewGroup rootView = activity.findViewById(android.R.id.content);
        FrameLayout layout = new FrameLayout(activity);

        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        int heightPixel = outMetrics.heightPixels;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(widthPixel,heightPixel);

        //设置要显示在屏幕中的位置高度，也就是marginTop的值
        lp.topMargin = (heightPixel/7)-70;
        lp.gravity = Gravity.CENTER_HORIZONTAL;  //横向居中

        layout.setLayoutParams(lp);
//        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        layout.setBackgroundColor(ContextCompat.getColor(activity,R.color.black));
        layout.setBackground(drawable);

        rootView.addView(layout);
    }

    public static class WatermarkViewBg extends Drawable {

        private Paint paint = new Paint();
        private List<String> labels;
        private Context context;
        private int degress;//角度
        private int fontSize;//字体大小 单位sp

        /**
         * 初始化构造
         *
         * @param context  上下文
         * @param labels   水印文字列表 多行显示支持
         * @param degress  水印角度
         * @param fontSize 水印文字大小
         */
        public WatermarkViewBg(Context context, List<String> labels, int degress, int fontSize) {
            this.labels = labels;
            this.context = context;
            this.degress = degress;
            this.fontSize = fontSize;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {


            int width = getBounds().right;
            int height = getBounds().bottom;

            canvas.drawColor(Color.parseColor("#00000000"));
            paint.setColor(Color.parseColor("#40999999"));

            paint.setAntiAlias(true);
            paint.setTextSize(sp2px(context, fontSize));
            canvas.save();
            canvas.rotate(degress);
            float textWidth = paint.measureText(labels.get(0));
            int index = 0;
            for (int positionY = height / 10; positionY <= height; positionY += height / 10 + 80) {
                float fromX = -width + (index++ % 2) * textWidth;
                for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                    int spacing = 0;//间距
                    for (String label : labels) {
                        canvas.drawText(label, positionX, positionY + spacing, paint);
                        spacing = spacing + 50;
                    }

                }
            }
            canvas.restore();
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.UNKNOWN;
        }


        public  int sp2px(Context context, float spValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
    }
}