package com.christophprenissl.shiftify.viewmodel.loggedout

enum class LoginState {
    LoggedInAsNurse,
    LoggedInAsShiftOwner,
    LoggedOut,
    Loading,
    SuccessAsNurse,
    SuccessAsShiftOwner,
    ErrorLogin,
    ErrorPassword,
    ErrorEmail
}
