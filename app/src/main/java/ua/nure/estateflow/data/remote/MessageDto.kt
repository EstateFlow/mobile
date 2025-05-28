package ua.nure.estateflow.data.remote

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("message") val message: String
)