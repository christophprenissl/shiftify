package com.christophprenissl.shiftify.viewmodel.loggedout.state

enum class RegisterState {
    InputMissing,
    EmailNotCorrect,
    PasswordTooSmall,
    PasswordNotMatching,
    RegistrationAsNurseSuccessful,
    RegistrationAsShiftOwnerSuccessful,
    AuthenticationFailed,
    RegistrationFailed
}