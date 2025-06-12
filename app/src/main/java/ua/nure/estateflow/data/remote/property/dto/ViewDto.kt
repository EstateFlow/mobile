package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.local.entity.ViewEntity

data class ViewDto(
    @SerializedName("id") val id : String,
    @SerializedName("propertyId") val propertyId : String,
    @SerializedName("viewedAt") val viewedAt : String,
)

fun ViewDto.toEntity() =
    ViewEntity(
        id = id,
        propertyId = propertyId,
        viewedAt = viewedAt,
        )