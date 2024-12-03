package com.example.quizcreator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizcreator.R
import com.example.quizcreator.Result

class ResultAdapter(
    private val resultList: MutableList<Result>
) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quizTitle: TextView = itemView.findViewById(R.id.quizTitle)
        val quizScore: TextView = itemView.findViewById(R.id.quizScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = resultList[position]
        holder.quizTitle.text = "Quiz ID: ${result.quizId}"
        holder.quizScore.text = "Puntaje: ${result.score}/${result.totalQuestions}"
    }

    override fun getItemCount(): Int = resultList.size
}
