package com.pandaq.pandaeye.utils.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pandaq.pandaeye.R;

/**
 * Created by PandaQ on 2017/6/29.
 * 带进度条的WebView
 */

public class ProcessWebView extends WebView {

    private ProgressBar mProgressBar;

    public ProcessWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10, 0, 0));
        Drawable drawable = context.getResources().getDrawable(R.drawable.webview_process_state, null);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
        setWebViewClient(new MyWebClient());
        setWebChromeClient(new ChromeClient());
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                addImageClickListener(view);
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE) {
                    mProgressBar.setVisibility(VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        }
    }

    private class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    /**
     * 位所有的图片添加点击事件
     *
     * @param webView 对应的 WebView
     */
    private void addImageClickListener(WebView webView) {
        webView.loadUrl("javascript:(initClick())()");
    }

}
