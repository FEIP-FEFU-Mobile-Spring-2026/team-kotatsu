package ru.makoto.fefustore.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.SelectedCategoryEntity

@Dao
interface SelectedCategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: SelectedCategoryEntity)

    @Update
    suspend fun update(item: SelectedCategoryEntity)

    @Delete
    suspend fun delete(item: SelectedCategoryEntity)

    @Query("SELECT * FROM selected_category")
    fun getAll(): Flow<List<SelectedCategoryEntity>>

    @Query("UPDATE selected_category SET isSelected = 0")
    suspend fun clearSelectedCategory()

    @Query("UPDATE selected_category SET isSelected = 1 WHERE categoryId = :id")
    suspend fun setCategory(id: String)

    @Transaction
    open suspend fun selectCategory(id: String) {
        clearSelectedCategory()
        setCategory(id)
    }

    @Query("SELECT * FROM selected_category WHERE isSelected = 1 LIMIT 1")
    fun getSelectedCategory(): Flow<List<SelectedCategoryEntity>>
}