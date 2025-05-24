package ua.nure.estateflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class OwnerEntity(
    @PrimaryKey val id: String,
    val username: String,
    val email: String,
    val role: String,
    val isEmailVerified: Boolean,
    val createdAt: String,
    val updatedAt: String,
)
