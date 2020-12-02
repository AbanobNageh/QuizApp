package com.abanobnageh.quizapp.features.quiz.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abanobnageh.quizapp.databinding.ItemQuizBinding
import com.abanobnageh.quizapp.features.quiz.domain.entities.Quiz

class QuizRecyclerViewAdapter(var quizList: ArrayList<Quiz>, val onItemSelected: (quizId: Int) -> Unit): RecyclerView.Adapter<QuizRecyclerViewAdapter.QuizViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
    val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
    val stadiumBinding: ItemQuizBinding = ItemQuizBinding.inflate(layoutInflater, parent, false)
    return QuizViewHolder(stadiumBinding, parent.context)
  }

  override fun getItemCount(): Int {
    return quizList.size
  }

  override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
    holder.bind(quizList[position])
  }

  inner class QuizViewHolder(var binding: ItemQuizBinding, var context: Context): RecyclerView.ViewHolder(binding.root) {
    fun bind(quiz: Quiz) {
      binding.titleTextView.text = quiz.name
      binding.ratingBar.rating = quiz.rating.toFloat()
      binding.descriptionTextView.text = quiz.description

      binding.root.setOnClickListener {
        onItemSelected(quiz.id)
      }
    }
  }
}