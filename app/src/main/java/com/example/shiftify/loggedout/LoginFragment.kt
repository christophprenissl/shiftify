package com.example.shiftify.loggedout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.shiftify.R
import com.example.shiftify.databinding.FragmentLoginBinding
import timber.log.Timber
import timber.log.Timber.i

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.loginNurseButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_nurseShiftsFragment)
        }

        binding.loginShiftOwnerButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_roleChoiceFragment)
        }

        binding.registerButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }
}
