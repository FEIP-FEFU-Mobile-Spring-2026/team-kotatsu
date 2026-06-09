package ru.makoto.fefustore.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.ClothesSizeEntity

@Dao
interface ClothesSizeDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ClothesSizeEntity)

    @Update
    suspend fun update(item: ClothesSizeEntity)

    @Delete
    suspend fun delete(item: ClothesSizeEntity)

    @Query("SELECT * FROM clothes_size")
    fun getAll(): Flow<List<ClothesSizeEntity>>

    @Query("SELECT * FROM clothes_size WHERE clothesId = :id")
    fun getSizesByClothesId(id: String): Flow<List<ClothesSizeEntity>>
}