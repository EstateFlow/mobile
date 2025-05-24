package ua.nure.estateflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.nure.estateflow.data.local.dao.ImageDao
import ua.nure.estateflow.data.local.dao.PropertyDao
import ua.nure.estateflow.data.local.dao.ViewDao
import ua.nure.estateflow.data.local.entity.ImageEntity
import ua.nure.estateflow.data.local.entity.PropertyEntity
import ua.nure.estateflow.data.local.entity.ViewEntity


@Database(
    entities = [
        PropertyEntity::class,
        ImageEntity::class,
        ViewEntity::class
    ],
    version = 4
)
@TypeConverters(DbConverters::class)
abstract class AppDb : RoomDatabase() {
    abstract val propertyDao: PropertyDao
    abstract val imageDao: ImageDao
    abstract val viewDao: ViewDao
}