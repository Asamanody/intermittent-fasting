package com.el3asas.regym.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.el3asas.regym.db.models.LongPlan

@Dao
interface LongPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLongPlan(longPlan: LongPlan?)

    @Query("select * from longplan where id =:id")
    suspend fun getLongPlanAsync(id: Int): LongPlan

    @Query("delete from longplan where id=:id")
    suspend fun deleteLongPlan(id: Int)
}