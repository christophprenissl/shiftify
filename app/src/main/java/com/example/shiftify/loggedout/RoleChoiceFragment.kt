package com.example.shiftify.loggedout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shiftify.databinding.FragmentRoleChoiceBinding
import com.example.shiftify.nurse.NurseActivity
import com.example.shiftify.shiftowner.ShiftOwnerActivity

class RoleChoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRoleChoiceBinding.inflate(inflater, container, false)

        binding.shiftOwnerButton.setOnClickListener {
            val shiftOwnerActivity = Intent(activity, ShiftOwnerActivity::class.java)
            startActivity(shiftOwnerActivity)
        }

        binding.nurseButton.setOnClickListener {
            val nurseActivity = Intent(activity, NurseActivity::class.java)
            startActivity(nurseActivity)
        }

        return binding.root
    }
}
