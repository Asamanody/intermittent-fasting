package com.el3asas.regym.di

import androidx.room.Room
import com.el3asas.regym.BuildConfig
import com.el3asas.regym.db.DataBase
import com.el3asas.regym.utils.Pref
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val DataBaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), DataBase::class.java, "regymDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<DataBase>().dataDao() }
    single { get<DataBase>().longPlanDao() }
    single { get<DataBase>().planInfoDao() }

    single { Pref(BuildConfig.APPLICATION_ID, get()) }
}