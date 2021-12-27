package com.christophprenissl.shiftify.view.shiftowner

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.CardShiftOwnerShiftPlanCellBinding
import com.christophprenissl.shiftify.util.daysInTimeToMillis
import com.christophprenissl.shiftify.util.isInSameMonthAs
import com.christophprenissl.shiftify.util.isSameDayAs
import com.christophprenissl.shiftify.viewmodel.nurse.PlanElementListener
import com.christophprenissl.shiftify.viewmodel.shiftowner.ShiftOwnerViewModel
import java.util.*

class ShiftOwnerShiftPlanAdapter constructor(private val context: Context?,
                                             private val viewModel: ShiftOwnerViewModel,
                                             private val onClick: PlanElementListener) : RecyclerView.Adapter<ShiftOwnerShiftPlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardShiftOwnerShiftPlanCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lastIndexOfMonth = viewModel.monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1
        val dayOfWeek = when(viewModel.monthCalendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 7
            else -> viewModel.monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        }

        val calendarIterator = Calendar.getInstance()

        calendarIterator.timeInMillis = viewModel.monthCalendar.time.time + 1.daysInTimeToMillis() * (position + 1 - dayOfWeek)

        val dayOfMonthString = calendarIterator.get(Calendar.DAY_OF_MONTH).toString()

        if (position + 1 > lastIndexOfMonth + dayOfWeek) {
            holder.bind(
                planElementIndex = null,
                calendarIterator.get(Calendar.DAY_OF_MONTH).toString(),
                isInCurrentMonth = false,
                isActive = false,
                onClick = null
            )
        } else if (!calendarIterator.isInSameMonthAs(viewModel.monthCalendar)) {
            holder.bind(
                planElementIndex = null,
                dayOfMonthString,
                isInCurrentMonth = false,
                isActive = false,
                onClick = null
            )
        } else {
            holder.bind(
                position + 1 - dayOfWeek,
                calendarIterator.get(Calendar.DAY_OF_MONTH).toString(),
                isInCurrentMonth = true,
                isActive = calendarIterator.isSameDayAs(viewModel.currentDayCalendar),
                onClick
            )
        }
    }

    override fun getItemCount(): Int = 42

    class ViewHolder(private val binding: CardShiftOwnerShiftPlanCellBinding,
                     private val context: Context?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(planElementIndex: Int?, dateText: String, isInCurrentMonth: Boolean, isActive: Boolean = false,
                 onClick: PlanElementListener?) {

            binding.weekDayText.text = dateText
            if (isActive && context != null) {
                binding.weekDayText.setTextColor(context.getColor(R.color.black))
                binding.cell.setCardBackgroundColor(context.getColor(R.color.today))
            } else if (!isInCurrentMonth && context != null) {
                binding.weekDayText.setTextColor(context.getColor(R.color.other_month))
                binding.cell.setCardBackgroundColor(context.getColor(R.color.cell_background))
            } else if (context != null) {
                binding.weekDayText.setTextColor(context.getColor(R.color.black))
                binding.cell.setCardBackgroundColor(context.getColor(R.color.cell_background))
            }

            if (onClick != null && planElementIndex != null) {
                binding.root.setOnClickListener { onClick.onPlanElementClick(planElementIndex) }
            }
        }
    }
}