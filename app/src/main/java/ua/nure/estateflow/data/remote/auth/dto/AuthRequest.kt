package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class AuthRequest(
    @SerializedName("username") val name: String? = null,
    @SerializedName("email") val login: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String = "renter_buyer"
)
