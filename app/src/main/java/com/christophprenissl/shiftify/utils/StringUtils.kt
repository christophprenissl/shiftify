package com.christophprenissl.shiftify.utils

import android.util.Patterns

fun isMatchingEmailAddress(address: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(address).matches()
}