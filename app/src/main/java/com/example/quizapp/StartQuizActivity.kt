package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

/**
 * StartQuizActivity is responsible for initializing the start screen UI.
 * It provides navigation to different activities such as the quiz and score screens.
 */
class StartQuizActivity : ComponentActivity() {

    /**
     * Called when the activity is created. Initializes the UI and sets up the button click listeners.
     * Each button navigates to a different activity or performs a specific action.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen) // Set the content layout for the start screen

        // Link the buttons to their respective UI elements
        val btnPlay: Button = findViewById(R.id.btnPlay)
        val btnShowScore: Button = findViewById(R.id.btnShowScore)
        val btnBack: Button = findViewById(R.id.btnBack)

        // Set click listener for the Play button to navigate to the quiz activity
        btnPlay.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java) // Navigate to QuizActivity
            startActivity(intent)
        }

        // Set click listener for the Show Score button to navigate to the score display activity
        btnShowScore.setOnClickListener {
            val intent = Intent(this, ShowScoreActivity::class.java) // Navigate to ShowScoreActivity
            startActivity(intent)
        }

        // Set click listener for the Back button to return to the previous activity
        btnBack.setOnClickListener {
            finish()
        }
    }
}