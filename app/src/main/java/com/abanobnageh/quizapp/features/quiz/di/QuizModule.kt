package com.abanobnageh.quizapp.features.quiz.di

import com.abanobnageh.quizapp.core.network.NetworkInfo
import com.abanobnageh.quizapp.core.network.QuizAPIService
import com.abanobnageh.quizapp.features.quiz.data.datasources.QuizRemoteDataSource
import com.abanobnageh.quizapp.features.quiz.data.datasources.QuizRemoteDataSourceImpl
import com.abanobnageh.quizapp.features.quiz.data.repositories.QuizRepositoryImpl
import com.abanobnageh.quizapp.features.quiz.domain.repositories.QuizRepository
import com.abanobnageh.quizapp.features.quiz.domain.usecases.GetQuizById
import com.abanobnageh.quizapp.features.quiz.domain.usecases.GetQuizzes
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class QuizModule {
  companion object {
    @Provides
    fun provideQuizAPIInstance(retrofit: Retrofit): QuizAPIService {
      return retrofit.create(QuizAPIService::class.java)
    }

    @Provides
    fun provideQuizRemoteDataSource(quizAPIService: QuizAPIService): QuizRemoteDataSource {
      return QuizRemoteDataSourceImpl(quizAPIService)
    }

    @Provides
    fun provideQuizRepository(quizRemoteDataSource: QuizRemoteDataSource, networkInfo: NetworkInfo): QuizRepository {
      return QuizRepositoryImpl(quizRemoteDataSource, networkInfo)
    }

    @Provides
    fun provideGetQuizByIdUseCase(quizRepository: QuizRepository): GetQuizById {
      return GetQuizById(quizRepository)
    }

    @Provides
    fun provideGetQuizzesUseCase(quizRepository: QuizRepository): GetQuizzes {
      return GetQuizzes(quizRepository)
    }
  }
}