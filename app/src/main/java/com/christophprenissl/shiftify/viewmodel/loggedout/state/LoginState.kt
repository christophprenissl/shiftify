package com.christophprenissl.shiftify.viewmodel.loggedout.state

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
