package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class StartGameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen) // Make sure this layout exists

        // Find the buttons by their IDs
        val btnPlay: Button = findViewById(R.id.btnPlay)
        val btnShowScore: Button = findViewById(R.id.btnShowScore)
        val btnBack: Button = findViewById(R.id.btnBack)

        // Set click listener for the Play button to navigate to a different activity (e.g., GameActivity)
        btnPlay.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java) // You should have a GameActivity for the game
            startActivity(intent)
        }

        // Set click listener for the Show Score button to navigate to a different activity (e.g., ShowScoreActivity)
        btnShowScore.setOnClickListener {
            val intent = Intent(this, ShowScoreActivity::class.java) // You should have a ShowScoreActivity to show scores
            startActivity(intent)
        }

        // Set click listener for the Back button to navigate back to the MainActivity
        btnBack.setOnClickListener {
            finish() // This will close StartGameActivity and return to the previous activity (MainActivity)
        }
    }
}
