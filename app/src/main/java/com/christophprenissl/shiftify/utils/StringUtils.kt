package com.christophprenissl.shiftify.utils

fun String.isEmail() : Boolean {
    val regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return this.matches(regex)
}