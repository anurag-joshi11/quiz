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

/**
 * Activity that manages the quiz gameplay, including displaying questions,
 * handling answers, and showing results.
 */
class QuizActivity : ComponentActivity() {

    // UI elements
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

    // Answer options corresponding to each question
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

    private val correctAnswers = arrayOf(
        2, // Paris
        3, // Mars
        1, // Harper Lee
        1, // Pacific
        2, // Amazon
        2, // 8
        1, // Brazil
        3, // Leonardo da Vinci
        1, // Au
        1, // Africa
        3, // Carbon Dioxide
        2, // Einstein
        1, // Beaver
        3, // Hydrogen
        3, // Diamond
        1, // Alexander Fleming
        2, // Jupiter
        1, // 0°C
        2, // Neil Armstrong
        3  // Football
    )

    private var score = 0
    private var questionIndex = 0
    private lateinit var selectedQuestions: List<Int>
    private var selectedAnswers = IntArray(20)

    private var countDownTimer: CountDownTimer? = null // Countdown timer for each question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_array)

        // Bind UI elements to their XML counterparts
        tvQuestion = findViewById(R.id.tvQuestion)
        btnOptionA = findViewById(R.id.btnOptionA)
        btnOptionB = findViewById(R.id.btnOptionB)
        btnOptionC = findViewById(R.id.btnOptionC)
        tvTimer = findViewById(R.id.tvTimer)

        // Randomly select a set of unique questions (4 out of 20)
        selectedQuestions = questions.indices.shuffled().take(4)

        // Load the first question
        loadQuestion()

        // Set up button click listeners for answer options
        btnOptionA.setOnClickListener { checkAnswer(1) }
        btnOptionB.setOnClickListener { checkAnswer(2) }
        btnOptionC.setOnClickListener { checkAnswer(3) }
    }

    /**
     * Loads and displays the next question along with the options.
     * Also starts the countdown timer.
     */
    @SuppressLint("SetTextI18n")
    private fun loadQuestion() {
        // If all questions have been answered, show the results
        if (questionIndex >= selectedQuestions.size) {
            showResults()
            return
        }

        val qIndex = selectedQuestions[questionIndex]
        tvQuestion.text = "Q${questionIndex + 1}: ${questions[qIndex]}"
        btnOptionA.text = options[qIndex][0]
        btnOptionB.text = options[qIndex][1]
        btnOptionC.text = options[qIndex][2]

        changeBackgroundColor() // Change background color for the current question
        startTimer() // Start the countdown timer for this question
    }

    /**
     * Checks the user's answer, updates the score, and moves to the next question.
     * @param selected The selected answer index (1, 2, or 3).
     */
    private fun checkAnswer(selected: Int) {
        countDownTimer?.cancel() // Stop the timer when an answer is selected

        val qIndex = selectedQuestions[questionIndex]
        selectedAnswers[questionIndex] = selected

        // If correct, increase the score
        if (selected == correctAnswers[qIndex]) {
            score++
        }

        // Move to the next question or show results if it's the last one
        if (questionIndex < selectedQuestions.size - 1) {
            questionIndex++
            loadQuestion()
        } else {
            showResults()
        }
    }

    /**
     * Changes the background color to add visual variety.
     */
    private fun changeBackgroundColor() {
        val colors = arrayOf("#2F63A7", "#AEA052", "#126659", "#783E27", "#540833")
        val newColor = Color.parseColor(colors[questionIndex % colors.size])
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setBackgroundColor(newColor) // Apply new color to background
    }

    /**
     * Starts a 10-second countdown timer for each question.
     */
    private fun startTimer() {
        countDownTimer?.cancel() // Cancel any existing timer

        countDownTimer = object : CountDownTimer(10000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Time left: ${millisUntilFinished / 1000}s" // Update timer UI
            }

            override fun onFinish() {
                // Move to the next question or show results when time runs out
                if (questionIndex < selectedQuestions.size - 1) {
                    questionIndex++
                    loadQuestion()
                } else {
                    showResults()
                }
            }
        }.start()
    }

    /**
     * Navigates to the results screen and passes the player's score and answers.
     */
    private fun showResults() {
        // Convert question data to Question objects for passing to the results screen
        val questionsList = questions.indices.map { i ->
            Question(
                question = questions[i],
                options = options[i].toList(),
                correctAnswerIndex = correctAnswers[i]
            )
        }

        // Create an intent to navigate to the results screen
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putParcelableArrayListExtra("questions", ArrayList(questionsList))
            putExtra("score", score) // Pass the score
            putIntegerArrayListExtra("selectedQuestions", ArrayList(selectedQuestions))
            putExtra("selectedAnswers", selectedAnswers) // Pass selected answers
        }

        // Save the score to shared preferences for future use
        val sharedPreferences = getSharedPreferences("QuizGamePrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("LAST_SCORE", score)
        editor.apply()

        startActivity(intent)
        finish()
    }
}
