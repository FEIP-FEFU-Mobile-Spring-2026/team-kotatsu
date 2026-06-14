package ru.makoto.fefustore.DI

import android.content.Context
import ru.makoto.fefustore.Data.Remote.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.makoto.fefustore.Data.Remote.ApiService
import ru.makoto.fefustore.Data.Remote.ConnectivityObserver
import ru.makoto.fefustore.Data.SecretsManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(secretsManager: SecretsManager): OkHttpClient {
        return NetworkClient.createOkHttpClient(secretsManager)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return NetworkClient.createRetrofit(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return NetworkClient.createService(retrofit)
    }
}