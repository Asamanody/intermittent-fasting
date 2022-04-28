package com.el3asas.regym.db

import android.content.Context
import com.el3asas.regym.db.daos.DataDao
import com.el3asas.regym.db.daos.LongPlanDao
import com.el3asas.regym.db.daos.PlanInfoDao
import com.el3asas.regym.db.models.LongPlan
import com.el3asas.regym.db.models.PlanInfo

class Repository {
    /*private var dataDao: DataDao? = null
    private var planInfoDao: PlanInfoDao? = null
    private var longPlanDao: LongPlanDao? = null

    fun setPlanInfo(planInfo: PlanInfo?) {
        return planInfoDao!!.setPlanInfo(planInfo)
    }

    suspend fun planInfo() = planInfoDao!!.planInfo.await()

    fun deletePlan(id: Int) {
        return planInfoDao!!.deletePlan(id)
    }

    fun setLongPlanInfo(longPlan: LongPlan?) {
        return longPlanDao!!.insertLongPlan(longPlan)
    }

    fun deleteLongPlan(id: Int) {
        return longPlanDao!!.deleteLongPlan(id)
    }

    suspend fun getLongPlanInfo(id: Int) = longPlanDao!!.getLongPlanAsync(id).await()*/
}