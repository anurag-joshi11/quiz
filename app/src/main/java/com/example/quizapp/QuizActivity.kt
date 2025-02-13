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

    private lateinit var tvQuestion: TextView
    private lateinit var btnOptionA: Button
    private lateinit var btnOptionB: Button
    private lateinit var btnOptionC: Button
    private lateinit var tvTimer: TextView

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

    private val correctAnswers = arrayOf(2, 3, 1, 1, 2, 2, 1, 3, 1, 1, 3, 2, 1, 3, 3, 1, 2, 1, 2, 3)

    private var score = 0
    private var questionIndex = 0
    private lateinit var selectedQuestions: List<Int>
    private var selectedAnswers = IntArray(20)

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_array)

        tvQuestion = findViewById(R.id.tvQuestion)
        btnOptionA = findViewById(R.id.btnOptionA)
        btnOptionB = findViewById(R.id.btnOptionB)
        btnOptionC = findViewById(R.id.btnOptionC)
        tvTimer = findViewById(R.id.tvTimer)

        // Randomly select 20 unique questions
        selectedQuestions = questions.indices.shuffled().take(4)
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

        changeBackgroundColor()
        startTimer()
    }

    private fun checkAnswer(selected: Int) {
        countDownTimer?.cancel()  // Stop the timer when the user answers

        val qIndex = selectedQuestions[questionIndex]
        selectedAnswers[questionIndex] = selected

        if (selected == correctAnswers[qIndex]) {
            score++
        }

        if (questionIndex < selectedQuestions.size - 1) {
            questionIndex++
            loadQuestion()
        } else {
            showResults()
        }
    }

    private fun changeBackgroundColor() {
        val colors = arrayOf("#FF5733", "#33FF57", "#3357FF", "#FF33A6", "#FFD700")
        val newColor = Color.parseColor(colors[questionIndex % colors.size])
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setBackgroundColor(newColor)
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Time left: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                // Ensure that the questionIndex does not increment beyond the limit
                if (questionIndex < selectedQuestions.size - 1) {
                    questionIndex++
                    loadQuestion()
                } else {
                    showResults()
                }
            }
        }.start()
    }

    private fun showResults() {
        // Prepare the list of questions with options and correct answers
        val questionsList = questions.indices.map { i ->
            Question(
                question = questions[i],
                options = options[i].toList(),
                correctAnswerIndex = correctAnswers[i]
            )
        }

        val intent = Intent(this, ResultsActivity::class.java).apply {
            // Passing the list of Question objects as Parcelable
            putParcelableArrayListExtra("questions", ArrayList(questionsList))
            putExtra("score", score)
            putIntegerArrayListExtra("selectedQuestions", ArrayList(selectedQuestions))
            putExtra("selectedAnswers", selectedAnswers)
        }

        startActivity(intent)
        finish()
    }
}
