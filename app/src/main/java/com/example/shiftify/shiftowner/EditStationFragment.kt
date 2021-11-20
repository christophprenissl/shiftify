package com.example.shiftify.shiftowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shiftify.databinding.FragmentEditStationBinding

class EditStationFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditStationBinding.inflate(inflater, container, false)
        return binding.root
    }
}