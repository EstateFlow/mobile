package ua.nure.estateflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ua.nure.estateflow.data.local.entity.ImageEntity

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<ImageEntity>)

}