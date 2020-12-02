package com.abanobnageh.quizapp.features.quiz.di

import com.abanobnageh.quizapp.features.quiz.presentation.screens.EndScreenFragment
import com.abanobnageh.quizapp.features.quiz.presentation.screens.QuizListScreenFragment
import com.abanobnageh.quizapp.features.quiz.presentation.screens.QuizScreenFragment
import com.abanobnageh.quizapp.features.quiz.presentation.screens.StartScreenFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class QuizFragmentBuilderModule {
  @ContributesAndroidInjector
  abstract fun contributeStartScreenFragment(): StartScreenFragment

  @ContributesAndroidInjector
  abstract fun contributeQuizListScreenFragment(): QuizListScreenFragment

  @ContributesAndroidInjector
  abstract fun contributeQuizScreenFragment(): QuizScreenFragment

  @ContributesAndroidInjector
  abstract fun contributeEndScreenFragment(): EndScreenFragment
}