package com.example.quizcreator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizcreator.R
import com.example.quizcreator.models.Quiz

class QuizAdapter(
    private val options: MutableList<Quiz>,
    private val onOptionSelected: (String) -> Unit
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    private var selectedOption: String? = null

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroupOptions)

        fun bind(options: List<String>) {
            radioGroup.removeAllViews() // Asegúrate de limpiar las opciones previas
            for (option in options) {
                val radioButton = RadioButton(itemView.context).apply {
                    text = option
                    isChecked = option == selectedOption
                }
                radioButton.setOnClickListener {
                    selectedOption = option
                    onOptionSelected(option) // Informar la selección
                }
                radioGroup.addView(radioButton)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(options)
    }

    override fun getItemCount() = 1 // Mostramos todas las opciones dentro de un único RadioGroup

    fun getSelectedOption(): String? {
        return selectedOption
    }
}
