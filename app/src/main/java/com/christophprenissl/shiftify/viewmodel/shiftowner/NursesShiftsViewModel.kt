package com.christophprenissl.shiftify.viewmodel.shiftowner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.christophprenissl.shiftify.model.dto.NurseDto
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.ShiftOwnerPlan
import com.christophprenissl.shiftify.util.dayMonthYearString
import com.christophprenissl.shiftify.util.mapper.NurseMapper
import com.christophprenissl.shiftify.util.monthYearString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import timber.log.Timber.w
import java.util.*

class NursesShiftsViewModel: ViewModel() {

    private val nurseMapper = NurseMapper()

    private val _shiftOwnerStationValue = MutableLiveData<String>()

    private val auth = FirebaseAuth.getInstance()
    private var usersDatabase: DatabaseReference
    private var shiftOwnerStationValueDatabase: DatabaseReference

    private val _monthYearText = MutableLiveData<String>()
    val monthYearText: LiveData<String> = _monthYearText

    private val _shiftOwnerPlan = MutableLiveData<ShiftOwnerPlan>()
    val shiftOwnerPlan: LiveData<ShiftOwnerPlan> = _shiftOwnerPlan

    var currentDayCalendar: Calendar = Calendar.getInstance()
    var monthCalendar: Calendar = currentDayCalendar.clone() as Calendar
    var chosenDayCalendar: Calendar = Calendar.getInstance()

    init {
        setupMonthCalendar()
        shiftOwnerStationValueDatabase = Firebase.database.reference.child("users")
            .child(auth.currentUser!!.uid)
            .child("stationValue")

        usersDatabase = Firebase.database.reference.child("users")
        val plansListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val nursesMap = dataSnapshot.getValue<Map<String, NurseDto>>()?.filter {
                    it.value.stationValue == _shiftOwnerStationValue.value
                } ?. mapValues {
                   nurseMapper.toEntity(it.value)
                }

                val planElementMap: MutableMap<String, Map<String, PlanElement>> = mutableMapOf()
                nursesMap?.forEach {
                    val nursePlanMap: MutableMap<String, PlanElement> = mutableMapOf()
                    it.value.nursePlanMonths.map { month ->
                        for (element in month.value.planElementList) {
                            nursePlanMap[element.date.dayMonthYearString()] = element
                        }
                    }
                    planElementMap[it.value.firstName + " " + it.value.lastName] = nursePlanMap
                }

                _shiftOwnerPlan.value = ShiftOwnerPlan(planElementMap)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                w(databaseError.message)
            }
        }

        val shiftOwnerListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _shiftOwnerStationValue.value = dataSnapshot.getValue<String>()
                usersDatabase.addValueEventListener(plansListener)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                w(databaseError.message)
            }
        }
        shiftOwnerStationValueDatabase.addValueEventListener(shiftOwnerListener)
    }

    private fun setupMonthCalendar() {
        monthCalendar.firstDayOfWeek = Calendar.MONDAY
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        _monthYearText.value = monthCalendar.monthYearString()
    }

    fun setChosenDayCalendar(day: Int) {
        chosenDayCalendar.set(Calendar.DAY_OF_MONTH, day)
    }
}
