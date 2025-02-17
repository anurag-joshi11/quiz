package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.activity.ComponentActivity

/**
 * ResultsActivity displays the results of the quiz, showing each question,
 * the user's selected answer, the correct answer, and whether the answer was correct or not.
 */
class ResultsActivity : ComponentActivity() {

    // Declare UI elements
    private lateinit var tvTitle: TextView
    private lateinit var tvResultsHeader: TextView
    private lateinit var linearResults: LinearLayout
    private lateinit var tvScore: TextView
    private lateinit var btnOK: Button

    /**
     * Called when the activity is created.
     * Sets up the UI elements and displays the results based on the data passed from the previous activity.
     */
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_screen)

        // Link UI elements to the layout views
        tvTitle = findViewById(R.id.tvTitle)
        tvResultsHeader = findViewById(R.id.tvResultsHeader)
        linearResults = findViewById(R.id.linearResults)
        tvScore = findViewById(R.id.tvScore)
        btnOK = findViewById(R.id.btnOK)

        // Retrieve quiz data passed from the previous activity
        val questionsList: List<Question>? = intent.getParcelableArrayListExtra("questions")
        val score = intent.getIntExtra("score", 0)
        val selectedAnswers = intent.getIntArrayExtra("selectedAnswers")
        val selectedQuestions = intent.getIntegerArrayListExtra("selectedQuestions")

        // Reorder the questions list based on the order they were asked
        val orderedQuestionsList = selectedQuestions?.mapNotNull { index ->
            questionsList?.getOrNull(index)
        } ?: emptyList()

        // Display the final score
        tvScore.text = "Score: $score"

        // Loop through the ordered questions and display each result
        orderedQuestionsList.forEachIndexed { index, question ->
            val selectedAnswerIndex = selectedAnswers?.get(index) ?: -1
            val correctAnswerIndex = question.correctAnswerIndex

            // Determine if the user's answer is correct or not
            val answerStatus =
                if (selectedAnswerIndex == correctAnswerIndex) "✅ Correct" else "❌ Incorrect"

            // Create and configure TextViews for each question, answer, and result status
            val questionTextView = TextView(this).apply {
                text = "Q${index + 1}: ${question.question}"
                textSize = 20f
                setTextColor(resources.getColor(R.color.black, theme))
            }

            val selectedAnswerTextView = TextView(this).apply {
                text =
                    "Your Answer: ${question.options.getOrNull(selectedAnswerIndex - 1) ?: "N/A"}"
                textSize = 18f
                setTextColor(resources.getColor(R.color.black, theme))
            }

            val correctAnswerTextView = TextView(this).apply {
                text =
                    "Correct Answer: ${question.options.getOrNull(correctAnswerIndex - 1) ?: "N/A"}"
                textSize = 18f
                setTextColor(resources.getColor(R.color.garnet, theme))
            }

            val statusTextView = TextView(this).apply {
                text = answerStatus
                textSize = 18f
                setTextColor(
                    if (selectedAnswerIndex == correctAnswerIndex)
                        resources.getColor(R.color.green, theme)
                    else
                        resources.getColor(R.color.red, theme)
                )
            }

            // Add the created TextViews to the results layout
            linearResults.addView(questionTextView)
            linearResults.addView(selectedAnswerTextView)
            linearResults.addView(correctAnswerTextView)
            linearResults.addView(statusTextView)

            // Add spacing between each question result
            val space = Space(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    20
                )
            }
            linearResults.addView(space)
        }

        // Set up the "OK" button to close the results screen
        btnOK.setOnClickListener {
            finish()
        }
    }
}
