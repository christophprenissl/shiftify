package com.christophprenissl.shiftify.views.nurse

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.CardNurseShiftPlanCellBinding
import com.christophprenissl.shiftify.utils.daysInTimeToMillis
import com.christophprenissl.shiftify.utils.isInSameMonthAs
import com.christophprenissl.shiftify.utils.isSameDayAs
import com.christophprenissl.shiftify.viewmodels.nurse.NurseShiftsViewModel
import com.christophprenissl.shiftify.viewmodels.nurse.PlanElementListener
import java.util.*

class ShiftPlanAdapter constructor(private val context: Context?,
                                   private val viewModel: NurseShiftsViewModel,
                                   private val onClick: PlanElementListener) : RecyclerView.Adapter<ShiftPlanAdapter.ViewHolder>() {

    private val calendarIterator: Calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardNurseShiftPlanCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //set start of week to Monday
        val dayOfWeek = when(viewModel.monthCalendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 7
            else -> viewModel.monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        }

        //showing days from last month in the beginning of the week till month starts
        //setting the date index for the first shown date by subtracting the dayOfWeek of the calendars first date
        calendarIterator.time = Date(viewModel.monthCalendar.time.time + 1.daysInTimeToMillis() * (position + 1 - dayOfWeek))

        holder.bind(calendarIterator.clone() as Calendar,
            viewModel.monthCalendar.isInSameMonthAs(calendarIterator),
            calendarIterator.isSameDayAs(viewModel.currentDayCalendar),
            onClick)
    }

    override fun getItemCount(): Int = 42

    class ViewHolder(private val binding: CardNurseShiftPlanCellBinding,
                     private val context: Context?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: Calendar, isInCurrentMonth: Boolean, isActive: Boolean = false,
            onClick: PlanElementListener) {

            binding.weekDayText.text = day.get(Calendar.DAY_OF_MONTH).toString()
            if (isActive && context != null) {
                binding.weekDayText.setTextColor(context.getColor(R.color.black))
                binding.cellTypeIndicator.setBackgroundColor(context.getColor(R.color.shift_early))
                binding.cell.setCardBackgroundColor(context.getColor(R.color.today))
            } else if (!isInCurrentMonth && context != null) {
                binding.weekDayText.setTextColor(context.getColor(R.color.other_month))
                binding.cellTypeIndicator.setBackgroundColor(context.getColor(R.color.shift_early))
                binding.cell.setCardBackgroundColor(context.getColor(R.color.cell_background))
            } else if (context != null) {
                binding.weekDayText.setTextColor(context.getColor(R.color.black))
                binding.cellTypeIndicator.setBackgroundColor(context.getColor(R.color.shift_early))
                binding.cell.setCardBackgroundColor(context.getColor(R.color.cell_background))
            }

            binding.root.setOnClickListener { onClick.onPlanElementClick(day) }
        }
    }
}
