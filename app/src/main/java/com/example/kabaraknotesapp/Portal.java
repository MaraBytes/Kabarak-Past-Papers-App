package com.example.kabaraknotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Portal extends AppCompatActivity {
private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        webview=findViewById(R.id.portalwebview);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!DetectConnection.checkInternetConnection(this)) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("No internet connection.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            startActivity(new Intent(Portal.this, Portal.class));
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.setCancelable(false);
            alert11.show();
        } else {
            webview.getSettings().setAllowContentAccess(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl("http://pastpapers.kabarak.ac.ke/");
            webview.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }


            });
            webview = findViewById(R.id.portalwebview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setAllowContentAccess(true);
            webview.getSettings().getAllowFileAccess();
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl("http://eserver.kabarak.ac.ke/students/Default.aspx");

        }
    }
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()){
            webview.goBack();
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}