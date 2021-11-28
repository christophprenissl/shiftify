package com.christophprenissl.shiftify.viewmodels.loggedout

enum class RegisterState {
    InputMissing,
    EmailNotCorrect,
    PasswordTooSmall,
    PasswordNotMatching,
    RegistrationSuccessful,
    AuthenticationFailed,
    RegistrationFailed
}