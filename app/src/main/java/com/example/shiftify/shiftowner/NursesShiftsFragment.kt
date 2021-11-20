package com.example.shiftify.shiftowner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shiftify.R
import com.example.shiftify.databinding.FragmentNursesShiftsBinding

class NursesShiftsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNursesShiftsBinding.inflate(inflater, container, false)
        return binding.root
    }
}