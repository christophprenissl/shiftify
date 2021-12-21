package com.christophprenissl.shiftify.viewmodel.loggedout

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