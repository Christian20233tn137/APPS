package com.example.quizcreator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizcreator.R
import com.example.quizcreator.models.QuizResult

class QuizResultAdapter(private val resultList: List<QuizResult>) : RecyclerView.Adapter<QuizResultAdapter.QuizResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz_result, parent, false)
        return QuizResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizResultViewHolder, position: Int) {
        val result = resultList[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int = resultList.size

    inner class QuizResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quizIdTextView: TextView = itemView.findViewById(R.id.quizIdTextView)
        private val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)
        private val totalQuestionsTextView: TextView = itemView.findViewById(R.id.totalQuestionsTextView)

        fun bind(quizResult: QuizResult) {
            quizIdTextView.text = quizResult.quizId
            scoreTextView.text = "Puntaje: ${quizResult.score}"
            totalQuestionsTextView.text = "Preguntas: ${quizResult.totalQuestions}"
        }
    }
}
