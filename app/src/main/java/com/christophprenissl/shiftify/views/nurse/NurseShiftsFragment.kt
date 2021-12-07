package com.christophprenissl.shiftify.views.nurse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentNurseShiftsBinding
import com.christophprenissl.shiftify.viewmodels.nurse.NurseShiftsViewModel
import com.christophprenissl.shiftify.viewmodels.nurse.PlanElementListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import java.util.*

class NurseShiftsFragment : Fragment(), PlanElementListener {

    private lateinit var viewModel: NurseShiftsViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNurseShiftsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        navController = findNavController()

        viewModel = requireActivity().run {
            ViewModelProviders.of(this)[NurseShiftsViewModel::class.java]
        }
        viewModel.unChooseDay()

        binding.shiftPlan.layoutManager = GridLayoutManager(context, 7)
        val adapter = ShiftPlanAdapter(context, viewModel, this)
        binding.shiftPlan.adapter = adapter

        binding.previousButton.setOnClickListener {
            viewModel.subtractMonth()
            adapter.notifyDataSetChanged()
        }
        binding.nextButton.setOnClickListener {
            viewModel.addMonth()
            adapter.notifyDataSetChanged()
        }

        val monthYearObserver = Observer<String> {
            binding.monthYearLabel.text = it
        }
        viewModel.monthYear.observe(viewLifecycleOwner, monthYearObserver)

        val chosenDayObserver = Observer<Calendar?> {
            it?.let {
                navController.navigate(R.id.action_nurseShiftsFragment_to_priorityFragment)
            }
        }
        viewModel.chosenDay.observe(viewLifecycleOwner, chosenDayObserver)

        return binding.root
    }

    override fun onPlanElementClick(day: Calendar) {
        viewModel.chooseDay(day)
    }
}
