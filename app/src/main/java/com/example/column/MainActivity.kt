package com.example.column

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var urlEditText: EditText;
    // Either set test URL here or paste one into your Android device
    private var defaultUrl = "<user url>"

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        urlEditText = findViewById(R.id.urlEditText)
    }

    fun launchWebView(view: View) {
        val intent = Intent(applicationContext, WebViewActivity::class.java)
        val url = getUrlText()
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun getUrlText(): String {
        var urlText = urlEditText.text.toString()

        return if (urlText.isNullOrEmpty()) {
            return defaultUrl
        } else {
            urlText
        }
    }
}
