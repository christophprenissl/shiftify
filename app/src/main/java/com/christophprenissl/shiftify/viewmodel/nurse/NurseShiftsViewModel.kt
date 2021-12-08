package com.christophprenissl.shiftify.viewmodel.nurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.christophprenissl.shiftify.util.daysInTimeToMillis
import com.christophprenissl.shiftify.util.monthYearString
import java.util.*
import kotlin.collections.ArrayList

class NurseShiftsViewModel: ViewModel() {

    private val _monthYearText = MutableLiveData<String>()
    val monthYear: LiveData<String> = _monthYearText

    private val _daysOfMonthList = MutableLiveData<List<Calendar>>()
    val daysOfMonthList: LiveData<List<Calendar>> = _daysOfMonthList

    private val _chosenDay = MutableLiveData<Calendar?>()
    val chosenDay: LiveData<Calendar?> = _chosenDay

    var currentDayCalendar: Calendar = Calendar.getInstance()
    var monthCalendar: Calendar = currentDayCalendar.clone() as Calendar

    private val calendarIterator: Calendar = Calendar.getInstance()

    init {
        monthCalendar.firstDayOfWeek = Calendar.MONDAY
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value =  monthCalendar.monthYearString()
        getDaysOfMonth()
    }

    private fun getDaysOfMonth() {
        val list = ArrayList<Calendar>()
        for (i in 0..41) {
            //set start of week to Monday
            val dayOfWeek = when(monthCalendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SUNDAY -> 7
                else -> monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
            }

            //showing days from last month in the beginning of the week till month starts
            //setting the date index for the first shown date by subtracting the dayOfWeek of the calendars first date
            calendarIterator.time = Date(monthCalendar.time.time + 1.daysInTimeToMillis() * (i + 1 - dayOfWeek))
            list.add(calendarIterator.clone() as Calendar)
        }
        _daysOfMonthList.value = list
    }

    fun addMonth() {
        monthCalendar.add(Calendar.MONTH, 1)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        getDaysOfMonth()
        _monthYearText.value = monthCalendar.monthYearString()
    }

    fun subtractMonth() {
        monthCalendar.add(Calendar.MONTH, -1)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        getDaysOfMonth()
        _monthYearText.value = monthCalendar.monthYearString()
    }

    fun chooseDay(day: Calendar) {
        _chosenDay.value = day
    }

    fun unChooseDay() {
        _chosenDay.value = null
    }

    fun setPriority(shiftTitle: String, priority: String) {
        //TODO: implement method
    }
}