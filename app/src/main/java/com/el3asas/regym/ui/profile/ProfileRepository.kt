package com.el3asas.regym.ui.profile

import com.el3asas.regym.R
import com.el3asas.regym.clients.ProfileClientService
import com.el3asas.regym.db.models.ProfileInfo

class ProfileRepository(private val profileClientService: ProfileClientService) {
    suspend fun insertInfo(
        profileInfo: ProfileInfo?,
        onSuccess: (Boolean) -> Unit,
        onError: (msg: String, res: Int) -> Unit,
        onLoading: (Boolean) -> Unit
    ) {
        onLoading(true)
        try {
            profileClientService.insertInfo(profileInfo)
            onSuccess(true)
        } catch (e: Exception) {
            onError(e.message.toString(), R.drawable.ic_error)
        }
    }

    suspend fun getInfoData(
        id: Int,
        onError: (msg: String, res: Int) -> Unit,
        onLoading: (Boolean) -> Unit,
        onSuccess: (ProfileInfo) -> Unit
    ) {
        onLoading(true)
        try {
            onSuccess(profileClientService.getInfoData(id))
        } catch (e: Exception) {
            onError(e.message.toString(), R.drawable.ic_error)
        }
    }

    suspend fun getCount() = profileClientService.getCount()

}