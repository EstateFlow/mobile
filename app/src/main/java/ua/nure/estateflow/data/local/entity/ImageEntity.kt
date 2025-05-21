package ua.nure.estateflow.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PropertyEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("propertyId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ImageEntity(
    @PrimaryKey val id: String,
    val propertyId: String,
    val imageUrl: String,
    val isPrimary: Boolean,
)
