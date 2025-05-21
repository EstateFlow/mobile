package ua.nure.estateflow.data.datasource.profile

import kotlinx.coroutines.flow.StateFlow

interface ProfileDataSource {
    val profileFlow: StateFlow<Profile?>
    val profile: Profile?
    suspend fun setProfile(profile: Profile)
}