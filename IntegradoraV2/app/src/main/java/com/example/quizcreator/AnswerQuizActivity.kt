package com.example.quizcreator

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizcreator.adapter.QuizAdapter
import com.example.quizcreator.models.Quiz
import com.example.quizcreator.models.QuizResult
import com.google.firebase.database.*

class AnswerQuizActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuizAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var btnSubmit: Button
    private lateinit var tvQuestion: TextView
    private var questionList: MutableList<Quiz.Question> = mutableListOf()
    private var currentQuestionIndex = 0
    private var score = 0
    private var totalQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_quiz)

        // Inicializar vistas
        recyclerView = findViewById(R.id.rvOptions)
        progressBar = findViewById(R.id.progressBar)
        btnSubmit = findViewById(R.id.btnSubmit)
        tvQuestion = findViewById(R.id.tvQuestion)

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance().reference

        // Cargar preguntas desde Firebase
        val quizId = intent.getStringExtra("quizId") ?: return
        loadQuiz(quizId)

        // Accionar al hacer clic en el botón de enviar
        btnSubmit.setOnClickListener {
            submitAnswer()
        }
    }

    private fun loadQuiz(quizId: String) {
        database.child("quizzes").child(quizId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val quiz = snapshot.getValue(Quiz::class.java)
                quiz?.let {
                    questionList = it.questions.toMutableList()
                    totalQuestions = questionList.size
                    progressBar.max = totalQuestions
                    showQuestion()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AnswerQuizActivity, "Error al cargar el quiz", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showQuestion() {
        if (currentQuestionIndex < totalQuestions) {
            val currentQuestion = questionList[currentQuestionIndex]
            tvQuestion.text = currentQuestion.questionText

            adapter = QuizAdapter(currentQuestion.options) { selectedOption ->
                // Actualiza el comportamiento al seleccionar una opción
            }
            recyclerView.adapter = adapter

            progressBar.progress = currentQuestionIndex + 1
        } else {
            saveResult()
        }
    }


    private fun checkAnswer(selectedOption: String, correctOption: String) {
        if (selectedOption == correctOption) {
            score++
        }

        currentQuestionIndex++
        showQuestion()
    }

    private fun submitAnswer() {
        val selectedOption = adapter.getSelectedOption()

        if (selectedOption.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor selecciona una opción", Toast.LENGTH_SHORT).show()
        } else {
            checkAnswer(selectedOption, questionList[currentQuestionIndex].correctOption)
        }
    }


    private fun saveResult() {
        val quizId = intent.getStringExtra("quizId") ?: return
        val quizResult = QuizResult(quizId, score, totalQuestions)

        // Guardar el resultado en Firebase
        database.child("results").push().setValue(quizResult).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Resultado guardado con éxito", Toast.LENGTH_SHORT).show()
                finish()  // Cerrar la actividad al guardar el resultado
            } else {
                Toast.makeText(this, "Error al guardar el resultado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
