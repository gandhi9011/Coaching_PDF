package com.example.admin.karsol_ano;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class PdfWebview extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_webview);
        webView=(WebView)findViewById(R.id.webview);
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        String weblink="http://authors.library.caltech.edu/26495/1/Keightley_1963.pdf";
        webView.loadUrl("https://docs.google.com/viewer?url="+weblink);
    }
}
