package com.example.column

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*
import android.util.Base64

class WebViewActivity: AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        val token = intent.getStringExtra("token")
        val json = """{"token": "$token"}""".toByteArray()
        val params = Base64.encodeToString(json, Base64.DEFAULT)

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
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        columnWebview.loadUrl(
            "http://10.0.2.2:3001/sdk?params=$params")
    }
}
