package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

/**
 * ShowScoreActivity displays the last game score fetched from SharedPreferences.
 * It also provides a button to navigate back to the previous activity.
 */
class ShowScoreActivity : ComponentActivity() {

    /**
     * Called when the activity is created.
     * Initializes the UI, retrieves the last score from SharedPreferences, and displays it.
     * Sets up a button click listener to return to the previous screen.
     */
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showscore)

        // Link the "Back" button in the layout
        val btnBack: Button = findViewById(R.id.btnBack)

        // Retrieve the last score from SharedPreferences
        val sharedPreferences = getSharedPreferences("QuizGamePrefs", MODE_PRIVATE)
        val lastScore = sharedPreferences.getInt("LAST_SCORE", 0) // Default score is 0 if no data found

        // Display the last score on the TextView
        val scoreTextView = findViewById<TextView>(R.id.tvScoreLabel)
        scoreTextView.text = "Last Game’s Score: $lastScore"

        // Set the "Back" button listener to close this activity and return to the previous one
        btnBack.setOnClickListener {
            finish()
        }
    }
}
