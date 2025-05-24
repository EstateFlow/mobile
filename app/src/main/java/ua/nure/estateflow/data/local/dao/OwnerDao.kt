package ua.nure.estateflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ua.nure.estateflow.data.local.entity.OwnerEntity

@Dao
interface OwnerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<OwnerEntity>)
}