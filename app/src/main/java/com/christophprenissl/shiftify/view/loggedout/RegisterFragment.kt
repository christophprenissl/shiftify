package com.christophprenissl.shiftify.view.loggedout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentRegisterBinding
import com.christophprenissl.shiftify.util.showSmallInfoToast
import com.christophprenissl.shiftify.viewmodel.loggedout.RegisterState
import com.christophprenissl.shiftify.viewmodel.loggedout.RegisterViewModel
import com.christophprenissl.shiftify.viewmodel.loggedout.RegisterViewState

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        navController = findNavController()
        viewModel.resetViewState()

        val registerStateObserver = Observer<RegisterState> {
            when(it) {
                RegisterState.InputMissing -> showSmallInfoToast(context, "Some input is missing")
                RegisterState.EmailNotCorrect -> showSmallInfoToast(context, "Email is not correct")
                RegisterState.PasswordTooSmall -> showSmallInfoToast(context, "The password needs to have at least 6 characters")
                RegisterState.PasswordNotMatching -> showSmallInfoToast(context, "Passwords not matching")
                RegisterState.RegistrationFailed -> showSmallInfoToast(context, "Some error happened, registration could not be completed")
                RegisterState.AuthenticationFailed -> showSmallInfoToast(context, "Some error happened, registration could not be completed")
                RegisterState.RegistrationAsNurseSuccessful -> {
                    showSmallInfoToast(context, "Registration successful")
                    navController.navigate(R.id.action_registerFragment_to_nurseShiftsFragment)
                }
                RegisterState.RegistrationAsShiftOwnerSuccessful -> {
                    showSmallInfoToast(context, "Registration successful")
                    navController.navigate(R.id.action_registerFragment_to_roleChoiceFragment)
                }
                else -> {}
            }
        }

        viewModel.registerState.observe(viewLifecycleOwner, registerStateObserver)

        val registerViewStateObserver = Observer<RegisterViewState> {
            if (it.isShiftOwner) {
                binding.stationIdLabel.text = getString(R.string.station_name_label)
            } else {
                binding.stationIdLabel.text = getString(R.string.station_code_label)
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner, registerViewStateObserver)

        binding.identityRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.nurse_radio_button -> {
                    viewModel.setIsShiftowner(false)
                }
                R.id.shift_owner_radio_button -> {
                    viewModel.setIsShiftowner(true)
                }
            }
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailEdit.text.toString()
            val password1 = binding.password1Edit.text.toString()
            val password2 = binding.password2Edit.text.toString()
            val lastName = binding.lastNameEdit.text.toString()
            val firstName = binding.firstNameEdit.text.toString()
            val isShiftOwner = binding.identityRadioGroup.checkedRadioButtonId == R.id.shift_owner_radio_button
            val stationValue = binding.stationIdEdit.text.toString()
            viewModel.registerWhenValidInput(
                email,
                lastName,
                firstName,
                isShiftOwner,
                password1,
                password2,
                stationValue
            )
        }

        return binding.root
    }
}

