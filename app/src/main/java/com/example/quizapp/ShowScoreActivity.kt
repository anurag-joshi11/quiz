package com.example.quizapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class ShowScoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showscore) // Make sure you create this layout to display the score

        val btnBack: Button = findViewById(R.id.btnBack)

        val sharedPreferences = getSharedPreferences("QuizGamePrefs", MODE_PRIVATE)
        val lastScore = sharedPreferences.getInt("LAST_SCORE", 0) // Default score is 0 if no data found

      // Assuming you have a TextView with id scoreTextView
        val scoreTextView = findViewById<TextView>(R.id.tvScoreLabel)
        scoreTextView.text = "Last Game’s Score: $lastScore"


        btnBack.setOnClickListener {
            finish() // This will close StartQuizActivity and return to the previous activity (MainActivity)
        }
    }
}
