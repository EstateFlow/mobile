package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName

enum class PropertyStatus(val status: String) {
    @SerializedName("active") active("active"),
    @SerializedName("inactive") inactive("inactive"),
    @SerializedName("sold") sold("sold"),
    @SerializedName("rented") rented("rented")
}