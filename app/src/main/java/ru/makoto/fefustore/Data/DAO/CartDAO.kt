package ru.makoto.fefustore.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.makoto.fefustore.Data.Entity.CartEntity
import ru.makoto.fefustore.Data.Relation.CartWithClothes

@Dao
interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CartEntity)

    @Update
    suspend fun update(item: CartEntity)

    @Delete
    suspend fun delete(item: CartEntity)

    @Query("SELECT * FROM cart")
    fun getAll(): Flow<List<CartWithClothes>>

    @Query("SELECT * FROM cart WHERE clothesId = :clothesId LIMIT 1")
    fun getItemByClothesId(clothesId: String): Flow<List<CartWithClothes>>
}