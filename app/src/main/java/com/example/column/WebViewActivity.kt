package com.example.column

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        val url = intent.getStringExtra("url")!!

        setContentView(R.layout.activity_webview)
        columnWebview.settings.javaScriptEnabled = true
        columnWebview.settings.domStorageEnabled = true

        // Important: this JavascriptInterface must be named "Android" to work correctly.
        columnWebview.addJavascriptInterface(JavascriptInterface(this), "Android")
        columnWebview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val uri = request?.url ?: return super.shouldOverrideUrlLoading(view, request)

                val allowedDomains = setOf(
                    "localhost",
                    "columnapi.com",
                    "env.bz"
                )

                val host = uri.host ?: ""
                val isExternalLink = uri.queryParameterNames.contains("columntax-external-link")
                val isAllowedInWebView = allowedDomains.any { domain ->
                    host == domain || host.endsWith(domain)
                }

                if (!isExternalLink && isAllowedInWebView) {
                    return super.shouldOverrideUrlLoading(view, request)
                }

                // Open external link within the browser
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                return true;
            }
        }

        columnWebview.loadUrl(url)
    }
}
