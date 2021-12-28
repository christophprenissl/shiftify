package com.christophprenissl.shiftify.view.nurse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentShiftOwnerShiftsBinding
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.util.WEEK_DAY_COUNT
import com.christophprenissl.shiftify.view.nurse.adapter.NurseShiftPlanAdapter
import com.christophprenissl.shiftify.viewmodel.nurse.state.NurseLoginState
import com.christophprenissl.shiftify.viewmodel.nurse.NurseViewModel
import com.christophprenissl.shiftify.viewmodel.nurse.NurseShiftPlanElementClickListener

class NurseShiftsFragment : Fragment(), NurseShiftPlanElementClickListener {

    private val viewModel: NurseViewModel by activityViewModels()
    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val binding = FragmentShiftOwnerShiftsBinding.inflate(inflater, container, false)
        navController = findNavController()

        viewModel.unChooseElement()

        binding.shiftPlan.layoutManager = GridLayoutManager(context, WEEK_DAY_COUNT)
        val adapter = NurseShiftPlanAdapter(context, viewModel, this)
        binding.shiftPlan.adapter = adapter

        val nurseShiftsStateObserver = Observer<NurseLoginState> {
            if (it == NurseLoginState.USER_LOGGED_OUT) {
                navController.navigate(R.id.action_nurseShiftsFragment_to_loginFragment)
            }
        }
        viewModel.nurseLoginState.observe(viewLifecycleOwner, nurseShiftsStateObserver)

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
