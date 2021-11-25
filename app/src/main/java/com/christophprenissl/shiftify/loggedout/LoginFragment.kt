package com.christophprenissl.shiftify.loggedout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentLoginBinding
import com.christophprenissl.shiftify.utils.showSmallInfoToast
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        navController = findNavController()

        if (auth.currentUser != null) {
            navController.navigate(R.id.action_loginFragment_to_nurseShiftsFragment)
        }

        binding.loginButton.setOnClickListener {
            if (binding.emailEdit.text.isEmpty()) {
                showSmallInfoToast(context, "Email address is missing.")
                return@setOnClickListener
            }

            when (binding.passwordEdit.text.length) {
                0 -> {
                    showSmallInfoToast(context, "Password is missing")
                    return@setOnClickListener
                }
                in 0..5 -> {
                    showSmallInfoToast(context, "Password is too short")
                    return@setOnClickListener
                }
            }

            binding.loginButton.isActivated = false

            auth.signInWithEmailAndPassword(binding.emailEdit.text.toString(),
                binding.passwordEdit.text.toString())
                .addOnFailureListener {
                    showSmallInfoToast(context, "Error while signing in")
                }
                .addOnSuccessListener {
                    navController.navigate(R.id.action_loginFragment_to_nurseShiftsFragment)
                }
                .addOnCompleteListener {
                    binding.loginButton.isActivated = true
                }

        }

        binding.registerButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            navController.navigate(R.id.action_loginFragment_to_nurseShiftsFragment)
        }
    }
}
