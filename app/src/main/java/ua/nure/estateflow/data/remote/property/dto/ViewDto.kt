package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName

data class ViewDto(
    @SerializedName("id") val id : String,
    @SerializedName("propertyId") val propertyId : String,
    @SerializedName("viewedAt") val viewedAt : String,
    @SerializedName("viewerIp") val viewerIp : String,
)
