package com.el3asas.regym.db.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "ProfileInfo")
class ProfileInfo {
    @PrimaryKey
    var id = 0
    var age = 0
    var weight = 0
    var gender: String? = null
    var height = 0
    var effort: String? = null
    var bodyStatus = 0

    constructor() {}
    constructor(
        id: Int,
        age: Int,
        weight: Int,
        gender: String?,
        height: Int,
        jobStatus: String?,
        bodyStatus: Int
    ) {
        this.id = id
        this.age = age
        this.weight = weight
        this.gender = gender
        this.height = height
        this.effort = jobStatus
        this.bodyStatus = bodyStatus
    }
}