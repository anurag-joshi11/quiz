package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class ResultsActivity : ComponentActivity() {

    private lateinit var linearResults: LinearLayout
    private lateinit var tvScore: TextView
    private lateinit var btnOK: Button

    // Assuming these are passed from the QuizActivity via Intent or another method
    private var selectedQuestions: List<Int>? = null
    private var selectedAnswers: List<Int>? = null
    private var correctAnswers: List<Int>? = null
    private var questions: List<String>? = null
    private var options: List<List<String>> = listOf() // List of answer options for each question

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_screen) // Ensure this is the correct layout

        // Initialize Views
        linearResults = findViewById(R.id.linearResults)
        tvScore = findViewById(R.id.tvScore)
        btnOK = findViewById(R.id.btnOK)

        // Retrieve passed data from the Intent's extras
        val bundle = intent.extras
        selectedQuestions = bundle?.getIntegerArrayList("selectedQuestions")
        selectedAnswers = bundle?.getIntegerArrayList("selectedAnswers")
        correctAnswers = bundle?.getIntegerArrayList("correctAnswers")
        questions = bundle?.getStringArrayList("questions")
        options = bundle?.getSerializable("options") as? List<List<String>> ?: listOf()

        // Calculate the score based on the selected answers
        var score = 0
        selectedAnswers?.forEachIndexed { index, selectedAnswerIndex ->
            if (selectedAnswerIndex == correctAnswers?.get(index)) {
                score++
            }
        }

        // Set score text
        tvScore.text = "Score: $score"

        // Display the questions, selected answers, and correct answers dynamically
        selectedQuestions?.forEachIndexed { index, questionIndex ->
            val question = questions?.get(questionIndex) ?: "No Question"
            val selectedOption = options.getOrNull(questionIndex)?.getOrNull(selectedAnswers?.get(index) ?: 0) ?: "No Answer"
            val correctOption = options.getOrNull(questionIndex)?.getOrNull(correctAnswers?.get(questionIndex) ?: 0) ?: "No Answer"

            // Create and set Question TextView
            val questionTextView = TextView(this).apply {
                text = "Q${index + 1}: $question"
                textSize = 22f
                setTextColor(ContextCompat.getColor(this@ResultsActivity, R.color.black))
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            linearResults.addView(questionTextView)

            // Create and set Selected Answer TextView
            val selectedAnswerTextView = TextView(this).apply {
                text = "Your Answer: $selectedOption"
                textSize = 22f
                setTextColor(ContextCompat.getColor(this@ResultsActivity, R.color.black))
            }
            linearResults.addView(selectedAnswerTextView)

            // Create and set Correct Answer TextView
            val correctAnswerTextView = TextView(this).apply {
                text = "Correct Answer: $correctOption"
                textSize = 22f
                setTextColor(ContextCompat.getColor(this@ResultsActivity, R.color.green))  // Color for correct answer
            }
            linearResults.addView(correctAnswerTextView)

            // Add spacing between each question
            val space = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)
            }
            linearResults.addView(space)
        }

        // Set up the OK button to finish the activity when clicked
        btnOK.setOnClickListener {
            finish() // Close the ResultsActivity and return to the previous activity
        }
    }
}
