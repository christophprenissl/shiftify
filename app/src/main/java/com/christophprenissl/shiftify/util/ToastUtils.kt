package com.christophprenissl.shiftify.util

import android.content.Context
import android.widget.Toast

fun showSmallInfoToast(context: Context?, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT)
        .show()
}