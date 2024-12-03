package com.example.quizcreator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Acceder a los botones usando findViewById
        val btnCreateQuiz: Button = findViewById(R.id.btnCreateQuiz)
        val btnAnswerQuiz: Button = findViewById(R.id.btnAnswerQuiz)
        val btnViewResults: Button = findViewById(R.id.btnViewResults)
        val btnMyQuizzes: Button = findViewById(R.id.btnMyQuizzes)

        // Configurar los clics de los botones
        btnCreateQuiz.setOnClickListener {
            val intent = Intent(this, CreateQuizActivity::class.java)
            startActivity(intent)
        }

        btnAnswerQuiz.setOnClickListener {
            val intent = Intent(this, AnswerQuizActivity::class.java)
            startActivity(intent)
        }

        btnViewResults.setOnClickListener {
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }

        btnMyQuizzes.setOnClickListener {
            val intent = Intent(this, MyQuizzesActivity::class.java)
            startActivity(intent)
        }
    }
}
