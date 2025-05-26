package ua.nure.estateflow.data.remote.ai.dto

import com.google.gson.annotations.SerializedName

data class MessageRequest(
    @SerializedName("message") val message: String
)
