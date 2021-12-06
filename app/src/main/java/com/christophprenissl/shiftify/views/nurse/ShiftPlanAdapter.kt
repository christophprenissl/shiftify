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
import java.util.*

class ShiftPlanAdapter constructor(private val context: Context?, private val currentDay: Calendar, private val month: Calendar) : RecyclerView.Adapter<ShiftPlanAdapter.ViewHolder>() {

    private val calendarIterator = month.clone() as Calendar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardNurseShiftPlanCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //set start of week to Monday
        val dayOfWeek = when(month.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 7
            else -> month.get(Calendar.DAY_OF_WEEK) - 1
        }

        //showing days from last month in the beginning of the week till month starts
        //setting the date index for the first shown date by subtracting the dayOfWeek of the calendars first date
        calendarIterator.time = Date(month.time.time + 1.daysInTimeToMillis() * (position + 1 - dayOfWeek))

        holder.bind(calendarIterator.get(Calendar.DAY_OF_MONTH),
            calendarIterator.isInSameMonthAs(month),
            calendarIterator.isSameDayAs(currentDay))
    }

    override fun getItemCount(): Int = 42

    class ViewHolder(private val binding: CardNurseShiftPlanCellBinding, private val context: Context?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: Int, isInCurrentMonth: Boolean, isActive: Boolean = false) {
            binding.weekDayText.text = day.toString()
            if (isActive && context != null) {
                binding.root.setCardBackgroundColor(context.getColor(R.color.teal_700))
            } else if (!isInCurrentMonth && context != null) {
                binding.weekDayText.setTextColor(context.getColor(R.color.teal_700))
            }
        }
    }
}
