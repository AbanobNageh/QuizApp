package com.abanobnageh.quizapp.core.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abanobnageh.quizapp.R
import com.tapadoo.alerter.Alerter

fun showError(context: Context, message: String, useToast: Boolean = false) {
    if (useToast) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    else {
        Alerter.create(context as AppCompatActivity)
            .setTitle(R.string.error_alerter_title)
            .setText(message)
            .setIcon(R.drawable.error_icon)
            .setBackgroundColorRes(R.color.alerter_error_background_color)
            .show()
    }
}

fun showWarning(context: Context, message: String, useToast: Boolean = false) {
    if (useToast) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    else {
        Alerter.create(context as AppCompatActivity)
            .setTitle(R.string.warning_alerter_title)
            .setText(message)
            .setIcon(R.drawable.warning_icon)
            .setBackgroundColorRes(R.color.alerter_warning_background_color)
            .show()
    }
}

fun showInformation(context: Context, message: String, useToast: Boolean = false) {
    if (useToast) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    else {
        Alerter.create(context as AppCompatActivity)
            .setTitle(R.string.information_alerter_title)
            .setText(message)
            .setIcon(R.drawable.information_icon)
            .setBackgroundColorRes(R.color.alerter_information_background_color)
            .show()
    }
}