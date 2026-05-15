package ru.makoto.fefustore.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.ClothesEntity

@Dao
interface ClothesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ClothesEntity)

    @Update
    suspend fun update(item: ClothesEntity)

    @Delete
    suspend fun delete(item: ClothesEntity)

    @Query("SELECT * FROM clothes")
    fun getAll(): Flow<List<ClothesEntity>>
}