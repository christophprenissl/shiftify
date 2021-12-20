package com.christophprenissl.shiftify.view.shiftowner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.christophprenissl.shiftify.databinding.FragmentNursesShiftsBinding
import com.christophprenissl.shiftify.util.WEEK_DAY_COUNT
import com.christophprenissl.shiftify.util.monthYearString
import com.christophprenissl.shiftify.viewmodel.nurse.PlanElementListener
import com.christophprenissl.shiftify.viewmodel.shiftowner.NursesShiftsViewModel
import timber.log.Timber.i

class NursesShiftsFragment : Fragment(), PlanElementListener {

    private val viewModel: NursesShiftsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNursesShiftsBinding.inflate(inflater, container, false)

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
        val month = viewModel.monthCalendar.monthYearString()
        i(month)
        for (nurse in viewModel.shiftOwnerPlan.value!!.planElementList) {
            i(nurse.key)
            val monthPlan = nurse.value[month]
            monthPlan?.forEach {
                it.priorityMap.forEach { priority ->
                    i("${priority.key} ${priority.value}")
                }
            }
        }
    }
}
