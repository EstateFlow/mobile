package ua.nure.estateflow.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import ua.nure.estateflow.data.remote.property.dto.ImageDto
import ua.nure.estateflow.data.remote.property.dto.PropertyStatus
import ua.nure.estateflow.data.remote.property.dto.PropertyType
import ua.nure.estateflow.data.remote.property.dto.TransactionType
import ua.nure.estateflow.data.remote.property.dto.ViewDto

@Entity()
data class PropertyEntity(
    @PrimaryKey val id: String,
    val ownerId: String,
    val title: String,
    val description: String,
    val propertyType: PropertyType,
    val transactionType: TransactionType,
    val price: String,
    val currency: String,
    val size: String,
    val rooms: Int,
    val address: String,
    val status: PropertyStatus,
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
    ) val images: List<ImageEntity>,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "id",
        entity = OwnerEntity::class
    ) val owner: OwnerEntity
)