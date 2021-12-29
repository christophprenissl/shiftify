package com.christophprenissl.shiftify.view.shiftowner

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentShiftOwnerPlanDetailBinding
import com.christophprenissl.shiftify.model.entity.ShiftOwnerPlan
import com.christophprenissl.shiftify.util.dayMonthYearString
import com.christophprenissl.shiftify.view.shiftowner.adapter.ShiftOwnerPlanDetailAdapter
import com.christophprenissl.shiftify.viewmodel.shiftowner.ShiftOwnerViewModel
import com.christophprenissl.shiftify.viewmodel.shiftowner.state.ShiftOwnerLoginState

class ShiftOwnerPlanDetailFragment: Fragment() {

    val viewModel: ShiftOwnerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShiftOwnerPlanDetailBinding.inflate(inflater, container, false)
        binding.dayMonthYear.text = viewModel.chosenDayCalendar.dayMonthYearString()
        binding.prioritiesList.layoutManager = LinearLayoutManager(context)
        val adapter = ShiftOwnerPlanDetailAdapter(context, viewModel)
        binding.prioritiesList.adapter = adapter

        val shiftOwnerPlanObserver = Observer<ShiftOwnerPlan> {
            adapter.updateDataset()
        }
        viewModel.shiftOwnerPlan.observe(viewLifecycleOwner, shiftOwnerPlanObserver)

        val navController = findNavController()

        val loginStateObserver = Observer<ShiftOwnerLoginState> {
            if (it == ShiftOwnerLoginState.USER_LOGGED_OUT) {
                navController.navigate(R.id.action_shiftOwnerPlanDetailFragment_to_loginFragment)
            }
        }
        viewModel.shiftOwnerLoginState.observe(viewLifecycleOwner, loginStateObserver)

        setHasOptionsMenu(true)
        return binding.root
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