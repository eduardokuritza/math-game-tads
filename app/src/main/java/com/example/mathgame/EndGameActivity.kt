package com.example.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EndGameActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val score = intent.getIntExtra("score", 0)
        val scoreText = findViewById<TextView>(R.id.finalScore)
        val restartButton = findViewById<Button>(R.id.restartButton)

        scoreText.text = "Sua nota: $score"

        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}