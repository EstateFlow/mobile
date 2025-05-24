package ua.nure.estateflow.data.remote.property.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializer

enum class TransactionType(val type: String) {
    @SerializedName("rent") rent("rent"),
    @SerializedName("sale") sale("sale")
}
