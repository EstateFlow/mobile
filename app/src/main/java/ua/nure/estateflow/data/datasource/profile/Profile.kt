package ua.nure.estateflow.data.datasource.profile

import ua.nure.estateflow.data.remote.auth.dto.Role

data class Profile(
    val login: String,
    val username: String,
    val role: Role,
    val isEmailVerified: Boolean
)
