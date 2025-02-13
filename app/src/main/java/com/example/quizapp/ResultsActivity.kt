package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class ResultsActivity : ComponentActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvResultsHeader: TextView
    private lateinit var linearResults: LinearLayout
    private lateinit var tvScore: TextView
    private lateinit var btnOK: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_screen)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        tvResultsHeader = findViewById(R.id.tvResultsHeader)
        linearResults = findViewById(R.id.linearResults)
        tvScore = findViewById(R.id.tvScore)
        btnOK = findViewById(R.id.btnOK)

        // Get data from Intent
        val questionsList: List<Question>? = intent.getParcelableArrayListExtra("questions")
        val score = intent.getIntExtra("score", 0)
        val selectedAnswers = intent.getIntArrayExtra("selectedAnswers")

        // Display final score
        tvScore.text = "Score: $score"

        // Check if questionsList is not null and display the questions and answers
        questionsList?.forEachIndexed { index, question ->
            val selectedAnswer = selectedAnswers?.get(index) ?: 0
            val answerStatus = if (selectedAnswer == question.correctAnswerIndex) {
                "✅ Correct"
            } else {
                "❌ Incorrect"
            }

            // Create TextViews for each question and answer pair
            val questionTextView = TextView(this).apply {
                text = "Q${index + 1}: ${question.question}"
                textSize = 20f
                setTextColor(resources.getColor(R.color.black, theme))
            }

            val selectedAnswerTextView = TextView(this).apply {
                text = "Your Answer: ${question.options.getOrNull(selectedAnswer - 1) ?: "N/A"}"
                textSize = 18f
                setTextColor(resources.getColor(R.color.black, theme))
            }

            val correctAnswerTextView = TextView(this).apply {
                text = "Correct Answer: ${question.options.getOrNull(question.correctAnswerIndex - 1) ?: "N/A"}"
                textSize = 18f
                setTextColor(resources.getColor(R.color.garnet, theme))
            }

            val statusTextView = TextView(this).apply {
                text = answerStatus
                textSize = 18f
                setTextColor(
                    if (selectedAnswer == question.correctAnswerIndex)
                        resources.getColor(R.color.green, theme)
                    else
                        resources.getColor(R.color.red, theme)
                )
            }

            // Add each TextView to the layout
            linearResults.addView(questionTextView)
            linearResults.addView(selectedAnswerTextView)
            linearResults.addView(correctAnswerTextView)
            linearResults.addView(statusTextView)

            // Add space between questions
            val space = Space(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    20 // space height
                )
            }
            linearResults.addView(space)
        }

        // Set the button to finish the activity when clicked
        btnOK.setOnClickListener {
            finish()
        }
    }
}