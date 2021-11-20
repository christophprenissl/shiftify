package com.christophprenissl.shiftify.loggedout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val navController = findNavController()

        binding.roleButton.apply {
            setOnClickListener {
                if (this.text == "Nurse") {
                    this.text = "Shift Owner"
                } else {
                    this.text = "Nurse"
                }
            }
        }

        binding.registerButton.setOnClickListener {
            if (binding.roleButton.text == "Shift Owner") {
                navController.navigate(R.id.action_registerFragment_to_roleChoiceFragment)
            } else {
                navController.navigate(R.id.action_registerFragment_to_nurseShiftsFragment)
            }
        }

        return binding.root
    }
}
