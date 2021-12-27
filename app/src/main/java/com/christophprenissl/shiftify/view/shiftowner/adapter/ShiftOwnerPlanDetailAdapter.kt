package com.christophprenissl.shiftify.view.shiftowner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.CardDayPlanPrioritiesBinding
import com.christophprenissl.shiftify.util.dayMonthYearString
import com.christophprenissl.shiftify.viewmodel.shiftowner.ShiftOwnerViewModel

class ShiftOwnerPlanDetailAdapter constructor(private val context: Context?,
                                              private val viewModel: ShiftOwnerViewModel,
) : RecyclerView.Adapter<ShiftOwnerPlanDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardDayPlanPrioritiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nurseName = viewModel.shiftOwnerPlan.value?.planElementMap?.keys?.toList()?.get(position)
        val dayString = viewModel.chosenDayCalendar.dayMonthYearString()
        val nurseDayPlanPriorities = viewModel.shiftOwnerPlan.value?.planElementMap?.get(nurseName)
            ?.get(dayString)?.priorityMap
        if (nurseDayPlanPriorities != null) {
            context?.let { c ->
                val earlyPriority = nurseDayPlanPriorities[c.getString(R.string.early_shift)] ?: 2
                val latePriority = nurseDayPlanPriorities[c.getString(R.string.late_shift)] ?: 2
                val nightPriority = nurseDayPlanPriorities[c.getString(R.string.night_shift)] ?: 2
                val freePriority = nurseDayPlanPriorities[c.getString(R.string.free_shift)] ?: 2
                val holidayPriority = nurseDayPlanPriorities[c.getString(R.string.holiday_shift)] ?: 2
                holder.bind(nurseName!!, earlyPriority, latePriority, nightPriority, freePriority, holidayPriority)
            }
        } else {
            holder.bind(nurseName?: "Error",
                2,2,2,2,2)
        }
    }

    override fun getItemCount(): Int = viewModel.shiftOwnerPlan.value?.planElementMap?.size?: 0

    class ViewHolder(private val binding: CardDayPlanPrioritiesBinding,
                     private val context: Context?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(nurseName: String,
                 earlyShiftPriority: Int,
                 lateShiftPriority: Int,
                 nightShiftPriority: Int,
                 freeShiftPriority: Int,
                 holidayShiftPriority: Int) {

            binding.nurseName.text = nurseName
            binding.earlyShift.text = context?.getString(R.string.early_shift_priority_string, earlyShiftPriority)
            binding.lateShift.text = context?.getString(R.string.late_shift_priority_string, lateShiftPriority)
            binding.nightShift.text = context?.getString(R.string.night_shift_priority_string, nightShiftPriority)
            binding.freeShift.text = context?.getString(R.string.free_shift_priority_string, freeShiftPriority)
            binding.holidayShift.text = context?.getString(R.string.holiday_shift_priority_string, holidayShiftPriority)
        }
    }
}
