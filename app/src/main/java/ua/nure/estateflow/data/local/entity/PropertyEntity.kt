package ua.nure.estateflow.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.remote.property.dto.ImageDto
import ua.nure.estateflow.data.remote.property.dto.ViewDto

@Entity()
data class PropertyEntity(
    @PrimaryKey val id: String,
    val ownerId: String,
    val title: String,
    val description: String,
    val propertyType: String,
    val transactionType: String,
    val price: String,
    val currency: String,
    val size: String,
    val rooms: Int,
    val address: String,
    val status: String,
    val documentUrl: String?,
    val verificationComments: String = "",
    val isVerified: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class Property(
    @Embedded val propertyEntity: PropertyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "propertyId",
        entity = ImageEntity::class
    ) val images: List<ImageEntity>
)