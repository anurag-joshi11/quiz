package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class QuizActivity : ComponentActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var btnOptionA: Button
    private lateinit var btnOptionB: Button
    private lateinit var btnOptionC: Button
    private lateinit var tvTimer: TextView // Added for Timer Display

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

    private val options = arrayOf(
        arrayOf("Paris", "London", "Berlin"),
        arrayOf("Mars", "Jupiter", "Venus"),
        arrayOf("Harper Lee", "J.K. Rowling", "Ernest Hemingway"),
        arrayOf("Pacific", "Atlantic", "Indian"),
        arrayOf("Nile", "Amazon", "Yangtze"),
        arrayOf("6", "8", "10"),
        arrayOf("Brazil", "China", "USA"),
        arrayOf("Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh"),
        arrayOf("Au", "Ag", "Fe"),
        arrayOf("Africa", "Asia", "Europe"),
        arrayOf("Oxygen", "Carbon Dioxide", "Nitrogen"),
        arrayOf("Newton", "Einstein", "Tesla"),
        arrayOf("Beaver", "Eagle", "Moose"),
        arrayOf("Hydrogen", "Helium", "Oxygen"),
        arrayOf("Diamond", "Gold", "Platinum"),
        arrayOf("Alexander Fleming", "Marie Curie", "Louis Pasteur"),
        arrayOf("Jupiter", "Saturn", "Neptune"),
        arrayOf("0°C", "-10°C", "5°C"),
        arrayOf("Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin"),
        arrayOf("Football", "Basketball", "Cricket")
    )

    private val correctAnswers = arrayOf(1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 2, 1, 1, 3, 1, 1, 1, 1, 3)

    private var score = 0
    private var questionIndex = 0
    private lateinit var selectedQuestions: List<Int>
    private var countDownTimer: CountDownTimer? = null // To manage the countdown timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_array)

        tvQuestion = findViewById(R.id.tvQuestion)
        btnOptionA = findViewById(R.id.btnOptionA)
        btnOptionB = findViewById(R.id.btnOptionB)
        btnOptionC = findViewById(R.id.btnOptionC)
        tvTimer = findViewById(R.id.tvTimer) // Find Timer TextView

        // Randomly select 20 unique questions
        selectedQuestions = questions.indices.shuffled().take(20)
        loadQuestion()

        btnOptionA.setOnClickListener { checkAnswer(1) }
        btnOptionB.setOnClickListener { checkAnswer(2) }
        btnOptionC.setOnClickListener { checkAnswer(3) }
    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestion() {
        if (questionIndex >= selectedQuestions.size) {
            showResults()
            return
        }

        val qIndex = selectedQuestions[questionIndex]
        tvQuestion.text = "Q${questionIndex + 1}: ${questions[qIndex]}"
        btnOptionA.text = options[qIndex][0]
        btnOptionB.text = options[qIndex][1]
        btnOptionC.text = options[qIndex][2]

        changeBackgroundColor() // Update background color
        startTimer() // Start or reset the timer for each new question
    }

    private fun checkAnswer(selected: Int) {
        val qIndex = selectedQuestions[questionIndex]
        if (selected == correctAnswers[qIndex]) {
            score++
        }
        questionIndex++
        loadQuestion()
    }

    private fun changeBackgroundColor() {
        val colors = arrayOf("#FF5733", "#33FF57", "#3357FF", "#FF33A6", "#FFD700")
        val newColor = Color.parseColor(colors[questionIndex % colors.size])
        Log.d("QuizActivity", "Background color: $newColor") // Debugging log
        val rootView = findViewById<View>(android.R.id.content) // Get root view
        rootView.setBackgroundColor(newColor)
    }

    private fun startTimer() {
        // Cancel the previous timer if it was running
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(10000, 1000) { // 10 seconds countdown
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Time left: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                questionIndex++
                loadQuestion() // Move to the next question when time is up
            }
        }.start()
    }

    private fun showResults() {
        val intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra("score", score)
        intent.putIntegerArrayListExtra("selectedQuestions", ArrayList(selectedQuestions))
        startActivity(intent)
        finish()
    }
}