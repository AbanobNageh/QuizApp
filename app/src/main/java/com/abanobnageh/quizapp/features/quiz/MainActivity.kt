package com.abanobnageh.quizapp.features.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.abanobnageh.quizapp.R
import com.abanobnageh.quizapp.core.di.ViewModelProviderFactory
import com.abanobnageh.quizapp.databinding.ActivityMainBinding
import com.abanobnageh.quizapp.features.quiz.presentation.viewmodels.StartScreenViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    @Inject lateinit var modelFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, modelFactory).get(MainActivityViewModel::class.java)
    }
}