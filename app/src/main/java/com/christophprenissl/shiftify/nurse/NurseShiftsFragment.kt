package com.christophprenissl.shiftify.nurse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.christophprenissl.shiftify.databinding.FragmentNurseShiftsBinding

class NurseShiftsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNurseShiftsBinding.inflate(inflater, container, false)

        return binding.root
    }
}
