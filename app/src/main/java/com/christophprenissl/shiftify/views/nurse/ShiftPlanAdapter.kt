package com.christophprenissl.shiftify.views.nurse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christophprenissl.shiftify.databinding.CardShiftPlanCellBinding
import com.christophprenissl.shiftify.utils.daysInTimeToMillis
import java.util.*

class ShiftPlanAdapter constructor(private val calendar: Calendar) : RecyclerView.Adapter<ShiftPlanAdapter.ViewHolder>() {

    private val calendarCopy = calendar.clone() as Calendar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardShiftPlanCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //showing days from last month in the beginning of the week till month starts
        //TODO: only show days from next month till week ends
        calendarCopy.time = Date(calendar.time.time + 1.daysInTimeToMillis() * (position + 2 - calendar.get(Calendar.DAY_OF_WEEK)))

        holder.bind(calendarCopy.get(Calendar.DAY_OF_MONTH))
    }


    override fun getItemCount(): Int = 42

    class ViewHolder(private val binding: CardShiftPlanCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weekDay: Int) {
            binding.weekDayText.text = weekDay.toString()
        }
    }
}