package com.christophprenissl.shiftify.viewmodel.loggedout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christophprenissl.shiftify.util.isMatchingEmailAddress
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _loginState: MutableLiveData<LoginState> = MutableLiveData()
    val loginState: LiveData<LoginState> = _loginState

    init {
        if (auth.currentUser != null) {
            _loginState.value = LoginState.LoggedIn
        } else {
            _loginState.value = LoginState.LoggedOut
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.LoggedOut
    }

    fun tryToSignIn(email: String, password: String) {
        if (!isMatchingEmailAddress(email)) {
            _loginState.value = LoginState.ErrorEmail
            return
        } else if (password.length < 6) {
            _loginState.value = LoginState.ErrorPassword
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    _loginState.value = LoginState.ErrorLogin
                }
                .addOnSuccessListener {
                    _loginState.value = LoginState.Success
                }
        }
    }
}
