package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class ResultsActivity : ComponentActivity() {

    // Declare UI elements
    private lateinit var tvTitle: TextView // Title of the results screen
    private lateinit var tvResultsHeader: TextView // Header text displaying "Results"
    private lateinit var linearResults: LinearLayout // Layout to dynamically display question results
    private lateinit var tvScore: TextView // TextView to display the final score
    private lateinit var btnOK: Button // Button to acknowledge results and close screen

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_screen) // Load the results screen layout

        // Link UI elements with their IDs in the layout file
        tvTitle = findViewById(R.id.tvTitle)
        tvResultsHeader = findViewById(R.id.tvResultsHeader)
        linearResults = findViewById(R.id.linearResults)
        tvScore = findViewById(R.id.tvScore)
        btnOK = findViewById(R.id.btnOK)

        // Retrieve quiz data passed from the previous activity
        val questionsList: List<Question>? = intent.getParcelableArrayListExtra("questions") // Full list of quiz questions
        val score = intent.getIntExtra("score", 0) // Retrieve the user's final score
        val selectedAnswers = intent.getIntArrayExtra("selectedAnswers") // Array of the user's selected answers
        val selectedQuestions = intent.getIntegerArrayListExtra("selectedQuestions") // Order in which questions were presented

        // Reorder the questions list based on the order they were asked
        val orderedQuestionsList = selectedQuestions?.mapNotNull { index ->
            questionsList?.getOrNull(index) // Get the question at the given index
        } ?: emptyList() // If no selection order exists, return an empty list

        // Display the final score in the TextView
        tvScore.text = "Score: $score"

        // Loop through the ordered list of questions and display results dynamically
        orderedQuestionsList.forEachIndexed { index, question ->
            val selectedAnswerIndex = selectedAnswers?.get(index) ?: -1 // Get the user's selected answer index
            val correctAnswerIndex = question.correctAnswerIndex // Get the correct answer index

            // Determine if the selected answer was correct or incorrect
            val answerStatus = if (selectedAnswerIndex == correctAnswerIndex) "✅ Correct" else "❌ Incorrect"

            // Create a TextView for the question text
            val questionTextView = TextView(this).apply {
                text = "Q${index + 1}: ${question.question}" // Display question number and text
                textSize = 20f // Set text size
                setTextColor(resources.getColor(R.color.black, theme)) // Set text color
            }

            // Create a TextView for the user's selected answer
            val selectedAnswerTextView = TextView(this).apply {
                text = "Your Answer: ${question.options.getOrNull(selectedAnswerIndex - 1) ?: "N/A"}"
                textSize = 18f // Set text size
                setTextColor(resources.getColor(R.color.black, theme)) // Set text color
            }

            // Create a TextView for the correct answer
            val correctAnswerTextView = TextView(this).apply {
                text = "Correct Answer: ${question.options.getOrNull(correctAnswerIndex - 1) ?: "N/A"}"
                textSize = 18f // Set text size
                setTextColor(resources.getColor(R.color.garnet, theme)) // Set color to differentiate the correct answer
            }

            // Create a TextView to show whether the user got the answer right or wrong
            val statusTextView = TextView(this).apply {
                text = answerStatus
                textSize = 18f // Set text size
                setTextColor(
                    if (selectedAnswerIndex == correctAnswerIndex)
                        resources.getColor(R.color.green, theme) // Green for correct
                    else
                        resources.getColor(R.color.red, theme) // Red for incorrect
                )
            }

            // Add the created TextViews to the results layout
            linearResults.addView(questionTextView)
            linearResults.addView(selectedAnswerTextView)
            linearResults.addView(correctAnswerTextView)
            linearResults.addView(statusTextView)

            // Add spacing between questions for better readability
            val space = Space(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    20 // Space height
                )
            }
            linearResults.addView(space)
        }

        // Set up the "OK" button to close the results screen when clicked
        btnOK.setOnClickListener {
            finish() // Close this activity and return to the previous screen
        }
    }
}
