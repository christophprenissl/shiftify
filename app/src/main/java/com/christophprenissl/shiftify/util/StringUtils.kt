package com.christophprenissl.shiftify.util

import android.util.Patterns

fun isMatchingEmailAddress(address: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(address).matches()
}