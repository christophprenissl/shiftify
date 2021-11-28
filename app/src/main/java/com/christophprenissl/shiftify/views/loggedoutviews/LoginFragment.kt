package com.christophprenissl.shiftify.views.loggedoutviews

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
import com.christophprenissl.shiftify.databinding.FragmentLoginBinding
import com.christophprenissl.shiftify.utils.showSmallInfoToast
import com.christophprenissl.shiftify.viewmodels.LoginState
import com.christophprenissl.shiftify.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        navController = findNavController()

        val loginStateObserver = Observer<LoginState> {
            binding.loginButton.isActivated = true
            when (it) {
                LoginState.Success -> {
                    showSmallInfoToast(context, "Successfully logged in")
                    navController.navigate(R.id.action_loginFragment_to_nurseShiftsFragment)
                }
                LoginState.LoggedIn -> navController.navigate(R.id.action_loginFragment_to_nurseShiftsFragment)
                LoginState.ErrorEmail -> showSmallInfoToast(context, "Email is not correct")
                LoginState.ErrorPassword -> showSmallInfoToast(context, "Password must at least be 6 characters")
                LoginState.ErrorLogin -> showSmallInfoToast(context, "An error occurred while logging in")
                LoginState.Loading -> binding.loginButton.isActivated = false
                else -> {}
            }
        }

        viewModel.loginState.observe(viewLifecycleOwner, loginStateObserver)

        binding.loginButton.setOnClickListener {

            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            viewModel.tryToSignIn(email, password)
        }

        binding.registerButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }
}
