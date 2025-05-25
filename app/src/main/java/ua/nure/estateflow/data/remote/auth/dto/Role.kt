package ua.nure.estateflow.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

enum class Role(val role: String) {
    @SerializedName("renter_buyer") renter_buyer("renter_buyer"),
    @SerializedName("private_seller") private_seller("private_seller"),
    @SerializedName("agency") agency("agency"),
    @SerializedName("moderator") moderator("moderator"),
    @SerializedName("admin") admin("admin")
}