package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.local.entity.ImageEntity

data class ImageDto (
    @SerializedName("id") val id: String,
    @SerializedName("propertyId") val propertyId: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("isPrimary") val isPrimary: Boolean,
)

fun ImageDto.toEntity() =
    ImageEntity (
        id = id,
        propertyId = propertyId,
        imageUrl = imageUrl,
        isPrimary = isPrimary,
    )