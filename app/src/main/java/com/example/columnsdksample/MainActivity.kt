package com.example.columnsdksample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.columntax.columntaxfile.ColumnTaxFileActivity

class MainActivity : AppCompatActivity() {
    private lateinit var urlEditText: EditText
    private lateinit var launchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        urlEditText = findViewById(R.id.urlEditText)
        launchButton = findViewById(R.id.launchButton)

        // Initially disable the button
        launchButton.isEnabled = false
        launchButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))

        // Change button color and state based on URL input
        urlEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val hasText = !s.isNullOrEmpty()
                if (hasText) {
                    launchButton.isEnabled = true
                    launchButton.setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_blue_dark))
                } else {
                    launchButton.isEnabled = false
                    launchButton.setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.darker_gray))
                }
            }
        })

        launchButton.setOnClickListener {
            launchColumnTaxSDK()
        }
    }

    private fun launchColumnTaxSDK() {
        val userUrl = urlEditText.text.toString().trim()

        if (userUrl.isEmpty()) {
            Toast.makeText(this, "Please enter a user URL", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val columnTaxActivity = ColumnTaxFileActivity(applicationContext, userUrl)
            columnTaxActivity.start()
        } catch (e: Exception) {
            Toast.makeText(this, "Error launching Column SDK: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}