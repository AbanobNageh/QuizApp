package com.abanobnageh.quizapp.features.quiz.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.abanobnageh.quizapp.R
import com.abanobnageh.quizapp.core.di.ViewModelProviderFactory
import com.abanobnageh.quizapp.databinding.FragmentEndScreenBinding
import com.abanobnageh.quizapp.features.quiz.MainActivityViewModel
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.EndScreenViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EndScreenFragment: DaggerFragment() {
  lateinit var binding: FragmentEndScreenBinding
  lateinit var viewModel: EndScreenViewModel
  lateinit var activityViewModel: MainActivityViewModel

  @Inject lateinit var modelFactory: ViewModelProviderFactory

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_end_screen, container, false)
    viewModel = ViewModelProvider(this, modelFactory).get(EndScreenViewModel::class.java)
    activityViewModel = ViewModelProvider(requireActivity(), modelFactory).get(MainActivityViewModel::class.java)

    binding.viewModel = viewModel

    val correctAnswers: Int = arguments?.getInt("correct_answer")!!
    val totalQuestions: Int = arguments?.getInt("total_questions")!!
    val userName: String = activityViewModel.userName

    binding.userNameTextView.text = userName
    binding.scoreTextView.text = getString(R.string.score_text, correctAnswers, totalQuestions)

    initializeListeners()

    return binding.root
  }

  fun initializeListeners() {
    binding.tryQuizzesButton.setOnClickListener {
      Navigation.findNavController(requireView()).navigate(R.id.action_endScreenFragment_to_quizListScreenFragment)
    }
  }
}