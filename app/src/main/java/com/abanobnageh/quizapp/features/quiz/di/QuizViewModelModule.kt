package com.abanobnageh.quizapp.features.quiz.di

import androidx.lifecycle.ViewModel
import com.abanobnageh.quizapp.core.di.ViewModelKey
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.EndScreenViewModel
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.QuizListScreenViewModel
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.QuizScreenViewModel
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.StartScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class QuizViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(StartScreenViewModel::class)
  abstract fun bindStartScreenViewModel(viewModel: StartScreenViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(QuizListScreenViewModel::class)
  abstract fun bindQuizListScreenViewModel(viewModel: QuizListScreenViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(QuizScreenViewModel::class)
  abstract fun bindQuizScreenViewModel(viewModel: QuizScreenViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(EndScreenViewModel::class)
  abstract fun bindEndScreenViewModel(viewModel: EndScreenViewModel): ViewModel
}