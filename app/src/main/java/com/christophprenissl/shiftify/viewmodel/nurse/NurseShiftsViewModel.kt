package com.christophprenissl.shiftify.viewmodel.nurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.PlanElementApprovalState
import com.christophprenissl.shiftify.model.entity.Shift
import com.christophprenissl.shiftify.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class NurseShiftsViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference

    private val _monthYearText = MutableLiveData<String>()
    val monthYear: LiveData<String> = _monthYearText

    private val _planElementsOfMonth = MutableLiveData<List<PlanElement>>()
    val planElementsOfMonth: LiveData<List<PlanElement>> = _planElementsOfMonth

    private var chosenIdx: Int = 0
        set(value) {
            field = value
            initializeAboutToSavePlanElement()
        }

    private val _aboutToSavePlanElement = MutableLiveData<PlanElement?>()
    val aboutToSavePlanElement: LiveData<PlanElement?> = _aboutToSavePlanElement

    var currentDayCalendar: Calendar = Calendar.getInstance()
    var monthCalendar: Calendar = currentDayCalendar.clone() as Calendar

    private val calendarIterator: Calendar = Calendar.getInstance()

    init {
        monthCalendar.firstDayOfWeek = Calendar.MONDAY
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value =  monthCalendar.monthYearString()
        createPlanElements()
    }

    private fun createPlanElements() {
        val list = ArrayList<PlanElement>()
        for (i in 0..41) {
            //set start of week to Monday
            val dayOfWeek = when(monthCalendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SUNDAY -> 7
                else -> monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
            }

            //showing days from last month in the beginning of the week till month starts
            //setting the date index for the first shown date by subtracting the dayOfWeek of the calendars first date
            calendarIterator.time = Date(monthCalendar.time.time + 1.daysInTimeToMillis() * (i + 1 - dayOfWeek))
            val planElement = PlanElement(
                calendarIterator.clone() as Calendar,
                hashMapOf(),
                PlanElementApprovalState.PROCESSING
            )
            list.add(planElement)
        }
        _planElementsOfMonth.value = list
    }

    private fun updateChosenPlanElement(shift: Shift, priority: Int) {
        aboutToSavePlanElement.value!!.priorityMap[shift] = priority
    }

    fun saveChosenPlanElement() {
        _planElementsOfMonth.value!![chosenIdx].priorityMap =
            aboutToSavePlanElement.value!!.priorityMap
    }

    fun unChooseElement() {
        _aboutToSavePlanElement.value = null
    }

    private fun initializeAboutToSavePlanElement() {
        setAboutToSavePlanElement()
        if (aboutToSavePlanElement.value!!.priorityMap.isEmpty()) {
            initializePriorityMap()
        }
    }

    private fun initializePriorityMap() {
        setPriority(EARLY_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setPriority(LATE_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setPriority(NIGHT_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setPriority(FREE_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setPriority(HOLIDAY_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
    }

    fun addMonth() {
        monthCalendar.add(Calendar.MONTH, 1)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        createPlanElements()
        _monthYearText.value = monthCalendar.monthYearString()
    }

    fun subtractMonth() {
        monthCalendar.add(Calendar.MONTH, -1)
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        createPlanElements()
        _monthYearText.value = monthCalendar.monthYearString()
    }

    fun choosePlanElement(index: Int) {
        chosenIdx = index
    }

    private fun setAboutToSavePlanElement() {
        _aboutToSavePlanElement.value = _planElementsOfMonth.value!![chosenIdx].getDeepCopy()
    }

    fun setPriority(shiftTitle: String, priorityTitle: String) {
        val shift = when(shiftTitle) {
            FREE_SHIFT_NAME -> Shift(shiftTitle, 0, 0, 1440)
            HOLIDAY_SHIFT_NAME -> Shift(shiftTitle, 0, 0, 1440)
            EARLY_SHIFT_NAME -> Shift(shiftTitle, 6, 45, 480)
            LATE_SHIFT_NAME -> Shift(shiftTitle, 12, 30, 480)
            NIGHT_SHIFT_NAME -> Shift(shiftTitle, 20, 15, 645)
            else -> null
        }

        val priority = when(priorityTitle) {
            WISH_SHIFT_PRIORITY_NAME -> WISH_SHIFT_PRIORITY
            HIGH_PRIORITY_NAME -> HIGH_PRIORITY
            NEUTRAL_PRIORITY_NAME -> NEUTRAL_PRIORITY
            NEGATIVE_PRIORITY_NAME -> NEGATIVE_PRIORITY
            else -> 0
        }

        if (shift != null) {
            updateChosenPlanElement(shift, priority)
        }
    }
}