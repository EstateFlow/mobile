package ua.nure.estateflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.local.entity.MessageEntity

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<MessageEntity>)

    @Query("SELECT * FROM messageentity ORDER BY `index` DESC")
    fun getAll(): Flow<List<MessageEntity>>
}