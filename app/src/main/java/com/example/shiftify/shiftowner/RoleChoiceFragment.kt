package com.example.shiftify.shiftowner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shiftify.R
import com.example.shiftify.databinding.FragmentRoleChoiceBinding

class RoleChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRoleChoiceBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.shiftOwnerButton.setOnClickListener {
            navController.navigate(R.id.action_roleChoiceFragment_to_nursesShiftsFragment)
        }

        binding.nurseButton.setOnClickListener {
           navController.navigate(R.id.action_roleChoiceFragment_to_nurseShiftsFragment)
        }

        return binding.root
    }
}
