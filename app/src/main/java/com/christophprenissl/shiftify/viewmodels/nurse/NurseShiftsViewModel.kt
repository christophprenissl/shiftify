package com.christophprenissl.shiftify.viewmodels.nurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.christophprenissl.shiftify.utils.monthYearString
import java.util.*

class NurseShiftsViewModel: ViewModel() {

    private val _monthYearText = MutableLiveData<String>()
    val monthYear: LiveData<String> = _monthYearText

    var currentDayCalendar: Calendar = Calendar.getInstance()
    var monthCalendar: Calendar = currentDayCalendar.clone() as Calendar

    init {
        monthCalendar.firstDayOfWeek = Calendar.MONDAY
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value =  monthCalendar.monthYearString()
    }

    fun addMonth() {
        monthCalendar.add(Calendar.MONTH, 1)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value = monthCalendar.monthYearString()
    }

    fun subtractMonth() {
        monthCalendar.add(Calendar.MONTH, -1)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value = monthCalendar.monthYearString()
    }
}