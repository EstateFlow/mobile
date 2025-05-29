package ua.nure.estateflow.data.datasource.profile

import ua.nure.estateflow.data.remote.auth.dto.Role

data class Profile(
    val login: String,
    val username: String? = null,
    val role: Role? = null,
    val isEmailVerified: Boolean? = null,
    val bio: String = "",
    val avatarUrl: String = "",
)
