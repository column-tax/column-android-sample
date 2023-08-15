package com.example.column

import android.content.Context
import android.webkit.JavascriptInterface
import kotlinx.android.synthetic.main.activity_webview.*
import org.json.JSONObject

class JavascriptInterface(private val mContext: Context) {
    @JavascriptInterface
    fun postMessage(payload: String) {
        val response = JSONObject(payload)

        when (val event = response.getString("name")) {
            "column-on-close" -> closeWebView()
            "column-on-user-event" -> println("Received user event: $response")
            else -> println("Unrecognized event: $event")
        }
    }

    fun closeWebView() {
        (mContext as WebViewActivity).columnWebview?.post {
            mContext.finish()
        }
    }
}
