package com.studiosol.utils

import android.content.Context
import android.widget.Toast

fun showToastMessage(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}