package com.christophprenissl.shiftify.view.shiftowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.christophprenissl.shiftify.databinding.FragmentShiftOwnerPlanDetailBinding
import com.christophprenissl.shiftify.util.dayMonthYearString
import com.christophprenissl.shiftify.viewmodel.shiftowner.NursesShiftsViewModel

class ShiftOwnerPlanDetailFragment: Fragment() {

    val viewModel: NursesShiftsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShiftOwnerPlanDetailBinding.inflate(inflater, container, false)
        binding.dayMonthYear.text = viewModel.chosenDayCalendar.dayMonthYearString()
        binding.prioritiesList.layoutManager = LinearLayoutManager(context)
        binding.prioritiesList.adapter = ShiftOwnerPlanDetailAdapter(context, viewModel)

        return binding.root
    }

}