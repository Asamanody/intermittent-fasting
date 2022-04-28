package com.el3asas.regym.db.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "PlanInfo")
class PlanInfo(
    @PrimaryKey
    var id: Int,
    var isSyamStarted: Boolean,
    var amountTime: Int,
    var startTime: Long,
    var endTime: Long
)