package ua.nure.estateflow.data.remote.property.dto

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.local.entity.OwnerEntity
import kotlin.String

data class OwnerDto(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
)

fun OwnerDto.toEntity() =
    OwnerEntity(
        id = id,
        username = username,
        email = email,
        role = role,
    )