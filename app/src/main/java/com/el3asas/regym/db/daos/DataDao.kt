package com.el3asas.regym.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.el3asas.regym.db.models.ProfileInfo

@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(profileInfo: ProfileInfo?)

    @Query("select * from ProfileInfo where id =:id")
    suspend fun getInfoDataAsync(id: Int): ProfileInfo

    @Query("select count(id) from ProfileInfo")
    suspend fun count(): Int
}