package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_array) // Make sure you create this layout for the game screen
    }
}

