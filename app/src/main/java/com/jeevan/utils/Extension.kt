package com.jeevan.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun Activity.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), message, length).show()
}