package com.christophprenissl.shiftify.view.shiftowner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentNursesShiftsBinding
import com.christophprenissl.shiftify.util.WEEK_DAY_COUNT
import com.christophprenissl.shiftify.viewmodel.nurse.PlanElementListener
import com.christophprenissl.shiftify.viewmodel.shiftowner.NursesShiftsViewModel
import java.util.*

class NursesShiftsFragment : Fragment(), PlanElementListener {

    private val viewModel: NursesShiftsViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNursesShiftsBinding.inflate(inflater, container, false)
        navController = findNavController()

        val observer = Observer<String> {
            binding.monthYearLabel.text = it
        }
        viewModel.monthYearText.observe(viewLifecycleOwner, observer)
        binding.shiftPlan.layoutManager = GridLayoutManager(context, WEEK_DAY_COUNT)
        val adapter = ShiftOwnerShiftPlanAdapter(context, viewModel, this)
        binding.shiftPlan.adapter = adapter

        return binding.root
    }

    override fun onPlanElementClick(index: Int) {
        viewModel.setChosenDayCalendar(index+1)

        navController.navigate(R.id.action_nursesShiftsFragment_to_shiftOwnerPlanDetailFragment)
    }
}
