package com.example.techie_desi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.techie_desi.R;

public class DetailActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Toolbar toolbar;
    WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        Toast.makeText(this, getIntent().getStringExtra("url"), Toast.LENGTH_SHORT).show();
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.detailView);

        setSupportActionBar(toolbar);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(DetailActivity.this, "Page Started loading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
                Toast.makeText(DetailActivity.this, "Page Loaded", Toast.LENGTH_SHORT).show();
            }
        }) ;
            webView.loadUrl(getIntent().getStringExtra("url"));

    }
}