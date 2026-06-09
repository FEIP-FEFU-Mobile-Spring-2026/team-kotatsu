package ru.makoto.fefustore.Data.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.TagEntity

@Dao
interface TagDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TagEntity)

    @Update
    suspend fun update(item: TagEntity)

    @Delete
    suspend fun delete(item: TagEntity)

    @Query("SELECT * FROM tag")
    fun getAll(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tag WHERE id = :id LIMIT 1")
    fun getTagById(id: String): Flow<TagEntity>
}