package ru.makoto.fefustore.Data.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.ClothesEntity
import ru.makoto.fefustore.Data.Relation.ClothesWithDetails

@Dao
interface ClothesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ClothesEntity)

    @Update
    suspend fun update(item: ClothesEntity)

    @Delete
    suspend fun delete(item: ClothesEntity)

    @Transaction
    @Query("SELECT * FROM clothes")
    fun getAll(): LiveData<List<ClothesWithDetails>>

    @Query("SELECT * FROM clothes WHERE id = :id")
    fun getClothesById(id: String): Flow<List<ClothesEntity>>
}