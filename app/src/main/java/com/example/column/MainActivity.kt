package com.example.column

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun launchWebView(view: View) {
        val launch = Intent(applicationContext, WebViewActivity::class.java)
        launch.putExtra("token", "<user token here>")
        startActivity(launch)
    }
}
