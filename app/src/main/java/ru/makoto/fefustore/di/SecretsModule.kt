package ru.makoto.fefustore.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import ru.makoto.fefustore.data.SecretsManager

@Module
@InstallIn(SingletonComponent::class)
object SecretsModule {
    @Provides
    @Singleton
    fun provideSecretsManager(
        @ApplicationContext context: Context,
    ): SecretsManager = SecretsManager(context)
}
