package com.abanobnageh.quizapp.core.di

import com.abanobnageh.quizapp.features.quiz.MainActivity
import com.abanobnageh.quizapp.features.quiz.di.QuizFragmentBuilderModule
import com.abanobnageh.quizapp.features.quiz.di.QuizModule
import com.abanobnageh.quizapp.features.quiz.di.QuizViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
  @ContributesAndroidInjector(
    modules = [
      QuizViewModelModule::class,
      QuizFragmentBuilderModule::class,
      QuizModule::class
    ]
  )
  abstract fun contributeMainActivity(): MainActivity
}