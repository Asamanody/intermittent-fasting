package com.el3asas.regym.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.el3asas.regym.db.models.PlanInfo

@Dao
interface PlanInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setPlanInfo(planInfo: PlanInfo?)

    @Query("delete from planinfo where id=:id")
    suspend fun deletePlan(id: Int)

}