package com.christophprenissl.shiftify.viewmodels.loggedout

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christophprenissl.shiftify.models.Nurse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import timber.log.Timber

class RegisterViewModel : ViewModel() {

    private val _registerState: MutableLiveData<RegisterState> = MutableLiveData()
    val registerState: LiveData<RegisterState> = _registerState

    private val _viewState: MutableLiveData<RegisterViewState> = MutableLiveData()
    val viewState: LiveData<RegisterViewState> = _viewState

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference

    init {
        resetViewState()
    }

    fun resetViewState() {
        _viewState.value = RegisterViewState()
    }

    fun setIsShiftowner(value: Boolean) {
        _viewState.value = _viewState.value?.copy(isShiftOwner = value)
    }

    fun registerWhenValidInput(email: String,
                                       lastName: String,
                                       firstName: String,
                                       isShiftOwner: Boolean,
                                       password1: String,
                                       password2: String,
                                       stationValue: String) {

        if (email.isEmpty()
            || firstName.isEmpty()
            || lastName.isEmpty()
            || stationValue.isEmpty()) {
                _registerState.value = RegisterState.InputMissing
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _registerState.value = RegisterState.EmailNotCorrect
            return
        }

        if (password1.length < 6) {
            _registerState.value = RegisterState.PasswordTooSmall
            return
        }

        if (password1 != password2) {
            _registerState.value = RegisterState.PasswordNotMatching
            return
        }
        val newNurse = Nurse (
            lastName,
            firstName,
            isShiftOwner,
            stationValue,
            null,
            null
        )
        registerNurse(email,password1, newNurse)
    }

    private fun registerNurse(email: String, password: String, nurse : Nurse) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser!!.let {
                            Timber.i("$email was registered correctly")
                            database.child("users").child(it.uid).setValue(nurse)
                                .addOnSuccessListener {
                                    Timber.i("${nurse.firstName} ${nurse.lastName} created in database.")
                                    _registerState.value = RegisterState.RegistrationSuccessful
                                }
                                .addOnFailureListener {
                                    _registerState.value = RegisterState.AuthenticationFailed
                                }
                        }
                    } else {
                        _registerState.value = RegisterState.RegistrationFailed
                    }
                }
        }
    }
}
