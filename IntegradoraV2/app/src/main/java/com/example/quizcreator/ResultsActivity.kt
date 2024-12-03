package com.example.quizcreator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizcreator.adapter.ResultAdapter
import com.google.firebase.database.*



data class Result(val quizId: String = "", val score: Int = 0, val totalQuestions: Int = 0)

class ResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ResultAdapter
    private val results: MutableList<Result> = mutableListOf()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        // Inicializar RecyclerView y Adapter
        recyclerView = findViewById(R.id.rvResults)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ResultAdapter(results)
        recyclerView.adapter = adapter

        // Configurar Firebase
        database = FirebaseDatabase.getInstance().reference.child("results")
        loadResults()
    }

    private fun loadResults() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                results.clear()
                for (resultSnapshot in snapshot.children) {
                    val result = resultSnapshot.getValue(Result::class.java)
                    result?.let { results.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ResultsActivity, "Error al cargar resultados", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
