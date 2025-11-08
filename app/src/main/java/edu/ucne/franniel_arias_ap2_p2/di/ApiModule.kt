package edu.ucne.franniel_arias_ap2_p2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.franniel_arias_ap2_p2.data.remote.GastosApi
import edu.ucne.franniel_arias_ap2_p2.data.repository.GastosRepositoryImpl
import edu.ucne.franniel_arias_ap2_p2.domain.repository.GastosRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BASE_URL = "https://gestionhuacalesapi.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi, okHttpClient: OkHttpClient): GastosApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(GastosApi::class.java)
    }

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class RepositoryModule {
        @Binds
        @Singleton
        abstract fun bindGastosRepository(
            gastosRepositoryImpl: GastosRepositoryImpl
        ): GastosRepository
    }
}