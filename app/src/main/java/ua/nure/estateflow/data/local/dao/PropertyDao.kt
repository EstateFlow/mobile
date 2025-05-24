package ua.nure.estateflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.nure.estateflow.data.local.entity.Property
import ua.nure.estateflow.data.local.entity.PropertyEntity

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<PropertyEntity>)

    suspend fun syncInsert(list:List<PropertyEntity>) {
        deleteAll()
        insert(list)
    }

    @Query("DELETE FROM propertyentity")
    suspend fun deleteAll()

    @Query("DELETE FROM propertyentity WHERE id NOT IN (:list)")
    suspend fun deleteExcluded(list: List<String>)

    @Query("SELECT * FROM propertyentity")
    fun getAll(): Flow<List<Property>>

    @Query("SELECT * FROM propertyentity WHERE id = :id")
    fun getById(id: String): Flow<Property>

}