package ru.makoto.fefustore.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.ClothesTagEntity

@Dao
interface ClothesTagDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ClothesTagEntity)

    @Update
    suspend fun update(item: ClothesTagEntity)

    @Delete
    suspend fun delete(item: ClothesTagEntity)

    @Query("SELECT * FROM clothes_tag")
    fun getAll(): Flow<List<ClothesTagEntity>>

    @Query("SELECT * FROM clothes_tag WHERE clothesId = :id")
    fun getTagsClothesByClothesId(id: String): Flow<List<ClothesTagEntity>>
}