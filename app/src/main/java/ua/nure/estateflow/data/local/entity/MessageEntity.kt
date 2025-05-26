package ua.nure.estateflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.remote.ai.dto.Sender

@Entity
data class MessageEntity (
    @PrimaryKey val id: String,
    val sender: Sender,
    val content: String,
    val createdAt: String,
)