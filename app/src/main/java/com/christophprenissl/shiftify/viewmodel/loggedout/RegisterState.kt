package com.christophprenissl.shiftify.viewmodel.loggedout

enum class RegisterState {
    InputMissing,
    EmailNotCorrect,
    PasswordTooSmall,
    PasswordNotMatching,
    RegistrationSuccessful,
    AuthenticationFailed,
    RegistrationFailed
}