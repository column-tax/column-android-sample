package com.example.column

import android.content.Context
import android.webkit.JavascriptInterface
import kotlinx.android.synthetic.main.activity_webview.*
import org.json.JSONObject

class JavascriptInterface(private val mContext: Context) {
    @JavascriptInterface
    fun postMessage(payload: String) {
        val response = JSONObject(payload)

        when(response.getString("name")) {
            "column-on-close" -> closeWebView()
        }
    }

    fun closeWebView() {
        (mContext as WebViewActivity).columnWebview?.post {
            mContext.finish()
        }
    }
}
