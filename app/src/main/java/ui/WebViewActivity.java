package ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.shahcement.nirmaneyaami.R;

public class WebViewActivity extends PreBaseActivity {

    private static final int REQUEST_SELECT_FILE = 100;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;

    WebView webview;
    ProgressBar pb;


    private MyChromeClient mClient;
    private final int EXTERNAL_STORAGE_REQUEST_CODE = 102;
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

        webview.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
            this.downloadUrl = url;
            this.downloadUserAgent = userAgent;
            this.contentDescription = contentDisposition;
            this.fileMimeType = mimeType;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
                    downloadFile(downloadUrl, downloadUserAgent, contentDescription, fileMimeType);
                else
                    ActivityCompat.requestPermissions(WebViewActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQUEST_CODE);
            } else
                downloadFile(downloadUrl, downloadUserAgent, contentDescription, fileMimeType);
        });

        loadURL(getIntent());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadFile(downloadUrl, downloadUserAgent, contentDescription, fileMimeType);
                }
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    /**
     * Download file from URL based on Mime Type
     *
     * @param url,                URL of the file to be download
     * @param userAgent,          User agent of request
     * @param contentDisposition, Content description of file to be download
     * @param mimeType,           MIME type of file to be download
     */
    public void downloadFile(final String url, final String userAgent, String contentDisposition, String mimeType) {

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(userAgent) || TextUtils.isEmpty(contentDisposition) || TextUtils.isEmpty(mimeType))
            return;

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setMimeType(mimeType);
        String cookies = CookieManager.getInstance().getCookie(url);
        request.addRequestHeader("cookie", cookies);
        request.addRequestHeader("User-Agent", userAgent);
        request.setDescription("Downloading file...");
        request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);
    }

}