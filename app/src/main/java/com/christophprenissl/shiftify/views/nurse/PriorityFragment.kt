package com.christophprenissl.shiftify.views.nurse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.christophprenissl.shiftify.databinding.FragmentPriorityBinding
import com.christophprenissl.shiftify.viewmodels.nurse.NurseShiftsViewModel
import java.util.*

class PriorityFragment() : Fragment() {

    private lateinit var viewModel: NurseShiftsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPriorityBinding.inflate(inflater)

        viewModel = requireActivity().run {
            ViewModelProviders.of(this)[NurseShiftsViewModel::class.java]
        }

        val observer = Observer<Calendar?> {
            binding.title.text = viewModel.chosenDay.value?.get(Calendar.DAY_OF_MONTH).toString()
        }
        viewModel.chosenDay.observe(viewLifecycleOwner, observer)

        return binding.root
    }
}