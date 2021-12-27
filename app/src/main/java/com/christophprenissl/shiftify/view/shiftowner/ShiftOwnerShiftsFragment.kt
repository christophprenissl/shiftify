package com.christophprenissl.shiftify.view.shiftowner

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
import com.christophprenissl.shiftify.databinding.FragmentNursesShiftsBinding
import com.christophprenissl.shiftify.util.WEEK_DAY_COUNT
import com.christophprenissl.shiftify.viewmodel.nurse.PlanElementListener
import com.christophprenissl.shiftify.viewmodel.shiftowner.ShiftOwnerViewModel
import com.christophprenissl.shiftify.viewmodel.shiftowner.ShiftOwnerLoginState

class ShiftOwnerShiftsFragment : Fragment(), PlanElementListener {

    private val viewModel: ShiftOwnerViewModel by activityViewModels()
    private lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
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

        binding.nextButton.setOnClickListener {
            viewModel.addMonth()
            adapter.notifyDataSetChanged()
        }

        binding.previousButton.setOnClickListener {
            viewModel.subtractMonth()
            adapter.notifyDataSetChanged()
        }

        val loginStateObserver = Observer<ShiftOwnerLoginState> {
            if (it == ShiftOwnerLoginState.USER_LOGGED_OUT) {
                navController.navigate(R.id.action_nursesShiftsFragment_to_loginFragment)
            }
        }
        viewModel.shiftOwnerLoginState.observe(viewLifecycleOwner, loginStateObserver)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onPlanElementClick(index: Int) {
        viewModel.setChosenDayCalendar(index+1)

        navController.navigate(R.id.action_nursesShiftsFragment_to_shiftOwnerPlanDetailFragment)
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
