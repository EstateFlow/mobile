package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("userId") val userId: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("role") val role: Role,
    @SerializedName("isEmailVerified") val isEmailVerified: Boolean
)