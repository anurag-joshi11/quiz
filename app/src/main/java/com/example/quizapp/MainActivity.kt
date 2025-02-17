package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

/**
 * The main entry point of the application.
 * This activity starts background music on launch and navigates to the quiz screen when the user clicks "OK".
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start background music service when the app launches
        startService(Intent(this, MusicService::class.java))

        val okButton: Button = findViewById(R.id.okButton)
        okButton.setOnClickListener {
            // Navigate to StartQuizActivity when OK is clicked
            startActivity(Intent(this, StartQuizActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        // Stop music only if the app is finishing
        if (isFinishing) {
            stopService(Intent(this, MusicService::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Restart the music service if it was stopped
        startService(Intent(this, MusicService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Ensure music stops when activity is destroyed
        stopService(Intent(this, MusicService::class.java))
    }
}
