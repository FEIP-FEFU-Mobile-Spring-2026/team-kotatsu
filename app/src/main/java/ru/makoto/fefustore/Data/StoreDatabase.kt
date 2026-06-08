package ru.makoto.fefustore.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.makoto.fefustore.Data.DAO.*
import ru.makoto.fefustore.Data.Entity.*

@Database(
    entities = [
        CategoryEntity::class,
        ClothesEntity::class,
        TagEntity::class,
        ClothesSizeEntity::class,
        ClothesTagEntity::class,
        CartEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun categoryDAO(): CategoryDAO
    abstract fun clothesDAO(): ClothesDAO
    abstract fun tagDAO(): TagDAO
    abstract fun clothesSizeDAO(): ClothesSizeDAO
    abstract fun clothesTagDAO(): ClothesTagDAO
    abstract fun cartDAO(): CartDAO

}