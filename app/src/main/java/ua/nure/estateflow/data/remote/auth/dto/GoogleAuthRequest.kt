package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class GoogleAuthRequest (
    @SerializedName("code") val idToken: String,
    @SerializedName("role") val role: String = Role.renter_buyer.role
)