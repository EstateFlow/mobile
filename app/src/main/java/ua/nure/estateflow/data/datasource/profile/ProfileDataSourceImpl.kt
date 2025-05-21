package ua.nure.estateflow.data.datasource.profile

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileDataSourceImpl(
) : ProfileDataSource {
    private val _profileFlow = MutableStateFlow<Profile?>(null)
    private var _profile: Profile? = null
    
    override suspend fun setProfile(profile: Profile) {
        _profileFlow.emit(profile)
        _profile = profile
    }

    override val profileFlow = _profileFlow.asStateFlow()

    override val profile: Profile?
        get() = _profile
}