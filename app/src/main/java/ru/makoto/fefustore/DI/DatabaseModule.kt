package ru.makoto.fefustore.DI
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.makoto.fefustore.Data.DAO.*
import javax.inject.Singleton
import ru.makoto.fefustore.Data.StoreDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): StoreDatabase {
        return Room.databaseBuilder(
            context,
            StoreDatabase::class.java,
            "store_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    fun provideCategoryDAO(database: StoreDatabase): CategoryDAO {
        return database.categoryDAO()
    }

    @Provides
    fun provideClothesDAO(database: StoreDatabase): ClothesDAO {
        return database.clothesDAO()
    }

    @Provides
    fun provideTagDAO(database: StoreDatabase): TagDAO {
        return database.tagDAO()
    }
    @Provides
    fun provideClothesSizeDAO(database: StoreDatabase): ClothesSizeDAO {
        return database.clothesSizeDAO()
    }
    @Provides
    fun provideClothesTagDAO(database: StoreDatabase): ClothesTagDAO {
        return database.clothesTagDAO()
    }
}