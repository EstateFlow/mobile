package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class SignInResponse (
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
)