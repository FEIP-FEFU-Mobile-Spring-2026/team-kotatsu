package ru.makoto.fefustore.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.makoto.fefustore.data.dao.CartDAO
import ru.makoto.fefustore.data.dao.CategoryDAO
import ru.makoto.fefustore.data.dao.ClothesDAO
import ru.makoto.fefustore.data.dao.ClothesSizeDAO
import ru.makoto.fefustore.data.dao.ClothesTagDAO
import ru.makoto.fefustore.data.dao.SelectedCategoryDAO
import ru.makoto.fefustore.data.dao.TagDAO
import ru.makoto.fefustore.data.entity.CartEntity
import ru.makoto.fefustore.data.entity.CategoryEntity
import ru.makoto.fefustore.data.entity.ClothesEntity
import ru.makoto.fefustore.data.entity.ClothesSizeEntity
import ru.makoto.fefustore.data.entity.ClothesTagEntity
import ru.makoto.fefustore.data.entity.SelectedCategoryEntity
import ru.makoto.fefustore.data.entity.TagEntity

@Database(
    entities = [
        CategoryEntity::class,
        ClothesEntity::class,
        TagEntity::class,
        ClothesSizeEntity::class,
        ClothesTagEntity::class,
        CartEntity::class,
        SelectedCategoryEntity::class,
    ],
    version = 3,
    exportSchema = false,
)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun categoryDAO(): CategoryDAO

    abstract fun clothesDAO(): ClothesDAO

    abstract fun tagDAO(): TagDAO

    abstract fun clothesSizeDAO(): ClothesSizeDAO

    abstract fun clothesTagDAO(): ClothesTagDAO

    abstract fun cartDAO(): CartDAO

    abstract fun selectedCategoryDAO(): SelectedCategoryDAO
}
