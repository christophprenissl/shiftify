package com.example.shiftify.loggedout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shiftify.R
import com.example.shiftify.databinding.FragmentLoginBinding
import com.example.shiftify.nurse.NurseActivity

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.loginNurseButton.setOnClickListener {
            val intent = Intent(activity, NurseActivity::class.java)
            startActivity(intent)
        }

        binding.loginStationOwnerButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_roleChoiceFragment)
        }

        binding.registerButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }
}
