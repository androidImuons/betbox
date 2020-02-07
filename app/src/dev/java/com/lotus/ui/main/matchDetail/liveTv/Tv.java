package com.lotus.ui.main.matchDetail.liveTv;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;

public class Tv extends AppBaseFragment {

    WebView web_view;
    String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_tv;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        web_view = getView().findViewById(R.id.web_view);

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setLoadsImagesAutomatically(true);
        // web_view.getSettings().setBuiltInZoomControls(false);
        // web_view.getSettings().setLoadWithOverviewMode(true);
        web_view.getSettings().setDomStorageEnabled(true);
        //web_view.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        //web_view.getSettings().setUseWideViewPort(true);

        web_view.setWebViewClient(new MyWebViewClient());

        web_view.loadUrl(url);

    }


    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
