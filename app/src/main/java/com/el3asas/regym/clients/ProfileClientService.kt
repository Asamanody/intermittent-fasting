package com.el3asas.regym.clients

import com.el3asas.regym.db.daos.DataDao
import com.el3asas.regym.db.models.ProfileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileClientService(private val dataDao: DataDao) {
    suspend fun insertInfo(
        profileInfo: ProfileInfo?
    ) = withContext(Dispatchers.IO) {
        dataDao.insertInfo(profileInfo)
    }

    suspend fun getInfoData(
        id: Int
    ) = withContext(Dispatchers.IO) {
        dataDao.getInfoDataAsync(id)
    }

    suspend fun getCount() = withContext(Dispatchers.IO) {
        dataDao.count()
    }
}