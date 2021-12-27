package com.christophprenissl.shiftify.view.shiftowner

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.FragmentRoleChoiceBinding
import com.christophprenissl.shiftify.viewmodel.shiftowner.NursesShiftsViewModel
import com.christophprenissl.shiftify.viewmodel.shiftowner.ShiftOwnerLoginState

class RoleChoiceFragment : Fragment() {

    private val viewModel: NursesShiftsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRoleChoiceBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.shiftOwnerButton.setOnClickListener {
            navController.navigate(R.id.action_roleChoiceFragment_to_nursesShiftsFragment)
        }

        binding.nurseButton.setOnClickListener {
           navController.navigate(R.id.action_roleChoiceFragment_to_nurseShiftsFragment)
        }

        val loginStateObserver = Observer<ShiftOwnerLoginState> {
            if (it == ShiftOwnerLoginState.USER_LOGGED_OUT) {
                navController.navigate(R.id.action_roleChoiceFragment_to_loginFragment)
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
