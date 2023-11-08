package com.example.column

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private var fileUploadCallback: ValueCallback<Array<Uri>>? = null
private const val FILE_CHOOSER_REQUEST_CODE = 1
private lateinit var currentPhotoUri: Uri

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        val url = intent.getStringExtra("url")!!

        setContentView(R.layout.activity_webview)
        columnWebview.settings.javaScriptEnabled = true
        columnWebview.settings.domStorageEnabled = true
        columnWebview.settings.allowFileAccess = true
        columnWebview.settings.allowContentAccess = true

        // Important: this JavascriptInterface must be named "Android" to work correctly.
        columnWebview.addJavascriptInterface(JavascriptInterface(this), "Android")
        columnWebview.webViewClient = object : WebViewClient() {
            // Open external URLs in a separate browser and not the WebView
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

        // Open up the file chooser / camera when opting to upload a file (eg a W-2)
        // Note: If your user has not already granted access to the camera, then you may need
        // to ask for further runtime permissions, see:
        // https://developer.android.com/training/permissions/requesting
        columnWebview.webChromeClient = object : WebChromeClient() {
            private fun createImageFileUri(): Uri {
                val fileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()) + ".jpg"
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
                }
                val resolver: ContentResolver = contentResolver
                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                return imageUri ?: throw RuntimeException("ImageUri is null")
            }

            override fun onShowFileChooser(webView: WebView?,
                                           filePathCallback: ValueCallback<Array<Uri>>?,
                                           fileChooserParams: FileChooserParams?): Boolean {
                fileUploadCallback?.onReceiveValue(null)
                fileUploadCallback = filePathCallback

                if (fileChooserParams?.acceptTypes?.contains("image/*") == true && fileChooserParams.isCaptureEnabled) {
                    // Launch camera
                    val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    currentPhotoUri = createImageFileUri()
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
                    startActivityForResult(captureIntent, FILE_CHOOSER_REQUEST_CODE)
                } else {
                    // Open file chooser
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    val chooserIntent = Intent.createChooser(intent, "Choose File")
                    startActivityForResult(chooserIntent, FILE_CHOOSER_REQUEST_CODE)

                }
                return true
            }
        }

        columnWebview.loadUrl(url)
    }

    override fun onBackPressed() {
        if (columnWebview.canGoBack()) {
            columnWebview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    // Support uploading a file (for example, a photo of a W-2)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            if (fileUploadCallback == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }

            val results: Array<Uri>? = when {
                resultCode == RESULT_OK && data?.data != null -> arrayOf(data.data!!)
                resultCode == RESULT_OK -> arrayOf(currentPhotoUri)
                else -> null
            }

            fileUploadCallback?.onReceiveValue(results)
            fileUploadCallback = null
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
