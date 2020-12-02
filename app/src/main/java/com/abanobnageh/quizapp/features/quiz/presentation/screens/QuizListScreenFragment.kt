package com.abanobnageh.quizapp.features.quiz.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.abanobnageh.quizapp.R
import com.abanobnageh.quizapp.core.di.ViewModelProviderFactory
import com.abanobnageh.quizapp.core.error.ServerError
import com.abanobnageh.quizapp.core.utils.showError
import com.abanobnageh.quizapp.databinding.FragmentQuizListScreenBinding
import com.abanobnageh.quizapp.features.quiz.presentation.adapters.QuizRecyclerViewAdapter
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.QuizListScreenViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizListScreenFragment: DaggerFragment() {
  lateinit var binding: FragmentQuizListScreenBinding
  lateinit var viewModel: QuizListScreenViewModel

  @Inject lateinit var modelFactory: ViewModelProviderFactory

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_list_screen, container, false)
    viewModel = ViewModelProvider(this, modelFactory).get(QuizListScreenViewModel::class.java)

    binding.viewModel = viewModel

    initializeObservers()

    return binding.root
  }

  override fun onResume() {
    super.onResume()
    GlobalScope.launch {
      if (viewModel.isLoading.value == false) {
        binding.quizList.adapter = null
        viewModel.getQuizzesList()
      }
    }
  }

  private fun initializeObservers() {
    viewModel.isLoading.observe(viewLifecycleOwner, {isLoading ->
      if (isLoading) {
        binding.quizProgressBar.visibility = View.VISIBLE
      }
      else {
        binding.quizProgressBar.visibility = View.INVISIBLE
      }
    })

    viewModel.quizResponse.observe(viewLifecycleOwner, {quizResponse ->
      if (quizResponse == null) {
        return@observe
      }

      if (quizResponse.isResponse()) {
        val quizRecyclerViewAdapter: QuizRecyclerViewAdapter = QuizRecyclerViewAdapter(quizResponse.response!!) { quizId ->
          val bundle = bundleOf("quiz_id" to quizId)
          Navigation.findNavController(requireView()).navigate(R.id.action_quizListScreenFragment_to_quizScreenFragment, bundle)
        }
        binding.quizList.adapter = quizRecyclerViewAdapter
      }
      else {
        if (quizResponse.error != null) {
          if (quizResponse.error is ServerError) {
            showError(requireActivity(), (quizResponse.error as ServerError).errorMessage)
          }
          else {
            showError(requireActivity(), getString(R.string.unknown_error_text))
          }
        }
        else {
          showError(requireActivity(), getString(R.string.internet_disabled_error_text))
        }
      }
    })
  }
}