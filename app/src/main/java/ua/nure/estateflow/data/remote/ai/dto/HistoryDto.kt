package ua.nure.estateflow.data.remote.ai.dto

import com.google.gson.annotations.SerializedName

data class HistoryDto(
    @SerializedName("messages") val historyMessages: HistoryMessagesDto
)

data class HistoryMessagesDto(
    @SerializedName("messages") val messages: List<ContentMessageDto>
)
