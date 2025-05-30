package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("username") val username: String? = null,
    @SerializedName("avatarUrl") val avatarUrl: String? = null,
    @SerializedName("bio") val bio: String? = null,
)

data class UpdateUserDto(
    @SerializedName("username") val username: String? = null,
    @SerializedName("avatarUrl") val avatarUrl: String? = null,
    @SerializedName("bio") val bio: String? = null,
    @SerializedName("updatedAt") val updatedAt: String
)
