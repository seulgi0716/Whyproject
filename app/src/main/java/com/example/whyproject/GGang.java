package com.example.whyproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GGang extends AppCompatActivity {
    
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ActionBar actionBar = getSupportActionBar();  //제목줄 객체 얻어오기
        actionBar.setTitle("너도 춰봐 깡!");

        actionBar.setDisplayHomeAsUpEnabled(true);

        web = findViewById(R.id.webView1);
        web.setWebViewClient(new CookWebViewClient());

        WebSettings webSet = web.getSettings();
        webSet.setBuiltInZoomControls(true);
        webSet.setJavaScriptEnabled(true);
        webSet.setJavaScriptCanOpenWindowsAutomatically(true);
        webSet.setAllowFileAccess(true);

        String ggangyt= "https://www.youtube.com/watch?v=hpI2A4RTvhs";
        web.loadUrl(ggangyt);
        web.setWebChromeClient(new FullscreenableChromeClient(this));
    }

    class CookWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    public void onBackPressed() {

    }

}
