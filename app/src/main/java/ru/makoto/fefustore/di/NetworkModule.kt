package ru.makoto.fefustore.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.makoto.fefustore.data.SecretsManager
import ru.makoto.fefustore.data.remote.ApiService
import ru.makoto.fefustore.data.remote.NetworkClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(secretsManager: SecretsManager): OkHttpClient = NetworkClient.createOkHttpClient(secretsManager)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = NetworkClient.createRetrofit(okHttpClient)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = NetworkClient.createService(retrofit)
}
