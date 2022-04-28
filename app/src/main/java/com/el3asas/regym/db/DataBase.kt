package com.el3asas.regym.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.el3asas.regym.db.daos.DataDao
import com.el3asas.regym.db.daos.LongPlanDao
import com.el3asas.regym.db.daos.PlanInfoDao
import com.el3asas.regym.db.models.DataConverter
import com.el3asas.regym.db.models.LongPlan
import com.el3asas.regym.db.models.PlanInfo
import com.el3asas.regym.db.models.ProfileInfo

@Database(
    entities = [ProfileInfo::class, PlanInfo::class, LongPlan::class],
    version = 80,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun dataDao(): DataDao?
    abstract fun longPlanDao(): LongPlanDao?
    abstract fun planInfoDao(): PlanInfoDao?
}