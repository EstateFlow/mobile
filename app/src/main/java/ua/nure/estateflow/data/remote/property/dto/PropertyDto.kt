package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName

data class PropertyDto(
    @SerializedName("id") val id: String,
    @SerializedName("ownerId") val ownerId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("propertyType") val propertyType: String,
    @SerializedName("transactionType") val transactionType: String,
    @SerializedName("price") val price: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("size") val size: String,
    @SerializedName("rooms") val rooms: Int,
    @SerializedName("address") val address: String,
    @SerializedName("status") val status: String,
    @SerializedName("documentUrl") val documentUrl: String,
    @SerializedName("verificationComments") val verificationComments: String,
    @SerializedName("isVerified") val isVerified: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("images") val images: List<ImageDto>,
    @SerializedName("views") val views: List<ViewDto>
)


