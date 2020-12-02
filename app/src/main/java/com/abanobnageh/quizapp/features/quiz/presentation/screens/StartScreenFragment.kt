package com.abanobnageh.quizapp.features.quiz.presentation.screens

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.abanobnageh.quizapp.R
import com.abanobnageh.quizapp.core.di.ViewModelProviderFactory
import com.abanobnageh.quizapp.core.error.EmptyPlayerNameError
import com.abanobnageh.quizapp.databinding.FragmentStartScreenBinding
import com.abanobnageh.quizapp.features.quiz.MainActivityViewModel
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.StartScreenViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_start_screen.view.*
import javax.inject.Inject


class StartScreenFragment : DaggerFragment() {
  lateinit var binding: FragmentStartScreenBinding
  lateinit var viewModel: StartScreenViewModel
  lateinit var activityViewModel: MainActivityViewModel

  @Inject lateinit var modelFactory: ViewModelProviderFactory

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_screen, container, false)
    viewModel = ViewModelProvider(this, modelFactory).get(StartScreenViewModel::class.java)
    activityViewModel = ViewModelProvider(requireActivity(), modelFactory).get(MainActivityViewModel::class.java)

    binding.viewModel = viewModel

    initializeObservers()
    initializeListeners()

    return binding.root
  }

  override fun onResume() {
    super.onResume()
  }

  private fun initializeObservers() {
    viewModel.nameErrorEnabled.observe(viewLifecycleOwner, { isEnabled ->
      binding.nameTextField.isErrorEnabled = isEnabled
    })

    viewModel.startGameResponse.observe(viewLifecycleOwner, { response ->
      if (response == null) {
        return@observe
      }

      if (response.isResponse()) {
        // close soft keyboard
        val imm: InputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        activityViewModel.userName = response.response!!
        Navigation.findNavController(requireView()).navigate(R.id.action_startScreenFragment_to_quizListScreenFragment)
      } else {
        if (response.error is EmptyPlayerNameError) {
          binding.nameTextField.error = getString(R.string.player_name_empty_error)
        }
      }
    })
  }

  private fun initializeListeners() {
    binding.nameTextField.nameEditText.doOnTextChanged { text, start, before, count ->
      if (binding.nameTextField.nameEditText.text?.length!! < 1) {
        binding.nameTextField.isErrorEnabled = true
        binding.nameTextField.error = getString(R.string.player_name_empty_error)
      }
      else {
        binding.nameTextField.isErrorEnabled = false
      }
    }
  }
}