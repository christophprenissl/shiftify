package com.christophprenissl.shiftify.views.nurse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christophprenissl.shiftify.databinding.CardShiftPlanCellBinding
import com.christophprenissl.shiftify.utils.daysInTimeToMillis
import java.util.*

class ShiftPlanAdapter constructor(private val firstDayOfMonth: Calendar) : RecyclerView.Adapter<ShiftPlanAdapter.ViewHolder>() {

    private val calendarCopy = firstDayOfMonth.clone() as Calendar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardShiftPlanCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //set start of week to Monday
        val dayOfWeek = when(firstDayOfMonth.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 7
            else -> firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1
        }

        //showing days from last month in the beginning of the week till month starts
        //setting the date index for the first shown date by subtracting the dayOfWeek of the calendars first date
        calendarCopy.time = Date(firstDayOfMonth.time.time + 1.daysInTimeToMillis() * (position + 1 - dayOfWeek))

        holder.bind(calendarCopy.get(Calendar.DAY_OF_MONTH))
    }

    override fun getItemCount(): Int = 42

    class ViewHolder(private val binding: CardShiftPlanCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weekDay: Int) {
            binding.weekDayText.text = weekDay.toString()
        }
    }
}
