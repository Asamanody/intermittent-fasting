package com.el3asas.regym.db.models

import androidx.annotation.Keep
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Keep
class DataConverter {
    @TypeConverter
    fun fromArrayToString(countryLang: BooleanArray?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<BooleanArray?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun fromStringToArray(countryLangString: String?): BooleanArray? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<BooleanArray?>() {}.type
        return gson.fromJson(countryLangString, type)
    }
}