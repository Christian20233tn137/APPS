package com.example.quizcreator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizcreator.models.Quiz
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateQuizActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private val questionsList = mutableListOf<Quiz.Question>()

    // Definir las vistas
    private lateinit var editTextQuizTitle: EditText
    private lateinit var editTextQuestion: EditText
    private lateinit var editTextOption1: EditText
    private lateinit var editTextOption2: EditText
    private lateinit var editTextOption3: EditText
    private lateinit var btnAddQuestion: Button
    private lateinit var btnSaveQuiz: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)

        // Inicializar las vistas
        editTextQuizTitle = findViewById(R.id.editTextQuizTitle)
        editTextQuestion = findViewById(R.id.editTextQuestion)
        editTextOption1 = findViewById(R.id.editTextOption1)
        editTextOption2 = findViewById(R.id.editTextOption2)
        editTextOption3 = findViewById(R.id.editTextOption3)
        btnAddQuestion = findViewById(R.id.btnAddQuestion)
        btnSaveQuiz = findViewById(R.id.btnSaveQuiz)

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance().reference

        // Manejo de clics para agregar preguntas
        btnAddQuestion.setOnClickListener {
            addQuestion()
        }

        // Manejo de clics para guardar el quiz
        btnSaveQuiz.setOnClickListener {
            saveQuiz()
        }
    }

    private fun addQuestion() {
        val questionText = editTextQuestion.text.toString()
        val option1 = editTextOption1.text.toString()
        val option2 = editTextOption2.text.toString()
        val option3 = editTextOption3.text.toString()

        if (questionText.isNotBlank() && option1.isNotBlank() && option2.isNotBlank() && option3.isNotBlank()) {
            // Agregar la pregunta a la lista
            val newQuestion = Quiz.Question(questionText, option1, option2, option3)
            questionsList.add(newQuestion)

            // Limpiar los campos para agregar una nueva pregunta
            editTextQuestion.text.clear()
            editTextOption1.text.clear()
            editTextOption2.text.clear()
            editTextOption3.text.clear()

            Toast.makeText(this, "Pregunta añadida", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveQuiz() {
        val quizTitle = editTextQuizTitle.text.toString()

        if (quizTitle.isNotBlank() && questionsList.isNotEmpty()) {
            val quizId = database.push().key ?: return // Genera un ID único para el quiz
            val quiz = Quiz(quizTitle, questionsList.toString())

            // Guardar el quiz en Firebase
            database.child("quizzes").child(quizId).setValue(quiz).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Quiz guardado exitosamente", Toast.LENGTH_SHORT).show()
                    finish() // Finaliza la actividad después de guardar el quiz
                } else {
                    Toast.makeText(this, "Error al guardar el quiz", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Por favor, ingresa un título y agrega al menos una pregunta", Toast.LENGTH_SHORT).show()
        }
    }
}
