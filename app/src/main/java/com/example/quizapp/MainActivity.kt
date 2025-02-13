package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Load the XML layout

        // Start music service if not already running
        startService(Intent(this, MusicService::class.java))

        // Find the OK button by ID
        val okButton: Button = findViewById(R.id.okButton)

        // Set click listener to navigate to StartQuizActivity
        okButton.setOnClickListener {
            val intent = Intent(this, StartQuizActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            stopService(Intent(this, MusicService::class.java))
        }
    }
}
