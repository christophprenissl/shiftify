package com.christophprenissl.shiftify.model.entity

data class StationPlanMonth(var yearMonthName: String,
                            var voteBeginDate: Long,
                            var voteEndDate: Long,
                            var minimalNurseCountEarly: Int,
                            var minimalNurseCountLate: Int,
                            var minimalNurseCountNight: Int,
                            var isValid: Boolean,
                            var nursePlanElementList: Map<String, List<PlanElement>>)
