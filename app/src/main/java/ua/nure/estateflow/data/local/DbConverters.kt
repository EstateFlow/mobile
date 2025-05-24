package ua.nure.estateflow.data.local

import androidx.room.TypeConverter
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

    fun propertyTypeFromDb(value: Int) =
        when(value) {
            0 -> PropertyType.house
            1 -> PropertyType.apartment
            else -> throw IllegalArgumentException("PropertyType value: $value not implemented")
        }

    fun transactionTypeToDb(type: TransactionType) =
        when(type) {
            TransactionType.rent -> 0
            TransactionType.sale -> 1
        }

    fun transactionTypeFromDb(value: Int) =
        when (value) {
            0 -> TransactionType.rent
            1 -> TransactionType.sale
            else -> throw IllegalArgumentException("TransactionType value: $value not implemented")
        }
}