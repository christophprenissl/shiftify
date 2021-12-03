package com.christophprenissl.shiftify.viewmodels.nurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.christophprenissl.shiftify.utils.monthYearString
import java.util.*

class NurseShiftsViewModel: ViewModel() {

    private val _monthYearText = MutableLiveData<String>()
    val monthYear: LiveData<String> = _monthYearText

    var calendar: Calendar = Calendar.getInstance()

    init {
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value =  calendar.monthYearString()
    }

    fun addMonth() {
        calendar.add(Calendar.MONTH, 1)
        _monthYearText.value = calendar.monthYearString()
    }

    fun subtractMonth() {
        calendar.add(Calendar.MONTH, -1)
        _monthYearText.value = calendar.monthYearString()
    }
}