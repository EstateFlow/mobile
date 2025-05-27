package ua.nure.estateflow.data.remote.ai.dto

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.local.entity.MessageEntity
import kotlin.String

data class ContentMessageDto(
    @SerializedName("id") val id: String,
    @SerializedName("sender") val sender: Sender,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("index") val index: Int,
)

enum class Sender (val sender: String) {
    @SerializedName("user") user("user"),
    @SerializedName("ai") ai("ai")
}

fun ContentMessageDto.toEntity(): MessageEntity =
    MessageEntity(
        id = id,
        sender = sender,
        content = content,
        createdAt = createdAt,
        index = index
    )