package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("email") val email: String
)
