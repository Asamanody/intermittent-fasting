package com.el3asas.regym.db.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "LongPlan")
class LongPlan(
    @PrimaryKey
    var id: Int,
    var selected: BooleanArray,
    var hour: Int,
    var minute: Int,
    var am_pm: Int,
    var endTime: Long,
    var selectedPlan: Int,
    var isWeek_cheek: Boolean
)