package com.abanobnageh.quizapp.core.di

import androidx.lifecycle.ViewModel
import com.abanobnageh.quizapp.features.quiz.MainActivityViewModel
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.StartScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(MainActivityViewModel::class)
  abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}