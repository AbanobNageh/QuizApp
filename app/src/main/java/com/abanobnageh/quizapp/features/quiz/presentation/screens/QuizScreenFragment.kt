package com.abanobnageh.quizapp.features.quiz.presentation.screens

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.abanobnageh.quizapp.R
import com.abanobnageh.quizapp.core.di.ViewModelProviderFactory
import com.abanobnageh.quizapp.core.error.ServerError
import com.abanobnageh.quizapp.core.utils.showError
import com.abanobnageh.quizapp.databinding.FragmentQuizScreenBinding
import com.abanobnageh.quizapp.features.quiz.MainActivityViewModel
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.QuizScreenViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizScreenFragment: DaggerFragment() {
  lateinit var binding: FragmentQuizScreenBinding
  lateinit var viewModel: QuizScreenViewModel
  lateinit var activityViewModel: MainActivityViewModel

  @Inject lateinit var modelFactory: ViewModelProviderFactory

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_screen, container, false)
    viewModel = ViewModelProvider(this, modelFactory).get(QuizScreenViewModel::class.java)
    activityViewModel = ViewModelProvider(requireActivity(), modelFactory).get(MainActivityViewModel::class.java)

    binding.viewModel = viewModel

    initializeObservers()
    initializeListeners()

    return binding.root
  }

  override fun onResume() {
    super.onResume()
    val quizId: Int = arguments?.getInt("quiz_id")!!
    GlobalScope.launch {
      if (viewModel.isLoading.value == false && viewModel.quizResponse.value == null) {
        binding.questionDataLayout.visibility = View.INVISIBLE
        binding.answersLayout.visibility = View.INVISIBLE
        binding.submitButton.visibility = View.INVISIBLE

        viewModel.getQuiz(quizId)
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
        binding.questionDataLayout.visibility = View.VISIBLE
        binding.answersLayout.visibility = View.VISIBLE
        binding.submitButton.visibility = View.VISIBLE
        setCurrentQuestionData()
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

  private fun setCurrentQuestionData() {
    val questionText = viewModel.getCurrentQuestion()
    val questionAnswers = viewModel.getCurrentQuestionAnswers()
    val currentQuestionImage = viewModel.getCurrentQuestionImage()
    val questionCount = viewModel.getQuestionCount()

    if (questionCount != null) {
      // set question progress bae
      binding.questionProgressBar.max = questionCount
      binding.questionProgressBar.progress = viewModel.currentQuestionIndex + 1

      // set question progress
      binding.questionPregressText.text = getString(R.string.question_progress_text, viewModel.currentQuestionIndex + 1, questionCount)
    }

    if (questionText != null) {
      binding.questionTextView.text = questionText
    }

    if (questionAnswers != null) {
      binding.firstAnswerButton.text = questionAnswers[0]
      binding.secondAnswerButton.text = questionAnswers[1]
      binding.thirdAnswerButton.text = questionAnswers[2]
      binding.forthAnswerButton.text = questionAnswers[3]
    }

    if (currentQuestionImage != null) {
      binding.imageProgressBar.visibility = View.VISIBLE

      Glide.with(requireContext())
        .load(getString(R.string.quiz_api_url) + currentQuestionImage.substring(7))
        .listener(object: RequestListener<Drawable> {
          override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            return false
          }

          override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            binding.imageProgressBar.visibility = View.INVISIBLE
            return false
          }

        })
        .into(binding.questionImageView)
    }

    println("---------- has next question = ${viewModel.hasNextQuestion()} -----------")
    if (viewModel.hasNextQuestion() != null && !viewModel.hasNextQuestion()!!) {
      binding.submitButton.text = getString(R.string.finish_button_text)
    }

    binding.submitButton.isEnabled = false
    resetAnswerButtonsSelectingState()
    setAnswersButtonsState(enabled = true)
  }

  private fun initializeListeners() {
    binding.firstAnswerButton.setOnClickListener {
      selectAnswer(1)
    }
    binding.secondAnswerButton.setOnClickListener {
      selectAnswer(2)
    }
    binding.thirdAnswerButton.setOnClickListener {
      selectAnswer(3)
    }
    binding.forthAnswerButton.setOnClickListener {
      selectAnswer(4)
    }
    binding.submitButton.setOnClickListener {
      val hasNextQuestion = viewModel.hasNextQuestion()

      if (hasNextQuestion != null) {
        if (hasNextQuestion) {
          viewModel.currentQuestionIndex++
          setCurrentQuestionData()
        }
        else {
          val bundle = bundleOf("correct_answer" to viewModel.correctQuestionCount, "total_questions" to viewModel.getQuestionCount())
          Navigation.findNavController(requireView()).navigate(R.id.action_quizScreenFragment_to_endScreenFragment, bundle)
        }
      }
    }
  }

  private fun selectAnswer(selectedAnswer: Int) {
    val correctAnswer = viewModel.getCurrentQuestionCorrectAnswer()

    if (correctAnswer != null) {
      if (selectedAnswer == correctAnswer) {
        setCorrectAnswerLayout(selectedAnswer)
        viewModel.correctQuestionCount++
      }
      else {
        setCorrectAnswerLayout(correctAnswer)
        setWrongAnswerLayout(selectedAnswer)
      }
      viewModel.setCurrentQuestionAnswer(selectedAnswer)
    }

    binding.submitButton.isEnabled = true
    setAnswersButtonsState(enabled = false)
  }

  private fun setCorrectAnswerLayout(buttonIndex: Int) {
    when (buttonIndex) {
      1 -> {
        binding.firstAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.correct_background_color)
        binding.firstAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
      2 -> {
        binding.secondAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.correct_background_color)
        binding.secondAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
      3 -> {
        binding.thirdAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.correct_background_color)
        binding.thirdAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
      4 -> {
        binding.forthAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.correct_background_color)
        binding.forthAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
    }
  }

  private fun setWrongAnswerLayout(buttonIndex: Int) {
    when (buttonIndex) {
      1 -> {
        binding.firstAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.wrong_background_color)
        binding.firstAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
      2 -> {
        binding.secondAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.wrong_background_color)
        binding.secondAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
      3 -> {
        binding.thirdAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.wrong_background_color)
        binding.thirdAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
      4 -> {
        binding.forthAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.wrong_background_color)
        binding.forthAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
    }
  }

  private fun setAnswersButtonsState(enabled: Boolean) {
    binding.firstAnswerButton.isEnabled = enabled
    binding.secondAnswerButton.isEnabled = enabled
    binding.thirdAnswerButton.isEnabled = enabled
    binding.forthAnswerButton.isEnabled = enabled
  }

  private fun resetAnswerButtonsSelectingState() {
    val mode = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
    when (mode) {
      Configuration.UI_MODE_NIGHT_YES -> {
        binding.firstAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_dark_background_color)
        binding.secondAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_dark_background_color)
        binding.thirdAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_dark_background_color)
        binding.forthAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_dark_background_color)

        binding.firstAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        binding.secondAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        binding.thirdAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        binding.forthAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
      }
      Configuration.UI_MODE_NIGHT_NO -> {
        binding.firstAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)
        binding.secondAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)
        binding.thirdAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)
        binding.forthAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)

        binding.firstAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        binding.secondAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        binding.thirdAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        binding.forthAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
      }
      Configuration.UI_MODE_NIGHT_UNDEFINED -> {
        binding.firstAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)
        binding.secondAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)
        binding.thirdAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)
        binding.forthAnswerButton.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.normal_background_color)

        binding.firstAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        binding.secondAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        binding.thirdAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        binding.forthAnswerButton.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
      }
    }
  }
}