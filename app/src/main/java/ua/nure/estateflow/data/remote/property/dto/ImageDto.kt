package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName

data class ImageDto (
    @SerializedName("id") val id: String,
    @SerializedName("propertyId") val propertyId: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("isPrimary") val isPrimary: Boolean,
)