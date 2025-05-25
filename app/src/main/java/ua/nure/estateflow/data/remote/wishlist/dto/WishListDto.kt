package ua.nure.estateflow.data.remote.wishlist.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class WishListDto(
    @SerializedName("propertyId") val propertyId: String
)
