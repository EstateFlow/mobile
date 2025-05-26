package ua.nure.estateflow.data.local

import androidx.room.TypeConverter
import ua.nure.estateflow.data.remote.ai.dto.Sender
import ua.nure.estateflow.data.remote.property.dto.PropertyType
import ua.nure.estateflow.data.remote.property.dto.TransactionType
import java.util.Date

class DbConverters {
    @TypeConverter
    fun dataToDb(date: Date): Long = date.time

    @TypeConverter
    fun dateFromDb(time: Long): Date = Date(time)

    @TypeConverter
    fun propertyTypeToDp(type: PropertyType) =
        when(type) {
            PropertyType.house -> 0
            PropertyType.apartment -> 1
        }

    @TypeConverter
    fun propertyTypeFromDb(value: Int) =
        when(value) {
            0 -> PropertyType.house
            1 -> PropertyType.apartment
            else -> throw IllegalArgumentException("PropertyType value: $value not implemented")
        }

    @TypeConverter
    fun transactionTypeToDb(type: TransactionType) =
        when(type) {
            TransactionType.rent -> 0
            TransactionType.sale -> 1
        }

    @TypeConverter
    fun transactionTypeFromDb(value: Int) =
        when (value) {
            0 -> TransactionType.rent
            1 -> TransactionType.sale
            else -> throw IllegalArgumentException("TransactionType value: $value not implemented")
        }

    @TypeConverter
    fun senderToDb(type: Sender) =
        when(type) {
            Sender.user -> 0
            Sender.ai -> 1
        }

    @TypeConverter
    fun senderFromDb(value: Int) =
        when(value) {
           0 -> Sender.user
           1 -> Sender.ai
           else -> throw IllegalArgumentException("Sender value: $value not implemented")
        }
}