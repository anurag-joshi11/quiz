package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class QuizActivity : ComponentActivity() {

    // UI elements for displaying the question and answer options
    private lateinit var tvQuestion: TextView
    private lateinit var btnOptionA: Button
    private lateinit var btnOptionB: Button
    private lateinit var btnOptionC: Button
    private lateinit var tvTimer: TextView

    // Array of quiz questions
    private val questions = arrayOf(
        "What is the capital of France?",
        "Which planet is known as the Red Planet?",
        "Who wrote 'To Kill a Mockingbird'?",
        "What is the largest ocean on Earth?",
        "Which is the longest river in the world?",
        "What is the square root of 64?",
        "Which country hosted the 2016 Olympics?",
        "Who painted the Mona Lisa?",
        "What is the chemical symbol for Gold?",
        "Which continent has the most countries?",
        "Which gas do plants absorb from the atmosphere?",
        "Who developed the theory of relativity?",
        "What is the national animal of Canada?",
        "Which element has the atomic number 1?",
        "What is the hardest natural substance on Earth?",
        "Who discovered penicillin?",
        "Which is the largest planet in our solar system?",
        "What is the freezing point of water in Celsius?",
        "Who was the first person to step on the moon?",
        "Which sport is known as the 'king of sports'?"
    )

    // Multiple-choice answer options for each question
    private val options = arrayOf(
        arrayOf("London", "Paris", "Berlin"),
        arrayOf("Venus", "Jupiter", "Mars"),
        arrayOf("Harper Lee", "J.K. Rowling", "Ernest Hemingway"),
        arrayOf("Pacific", "Atlantic", "Indian"),
        arrayOf("Nile", "Amazon", "Yangtze"),
        arrayOf("6", "8", "10"),
        arrayOf("Brazil", "China", "USA"),
        arrayOf("Pablo Picasso", "Vincent van Gogh", "Leonardo da Vinci"),
        arrayOf("Au", "Ag", "Fe"),
        arrayOf("Africa", "Asia", "Europe"),
        arrayOf("Oxygen", "Nitrogen", "Carbon Dioxide"),
        arrayOf("Newton", "Einstein", "Tesla"),
        arrayOf("Beaver", "Eagle", "Moose"),
        arrayOf("Helium", "Oxygen", "Hydrogen"),
        arrayOf("Gold", "Platinum", "Diamond"),
        arrayOf("Alexander Fleming", "Marie Curie", "Louis Pasteur"),
        arrayOf("Saturn", "Jupiter", "Neptune"),
        arrayOf("0°C", "-10°C", "5°C"),
        arrayOf("Buzz Aldrin", "Neil Armstrong", "Yuri Gagarin"),
        arrayOf("Football", "Basketball", "Cricket")
    )

    // The correct answer indices for each question (1-based indexing)
    private val correctAnswers = arrayOf(2, 3, 1, 1, 2, 2, 1, 3, 1, 1, 3, 2, 1, 3, 3, 1, 2, 1, 2, 3)

    private var score = 0 // Stores the player's score
    private var questionIndex = 0 // Keeps track of the current question
    private lateinit var selectedQuestions: List<Int> // Stores randomly selected question indices
    private var selectedAnswers = IntArray(20) // Stores the player's selected answers

    private var countDownTimer: CountDownTimer? = null // Countdown timer for each question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_array)

        // Link UI elements with their XML IDs
        tvQuestion = findViewById(R.id.tvQuestion)
        btnOptionA = findViewById(R.id.btnOptionA)
        btnOptionB = findViewById(R.id.btnOptionB)
        btnOptionC = findViewById(R.id.btnOptionC)
        tvTimer = findViewById(R.id.tvTimer)

        // Randomly select a set of unique questions (choosing 4 out of 20 for this session)
        selectedQuestions = questions.indices.shuffled().take(4)

        // Load the first question
        loadQuestion()

        // Set click listeners for answer buttons
        btnOptionA.setOnClickListener { checkAnswer(1) }
        btnOptionB.setOnClickListener { checkAnswer(2) }
        btnOptionC.setOnClickListener { checkAnswer(3) }
    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestion() {
        // If all selected questions have been answered, show the results
        if (questionIndex >= selectedQuestions.size) {
            showResults()
            return
        }

        val qIndex = selectedQuestions[questionIndex] // Get the index of the current question
        tvQuestion.text = "Q${questionIndex + 1}: ${questions[qIndex]}" // Display the question text
        btnOptionA.text = options[qIndex][0] // Set text for option A
        btnOptionB.text = options[qIndex][1] // Set text for option B
        btnOptionC.text = options[qIndex][2] // Set text for option C

        changeBackgroundColor() // Change background color for each question
        startTimer() // Start the countdown timer
    }

    private fun checkAnswer(selected: Int) {
        countDownTimer?.cancel() // Stop the timer when the user answers

        val qIndex = selectedQuestions[questionIndex] // Get the current question index
        selectedAnswers[questionIndex] = selected // Store the user's answer

        // Check if the selected answer matches the correct answer
        if (selected == correctAnswers[qIndex]) {
            score++ // Increase score for correct answers
        }

        // Move to the next question or show results if it was the last one
        if (questionIndex < selectedQuestions.size - 1) {
            questionIndex++
            loadQuestion()
        } else {
            showResults()
        }
    }

    // Changes the background color dynamically to add variety to each question
    private fun changeBackgroundColor() {
        val colors = arrayOf("#FF9F80", "#80FF99", "#80A6FF", "#FF80C2", "#FFE066")
        val newColor = Color.parseColor(colors[questionIndex % colors.size]) // Cycle through colors
        val rootView = findViewById<View>(android.R.id.content) // Get root view
        rootView.setBackgroundColor(newColor) // Apply new background color
    }

    // Starts a 10-second countdown timer for each question
    private fun startTimer() {
        countDownTimer?.cancel() // Cancel any existing timer

        countDownTimer = object : CountDownTimer(10000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Time left: ${millisUntilFinished / 1000}s" // Update timer UI
            }

            override fun onFinish() {
                // Move to the next question when time runs out
                if (questionIndex < selectedQuestions.size - 1) {
                    questionIndex++
                    loadQuestion()
                } else {
                    showResults()
                }
            }
        }.start()
    }

    // Navigates to the results screen, passing the player's answers and score
    private fun showResults() {
        // Convert the raw question data into Question objects
        val questionsList = questions.indices.map { i ->
            Question(
                question = questions[i],
                options = options[i].toList(),
                correctAnswerIndex = correctAnswers[i]
            )
        }

        // Create an intent to switch to ResultsActivity and pass the required data
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putParcelableArrayListExtra("questions", ArrayList(questionsList)) // Pass questions
            putExtra("score", score) // Pass final score
            putIntegerArrayListExtra("selectedQuestions", ArrayList(selectedQuestions)) // Pass selected question indices
            putExtra("selectedAnswers", selectedAnswers) // Pass selected answers
        }

        // Save the last score in shared preferences
        val sharedPreferences = getSharedPreferences("QuizGamePrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("LAST_SCORE", score)
        editor.apply()

        startActivity(intent) // Start results activity
        finish() // Close the quiz activity
    }
}
