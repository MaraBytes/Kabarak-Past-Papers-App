package com.example.kabaraknotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    private DrawerLayout drawerLayout;

    private Toolbar toolbar;
    WebView webView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main);
        final ProgressDialog progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setMessage("Please wait...");

        setToolbar();
        webView = findViewById(R.id.mainwebview);
        if (!DetectConnection.checkInternetConnection(this)) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("No internet connection.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
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
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("http://pastpapers.kabarak.ac.ke/");
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

            });

            this.drawerLayout = findViewById(R.id.drawer_layout);
            this.navigationView = findViewById(R.id.nav_view);
            this.navigationView.setNavigationItemSelectedListener(this);
            this.drawerLayout.addDrawerListener(this);
        }
    }

    private void setToolbar() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Past Papers");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Open left menu
                this.drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.website:
                String url = "https://kabarak.ac.ke/home-page-default/";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                closeDrawer();
                break;
            case R.id.Students_portal:
                startActivity(new Intent(this, Portal.class));
                closeDrawer();
                break;
            case R.id.share:
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Kabarak Past Papers");
                intent.putExtra(Intent.EXTRA_TEXT,"Get Easy access to Kabarak Past Papers with this app, Link :  https://play.google.com/store/apps/details?id=com.oyopizManga.mangaapp");
                intent.setType("text/plain");
                startActivity(intent);
                closeDrawer();
                break;
            case R.id.support:
                Intent mintent =new Intent();
                mintent.setAction(Intent.ACTION_SEND);
                mintent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Kabarak Past Papers");
                mintent.putExtra(Intent.EXTRA_TEXT,"Get Easy access to Kabarak Past Papers with this app, Link :  https://play.google.com/store/apps/details?id=com.oyopizManga.mangaapp");
                mintent.setType("text/plain");
                startActivity(mintent);
                closeDrawer();
                break;
            case R.id.android:
                Toast.makeText(this, "Android", Toast.LENGTH_SHORT).show();
                closeDrawer();
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    // .add(R.id.nav_host_fragment,fragment)
                    .commit();

            menuItem.setChecked(true);
            getSupportActionBar().setTitle(menuItem.getTitle());
            this.drawerLayout.closeDrawers();
        }

        return true;
    }

    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}