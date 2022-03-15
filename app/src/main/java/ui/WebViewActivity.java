package ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.shahcement.nirmaneyaami.R;

public class WebViewActivity extends PreBaseActivity {

    private static final int REQUEST_SELECT_FILE = 100;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;

    WebView webview;
    ProgressBar pb;


    private MyChromeClient mClient;
    private ProgressBar progressBar;
    private String downloadUrl = "";
    private String contentDescription = "";
    private String fileMimeType = "";
    private String downloadUserAgent = "";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("");

        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        pb = findViewById(R.id.pb);
        pb.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.progress_bar_bg_color), PorterDuff.Mode.SRC_IN);

        webview = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        webview.setBackgroundColor(Color.TRANSPARENT);

        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowContentAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showView(progressBar);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (view.getProgress() != 100) return;
                hideView(progressBar);
            }


            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description,
                        failingUrl);
                finish();
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                //if url doesn't start with http or https, or has target=_blank send it outside webView
                if (!url.startsWith("http") || url.contains("target=_blank")) {
                    finish();
                    return true;
                }


                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //if url doesn't start with http or https, or has target=_blank send it outside webView
                if (!url.startsWith("http") || url.contains("target=_blank")) {
                    finish();
                    return true;
                }

                return false;
            }
        });

        mClient = new MyChromeClient();
        webview.setWebChromeClient(mClient);

        loadURL(getIntent());
    }

    private boolean requestPermissions(int action) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    action);

            return true;
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadURL(intent);
    }

    private void loadURL(Intent intent) {
        if (intent == null) return;

        String url = intent.getStringExtra("url");
        if (TextUtils.isEmpty(url))
            return;

        webview.loadUrl(url);

    }


    class MyChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            try {
                if (progress == 100)
                    pb.setVisibility(View.GONE);
                else
                    pb.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(MyChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }


    @Override
    protected void onResume() {
        if (webview != null) webview.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (webview != null) webview.onPause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}