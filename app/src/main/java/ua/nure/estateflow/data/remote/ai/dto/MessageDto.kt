package ua.nure.estateflow.data.remote.ai.dto

import android.os.Message
import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.local.entity.MessageEntity

data class MessageDto(
    @SerializedName("message") val message: String,
    @SerializedName("userMessage") val userMessage: ContentMessageDto,
    @SerializedName("aiResponse") val aiResponse: ContentMessageDto,
)

fun MessageDto.toEntity(): List<MessageEntity> =
    listOf(
        userMessage.toEntity(),
        aiResponse.toEntity()
    )