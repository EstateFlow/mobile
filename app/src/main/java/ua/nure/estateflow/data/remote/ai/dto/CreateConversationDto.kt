package ua.nure.estateflow.data.remote.ai.dto

import com.google.gson.annotations.SerializedName

data class CreateConversationDto(
    @SerializedName("title") val title: String
)
