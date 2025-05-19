package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("message") val message: String
)
