package com.example.mathgame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var answerInput: EditText
    private lateinit var correctAnswerText: TextView
    private lateinit var submitButton: Button
    private lateinit var mainLayout: LinearLayout

    private var currentAnswer = 0
    private var questionCount = 0
    private var score = 0
    private val maxQuestions = 5

    private var state = GameState.ANSWERING

    enum class GameState {
        ANSWERING,
        NEXT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionText = findViewById(R.id.questionText)
        answerInput = findViewById(R.id.answerInput)
        submitButton = findViewById(R.id.submitButton)


        correctAnswerText = findViewById(R.id.correctAnswerText)

        mainLayout = findViewById(R.id.quiz_layout)


        generateQuestion()

        submitButton.setOnClickListener {
            if (state == GameState.ANSWERING) {
                checkAnswer()
            } else {
                questionCount++
                if (questionCount == maxQuestions) {
                    val intent = Intent(this, EndGameActivity::class.java)
                    intent.putExtra("score", score)
                    startActivity(intent)
                    finish()
                } else {
                    resetLayout()
                    generateQuestion()
                }
            }
        }
    }

    private fun generateQuestion() {
        var num1: Int
        var num2: Int
        val operator = if (Random.nextBoolean()) "+" else "-"

        do {
            num1 = Random.nextInt(0, 100)
            num2 = Random.nextInt(0, 100)
        } while (operator == "-" && num1 < num2)

        currentAnswer = if (operator == "+") num1 + num2 else num1 - num2
        questionText.text = "$num1 $operator $num2 = ?"
    }

    private fun checkAnswer() {
        val answerText = answerInput.text.toString()
        if (answerText.isEmpty()) {
            Toast.makeText(this, "Digite uma resposta", Toast.LENGTH_SHORT).show()
            return
        }

        val userAnswer = answerText.toInt()

        if (userAnswer == currentAnswer) {
            score += 20
            mainLayout.setBackgroundColor(Color.argb(64, 0, 255 ,0))
        } else {
            mainLayout.setBackgroundColor(Color.argb(64, 255, 0, 0))
            correctAnswerText.text = "Resposta correta: $currentAnswer"
            correctAnswerText.visibility = TextView.VISIBLE
        }

        answerInput.isEnabled = false
        state = GameState.NEXT

        if (questionCount == maxQuestions - 1) {
            submitButton.text = "Finalizar"
        } else {
            submitButton.text = "Próxima"
        }
    }

    private fun resetLayout() {
        mainLayout.setBackgroundColor(Color.WHITE)
        answerInput.text.clear()
        answerInput.isEnabled = true
        correctAnswerText.text = ""
        correctAnswerText.visibility = TextView.GONE
        submitButton.text = "Responder"
        state = GameState.ANSWERING
    }
}