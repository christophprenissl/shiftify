package com.christophprenissl.shiftify.view.nurse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentNurseShiftsBinding
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.util.WEEK_DAY_COUNT
import com.christophprenissl.shiftify.viewmodel.nurse.NurseShiftsState
import com.christophprenissl.shiftify.viewmodel.nurse.NurseShiftsViewModel
import com.christophprenissl.shiftify.viewmodel.nurse.PlanElementListener

class NurseShiftsFragment : Fragment(), PlanElementListener {

    private lateinit var viewModel: NurseShiftsViewModel
    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val binding = FragmentNurseShiftsBinding.inflate(inflater, container, false)
        navController = findNavController()

        viewModel = requireActivity().run {
            ViewModelProviders.of(this)[NurseShiftsViewModel::class.java]
        }
        viewModel.unChooseElement()

        binding.shiftPlan.layoutManager = GridLayoutManager(context, WEEK_DAY_COUNT)
        val adapter = ShiftPlanAdapter(context, viewModel, this)
        binding.shiftPlan.adapter = adapter

        val nurseShiftsStateObserver = Observer<NurseShiftsState> {
            if (it == NurseShiftsState.USER_LOGGED_OUT) {
                navController.navigate(R.id.action_nurseShiftsFragment_to_loginFragment)
            }
        }
        viewModel.nurseShiftsState.observe(viewLifecycleOwner, nurseShiftsStateObserver)

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

        val chosenDayObserver = Observer<PlanElement?> {
            it?.let {
                navController.navigate(R.id.action_nurseShiftsFragment_to_priorityFragment)
            }
        }
        viewModel.aboutToSavePlanElement.observe(viewLifecycleOwner, chosenDayObserver)

        viewModel.setLoginState()

        return binding.root
    }

    override fun onPlanElementClick(index: Int) {
        viewModel.choosePlanElement(index)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> viewModel.logoutUser()
        }

        return super.onOptionsItemSelected(item)
    }
}
