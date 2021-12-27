package com.christophprenissl.shiftify.viewmodel.nurse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.christophprenissl.shiftify.model.dto.NursePlanMonthDto
import com.christophprenissl.shiftify.model.entity.*
import com.christophprenissl.shiftify.util.*
import com.christophprenissl.shiftify.util.mapper.NursePlanMonthMapper
import com.christophprenissl.shiftify.viewmodel.nurse.state.NurseLoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NurseViewModel: ViewModel() {

    private val planMonthMapper = NursePlanMonthMapper()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference


    private val _monthYearText = MutableLiveData<String>()
    val monthYear: LiveData<String> = _monthYearText

    private val _nursePlanMonths = MutableLiveData<HashMap<String,NursePlanMonth>>()
    val nursePlanMonths: LiveData<HashMap<String, NursePlanMonth>> = _nursePlanMonths

    private val _nurseShiftsState = MutableLiveData<NurseLoginState>()
    val nurseLoginState: LiveData<NurseLoginState> = _nurseShiftsState

    private val _aboutToSavePlanElement = MutableLiveData<PlanElement?>()
    val aboutToSavePlanElement: LiveData<PlanElement?> = _aboutToSavePlanElement


    var currentDayCalendar: Calendar = Calendar.getInstance()
    var monthCalendar: Calendar = currentDayCalendar.clone() as Calendar
    private var chosenIdx: Int = 0
        set(value) {
            field = value
            initializeAboutToSavePlanElement()
        }

    init {
        setupMonthCalendar()
        createMonthPlanElements()
        if (auth.currentUser != null) {
            _nurseShiftsState.value = NurseLoginState.USER_LOGGED_IN

            val userId = auth.currentUser!!.uid
            database = Firebase.database.reference
                .child("users").child(userId).child("planMonths")

            val planElementsListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val planMonths = dataSnapshot.getValue<Map<String, NursePlanMonthDto>>()
                    if (planMonths != null) {
                        for (planMonth in planMonths) {
                            _nursePlanMonths.value!![planMonth.key] = planMonthMapper.toEntity(planMonth.value)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    i("$databaseError")
                }
            }
            database.addValueEventListener(planElementsListener)
        } else {
            _nurseShiftsState.value = NurseLoginState.USER_LOGGED_OUT
        }
    }

    fun setLoginState() {
        if (auth.currentUser != null) {
            _nurseShiftsState.value = NurseLoginState.USER_LOGGED_IN
        } else {
            _nurseShiftsState.value = NurseLoginState.USER_LOGGED_OUT
        }
    }

    fun logoutUser() {
        auth.signOut()
        _nurseShiftsState.value = NurseLoginState.USER_LOGGED_OUT
    }

    private fun setupMonthCalendar() {
        monthCalendar.firstDayOfWeek = Calendar.MONDAY
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value = monthCalendar.monthYearString()
    }

    private fun createMonthPlanElements() {
        _nursePlanMonths.value = hashMapOf()
        val planMonth = NursePlanMonth(
            _monthYearText.value!!,
            listOf()
        )

        _nursePlanMonths.value!![_monthYearText.value!!] = planMonth

        val list = ArrayList<PlanElement>()
        val calendarIterator = monthCalendar.clone() as Calendar
        var i = 0
        while (calendarIterator.isInSameMonthAs(monthCalendar)) {
            //showing days from last month in the beginning of the week till month starts
            //setting the date index for the first shown date by subtracting the dayOfWeek of the calendars first date
            val planElement = PlanElement(
                calendarIterator.clone() as Calendar,
                hashMapOf(),
                PlanElementApprovalState.PROCESSING
            )
            list.add(planElement)
            i++
            calendarIterator.time = Date(monthCalendar.time.time + i.daysInTimeToMillis())
        }
        _nursePlanMonths.value!![_monthYearText.value]!!.planElementList = list
    }

    private fun updateChosenPlanElement(shift: Shift, priority: Int) {
        aboutToSavePlanElement.value!!.priorityMap[shift.name] = priority
    }

    fun saveChosenPlanElementInMonth() {
        _nursePlanMonths.value!![_monthYearText.value]!!.planElementList[chosenIdx].priorityMap =
            aboutToSavePlanElement.value!!.priorityMap
        val children = mutableMapOf<String, NursePlanMonthDto>()
        for (month in _nursePlanMonths.value!!) {
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
        setShiftAndPriority(EARLY_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setShiftAndPriority(LATE_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setShiftAndPriority(NIGHT_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setShiftAndPriority(FREE_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
        setShiftAndPriority(HOLIDAY_SHIFT_NAME, NEUTRAL_PRIORITY_NAME)
    }

    fun addMonth() {
        monthCalendar.add(Calendar.MONTH, 1)
        setupMonthCalendar()
        createMonthPlanElements()
    }

    fun subtractMonth() {
        monthCalendar.add(Calendar.MONTH, -1)
        setupMonthCalendar()
        createMonthPlanElements()
    }

    fun choosePlanElement(index: Int) {
        chosenIdx = index
    }

    fun findAndSetNextPlanElement(): Boolean {
        if (checkIfLastDayOfCalendarView()) {
            return false
        }

        saveChosenPlanElementInMonth()
        choosePlanElement(chosenIdx + 1)
        return true
    }

    fun checkIfLastDayOfCalendarView(): Boolean {
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
            _nursePlanMonths.value!![_monthYearText.value]!!.planElementList[chosenIdx].getDeepCopy()
    }

    fun setShiftAndPriority(shiftTitle: String, priorityTitle: String) {
        val shift = when(shiftTitle) {
            FREE_SHIFT_NAME -> Shift(shiftTitle, FREE_SHIFT_STARTING_MILLIS, FREE_SHIFT_ENDING_MILLIS)
            HOLIDAY_SHIFT_NAME -> Shift(shiftTitle, HOLIDAY_SHIFT_STARTING_MILLIS, HOLIDAY_SHIFT_ENDING_MILLIS)
            EARLY_SHIFT_NAME -> Shift(shiftTitle, EARLY_SHIFT_STARTING_MILLIS, EARLY_SHIFT_ENDING_MILLIS)
            LATE_SHIFT_NAME -> Shift(shiftTitle, LATE_SHIFT_STARTING_MILLIS, LATE_SHIFT_ENDING_MILLIS)
            NIGHT_SHIFT_NAME -> Shift(shiftTitle, NIGHT_SHIFT_STARTING_MILLIS, NIGHT_SHIFT_ENDING_MILLIS)
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

    fun getMonthPlanList(): List<PlanElement> {
        return _nursePlanMonths.value!![_monthYearText.value]!!.planElementList
    }
}
