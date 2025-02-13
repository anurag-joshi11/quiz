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

        btnBack.setOnClickListener {
            finish() // This will close StartGameActivity and return to the previous activity (MainActivity)
        }
    }
}
