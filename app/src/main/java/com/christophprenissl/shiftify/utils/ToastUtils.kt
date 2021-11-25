package com.christophprenissl.shiftify.utils

import android.content.Context
import android.widget.Toast

fun showSmallInfoToast(context: Context?, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT)
        .show()
}