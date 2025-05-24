package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName

enum class PropertyType(val type: String) {
    @SerializedName("house") house("house"),
    @SerializedName("apartment") apartment("apartment")
}