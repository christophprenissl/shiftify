package com.christophprenissl.shiftify.view.nurse

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.CardNurseShiftPlanCellBinding
import com.christophprenissl.shiftify.util.isInSameMonthAs
import com.christophprenissl.shiftify.util.isSameDayAs
import com.christophprenissl.shiftify.viewmodel.nurse.NurseShiftsViewModel
import com.christophprenissl.shiftify.viewmodel.nurse.PlanElementListener
import java.util.*

class ShiftPlanAdapter constructor(private val context: Context?,
                                   private val viewModel: NurseShiftsViewModel,
                                   private val onClick: PlanElementListener) : RecyclerView.Adapter<ShiftPlanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardNurseShiftPlanCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val planElementAtPos = viewModel.planElementsOfMonth.value!![position]

        holder.bind(position,
            planElementAtPos.date.get(Calendar.DAY_OF_MONTH).toString(),
            viewModel.monthCalendar.isInSameMonthAs(planElementAtPos.date),
            viewModel.currentDayCalendar.isSameDayAs(planElementAtPos.date),
            onClick)
    }

    override fun getItemCount(): Int = 42

    class ViewHolder(private val binding: CardNurseShiftPlanCellBinding,
                     private val context: Context?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(planElementIndex: Int, dateText: String, isInCurrentMonth: Boolean, isActive: Boolean = false,
                 onClick: PlanElementListener) {

            binding.weekDayText.text = dateText
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

            binding.root.setOnClickListener { onClick.onPlanElementClick(planElementIndex) }
        }
    }
}
