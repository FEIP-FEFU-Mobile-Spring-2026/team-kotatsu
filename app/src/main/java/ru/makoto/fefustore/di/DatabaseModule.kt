package ru.makoto.fefustore.di
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.makoto.fefustore.data.StoreDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): StoreDatabase =
        Room
            .databaseBuilder(
                context,
                StoreDatabase::class.java,
                "store_database",
            ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCategoryDAO(database: StoreDatabase) = database.categoryDAO()

    @Provides
    fun provideClothesDAO(database: StoreDatabase) = database.clothesDAO()

    @Provides
    fun provideTagDAO(database: StoreDatabase) = database.tagDAO()

    @Provides
    fun provideClothesSizeDAO(database: StoreDatabase) = database.clothesSizeDAO()

    @Provides
    fun provideClothesTagDAO(database: StoreDatabase) = database.clothesTagDAO()

    @Provides
    fun provideCartDAO(database: StoreDatabase) = database.cartDAO()

    @Provides
    fun selectedCategoryDAO(database: StoreDatabase) = database.selectedCategoryDAO()
}
