package com.christophprenissl.shiftify.views.nurse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.christophprenissl.shiftify.databinding.FragmentNurseShiftsBinding
import com.christophprenissl.shiftify.viewmodels.nurse.NurseShiftsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class NurseShiftsFragment : Fragment() {

    private val viewModel: NurseShiftsViewModel by viewModels()
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

        binding.shiftPlan.layoutManager = GridLayoutManager(context, 7)
        val adapter = ShiftPlanAdapter(context, viewModel.currentDayCalendar, viewModel.monthCalendar)
        binding.shiftPlan.adapter = adapter

        binding.previousButton.setOnClickListener {
            viewModel.subtractMonth()
            adapter.notifyDataSetChanged()
        }
        binding.nextButton.setOnClickListener {
            viewModel.addMonth()
            adapter.notifyDataSetChanged()
        }

        val observer = Observer<String> {
            binding.monthYearLabel.text = it
        }

        viewModel.monthYear.observe(viewLifecycleOwner, observer)

        return binding.root
    }
}
