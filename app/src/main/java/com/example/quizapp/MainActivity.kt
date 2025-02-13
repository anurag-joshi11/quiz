package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Load the XML layout

        // Find the OK button by ID
        val okButton: Button = findViewById(R.id.okButton)

        // Set click listener to navigate to StartGameActivity
        okButton.setOnClickListener {
            val intent = Intent(this, StartGameActivity::class.java)
            startActivity(intent)
        }
    }
}
