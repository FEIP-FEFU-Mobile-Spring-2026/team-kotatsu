package ru.makoto.fefustore.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.CategoryEntity

@Dao
interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CategoryEntity)

    @Update
    suspend fun update(item: CategoryEntity)

    @Delete
    suspend fun delete(item: CategoryEntity)
    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<CategoryEntity>>

}