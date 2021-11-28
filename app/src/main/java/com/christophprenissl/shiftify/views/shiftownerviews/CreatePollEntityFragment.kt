package com.christophprenissl.shiftify.views.shiftownerviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.christophprenissl.shiftify.databinding.FragmentCreatePollEntityBinding

class CreatePollEntityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreatePollEntityBinding.inflate(inflater, container, false)
        return binding.root
    }
}