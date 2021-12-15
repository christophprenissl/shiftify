package com.christophprenissl.shiftify.viewmodel.nurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.christophprenissl.shiftify.model.dto.NursePlanMonthDto
import com.christophprenissl.shiftify.model.entity.*
import com.christophprenissl.shiftify.util.*
import com.christophprenissl.shiftify.util.mapper.NursePlanMonthMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NurseShiftsViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private var database: DatabaseReference

    private val _monthYearText = MutableLiveData<String>()
    val monthYear: LiveData<String> = _monthYearText

    private val _planMonths = MutableLiveData<HashMap<String,NursePlanMonth>>()
    val nursePlanMonths: LiveData<HashMap<String, NursePlanMonth>> = _planMonths

    private val planMonthMapper = NursePlanMonthMapper()

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
        val userId = auth.currentUser!!.uid
        database = Firebase.database.reference
            .child("users").child(userId).child("planMonths")

        monthCalendar.firstDayOfWeek = Calendar.MONDAY
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value =  monthCalendar.monthYearString()

        val planElementsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val planMonths = dataSnapshot.getValue<Map<String, NursePlanMonthDto>>()
                if (planMonths != null) {
                    for (planMonth in planMonths) {
                        _planMonths.value!![planMonth.key] = planMonthMapper.toEntity(planMonth.value)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                i("$databaseError")
            }
        }
        database.addValueEventListener(planElementsListener)

        createPlanElements()
    }

    fun getMonthPlanList(): List<PlanElement> {
        return _planMonths.value!![_monthYearText.value]!!.planElementList
    }

    private fun getPlanElementPriorityAt(idx: Int): PlanElement {
        return getMonthPlanList()[idx]
    }

    private fun createPlanElements() {
        _planMonths.value = hashMapOf()
        val planMonth = NursePlanMonth(
            _monthYearText.value!!,
            listOf()
        )
        _planMonths.value!![_monthYearText.value!!] = planMonth
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
        _planMonths.value!![_monthYearText.value]!!.planElementList = list
    }

    private fun updateChosenPlanElement(shift: Shift, priority: Int) {
        aboutToSavePlanElement.value!!.priorityMap[shift.name] = priority
    }

    fun saveChosenPlanElementInMonth() {
        _planMonths.value!![_monthYearText.value]!!.planElementList[chosenIdx].priorityMap =
            aboutToSavePlanElement.value!!.priorityMap
        val children = mutableMapOf<String, NursePlanMonthDto>()
        for (month in _planMonths.value!!) {
            children[month.key] = planMonthMapper.fromEntity(month.value)
        }

        database.updateChildren(children as Map<String, NursePlanMonthDto>)
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

    fun findAndSetNextPlanElement(): Boolean {
        if (checkIfLastDayOfMonth()) {
            return false
        }

        saveChosenPlanElementInMonth()
        choosePlanElement(chosenIdx + 1)
        return true
    }

    fun checkIfLastDayOfMonth(): Boolean {
        if (getMonthPlanList().lastIndex == chosenIdx) {
            return true
        } else if (aboutToSavePlanElement.value!!.date.get(Calendar.DAY_OF_MONTH)
            > getMonthPlanList()[chosenIdx+1].date.get(Calendar.DAY_OF_MONTH)) {
            return true
        }
        return false
    }

    private fun setAboutToSavePlanElement() {
        _aboutToSavePlanElement.value =
            _planMonths.value!![_monthYearText.value]!!.planElementList[chosenIdx].getDeepCopy()
    }

    fun setPriority(shiftTitle: String, priorityTitle: String) {
        val shift = when(shiftTitle) {
            FREE_SHIFT_NAME -> Shift(shiftTitle, hoursMinutesToMillis(0,0), hoursMinutesToMillis(24,0))
            HOLIDAY_SHIFT_NAME -> Shift(shiftTitle, hoursMinutesToMillis(0,0), hoursMinutesToMillis(24,0))
            EARLY_SHIFT_NAME -> Shift(shiftTitle, hoursMinutesToMillis(6,45), hoursMinutesToMillis(14, 30))
            LATE_SHIFT_NAME -> Shift(shiftTitle, hoursMinutesToMillis(12, 30), hoursMinutesToMillis(20, 30))
            NIGHT_SHIFT_NAME -> Shift(shiftTitle, hoursMinutesToMillis(20, 15), hoursMinutesToMillis(24 + 7, 0))
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