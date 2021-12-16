package com.christophprenissl.shiftify.viewmodel.loggedout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christophprenissl.shiftify.util.isMatchingEmailAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val usersDatabaseReference = Firebase.database.reference.child("users")

    private val _loginState: MutableLiveData<LoginState> = MutableLiveData()
    val loginState: LiveData<LoginState> = _loginState

    private var isShiftOwner:Boolean? = null

    init {
        auth.addAuthStateListener { mAuth ->
            if (mAuth.currentUser != null) {
                usersDatabaseReference.child(mAuth.currentUser!!.uid).child("shiftOwner").get()
                    .addOnSuccessListener {
                        isShiftOwner = it.value as Boolean?
                        setLoginState()
                    }
            }
        }

    }

    private fun setLoginState() {
        if (auth.currentUser != null && isShiftOwner != null) {
            if (isShiftOwner== false) {
                _loginState.value = LoginState.LoggedInAsNurse
            } else {
                _loginState.value = LoginState.LoggedInAsShiftOwner
            }
        } else {
            _loginState.value = LoginState.LoggedOut
        }
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
        }
    }
}
