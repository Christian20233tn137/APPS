package com.example.quizcreator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizcreator.adapter.QuizAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.quizcreator.models.Quiz


data class Quiz(val quizId: String = "", val title: String = "")

class MyQuizzesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuizAdapter
    private lateinit var quizzes: MutableList<Quiz>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_quizzes)

        // Inicializar RecyclerView y Adapter
        recyclerView = findViewById(R.id.recyclerViewQuizzes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = QuizAdapter(quizzes) { quiz ->
            val intent = Intent(this, AnswerQuizActivity::class.java)
            intent.putExtra("QUIZ_ID", quiz)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Configurar Firebase
        database = FirebaseDatabase.getInstance().reference.child("quizzes")
        loadQuizzes()
    }

    private fun loadQuizzes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                quizzes.clear()
                for (quizSnapshot in snapshot.children) {
                    val quiz = quizSnapshot.getValue(Quiz::class.java)
                    quiz?.let { quizzes.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyQuizzesActivity, "Error al cargar quizzes", Toast.LENGTH_SHORT).show()
            }
        })
    }
}