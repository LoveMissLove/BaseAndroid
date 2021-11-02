package com.example.ltbase.base_activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ltbase.R;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * 作者：王健 on 2021/9/6
 * 邮箱：845040970@qq.com
 * 描述：
 */
public class WebViewActivity extends BaseActivity{
    private LinearLayout llLeft;
    private TextView tvTitle;
    private WebView webView;
    private ProgressBar progressBar;
    private WebSettings webSettings;// WebSettings对象
    private String url = "http://www.baidu.com";
    private boolean isAnimStart = false;
    private int currentProgress;
    private String mTitle="我是WebView";
    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
    }
    public static void intentActivity(Activity activity, String title, String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }
    @Override
    protected void initView() {
        super.initView();
        llLeft= F(R.id.ll_left);
        tvTitle= F(R.id.tv_title);
        webView= F(R.id.web);
        progressBar= F(R.id.progressBar);
        mTitle=TextUtils.isEmpty(getIntent().getStringExtra("title"))?mTitle:getIntent().getStringExtra("title");
        tvTitle.setText(mTitle);
        url =TextUtils.isEmpty(getIntent().getStringExtra("url"))?url:getIntent().getStringExtra("url");
        openWebViewPage();
    }
    /**
     * 打开webView页面
     */
    private void openWebViewPage() {
        IX5WebViewExtension ix5WebViewExtension = webView.getX5WebViewExtension();
        if(null!=ix5WebViewExtension){
            ix5WebViewExtension.setScrollBarFadingEnabled(false);
        }

        // 清除网页访问留下的缓存
        // 由于内核缓存是全局的因此这个方法不仅仅针对webView而是针对整个应用程序
        webView.clearCache(true);

        // 清除当前webView的访问历史记录
        webView.clearHistory();

        // 这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        webView.clearFormData();
        // 获取WebSettings对象
        webSettings = webView.getSettings();
        // 特别注意：5.1以上默认禁止了https和http混用。下面代码是开启
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不使用缓存，直接用网络加载
        webSettings.setJavaScriptEnabled(true);// webView支持javascript
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 告诉js可以自动打开window

        // 两者一起使用，可以让html页面加载显示适应手机的屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setSavePassword(false);// 关闭密码保存提醒；该方法在以后的版本中该方法将不被支持

        webSettings.setDomStorageEnabled(true);// 设置支持DOM storage API

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        // 添加js交互接口类，并起别名 AndroidWebView
        //TODO 注入js方法
        registerJsData();
        //设置WebViewClient类
        webView.setWebViewClient(new WebViewClient() {

            // 设置不用系统浏览器打开,直接显示在当前 webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如果不是http或者https开头的url，那么使用手机自带的浏览器打开
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return true;
                    }
                }
                view.loadUrl(url);
                return false;
//                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);

            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                //TODO 如果不想用微信自己的小乌鸦错误页面，在这里自己设置需要显示的页面
//                webView.onResume();
//                webView.reload();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

            }
        });
        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                currentProgress = progressBar.getProgress();
                if (newProgress >= 100 && !isAnimStart) {
                    // 防止调用多次动画
                    isAnimStart = true;
                    progressBar.setProgress(newProgress);
                    // 开启属性动画让进度条平滑消失
                    startDismissAnimation(progressBar.getProgress());
                } else {
                    // 开启属性动画让进度条平滑递增
                    startProgressAnimation(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
                //首页的外部跳转要从网页获取标题
                if ("首页跳转".equals(mTitle)){
                    tvTitle.setText(str);
                }
            }
        });
        if("预售活动协议".equals(tvTitle.getText().toString())||"团购活动协议".equals(tvTitle.getText().toString())){
            webView.loadDataWithBaseURL(null,url, "text/html" , "utf-8", null);//加载html数据
        }else{
            webView.loadUrl(url);
        }

    }


    // 先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空
    private void clearWebView() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.clearCache(true);// 清除缓存

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(false);
        }
    }

    /**
     * progressBar递增动画
     */
    private void startProgressAnimation(int newProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    /**
     * progressBar消失动画
     */
    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(progressBar, "alpha", 1.0f, 0.0f);
        anim.setDuration(1500);  // 动画时长
        anim.setInterpolator(new DecelerateInterpolator());     // 减速
        // 关键, 添加动画进度监听器
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();      // 0.0f ~ 1.0f
                int offset = 100 - progress;
                progressBar.setProgress((int) (progress + offset * fraction));
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束
                progressBar.setProgress(0);
                progressBar.setVisibility(View.INVISIBLE);
                isAnimStart = false;
            }
        });
        anim.start();
    }


    /**
     * 注入js方法，
     * window.jsCallAndroid.jumpToCart(); // 跳购物车
     * window.jsCallAndroid.expiredToken(); // token失效
     * */
    private void registerJsData() {
        webView.addJavascriptInterface(new JavascriptInterface(this), "jsCallAndroid");
    }
    /**
     * Native JS 接口
     */

    @SuppressLint("JavascriptInterface")
    public class JavascriptInterface {

        /**
         * 构造方法
         */

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        /**
         * JS 调用 Android
         */
        @android.webkit.JavascriptInterface
        public void jumpToCart() {
            //TODO 再此执行javascript回调方法

        }
    }
}
