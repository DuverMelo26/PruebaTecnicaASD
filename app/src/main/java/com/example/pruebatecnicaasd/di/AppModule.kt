package com.example.pruebatecnicaasd.di

import com.example.pruebatecnicaasd.peliculas.data.repository.PeliculasRemoteRepository
import com.example.pruebatecnicaasd.core.network.PeliculasApi
import com.example.pruebatecnicaasd.core.network.PeliculasInterceptor
import com.example.pruebatecnicaasd.peliculas.domain.repository.PeliculasRemoteRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(PeliculasInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun providePeliculasApi(retrofit: Retrofit): PeliculasApi =
        retrofit.create(PeliculasApi::class.java)

    @Singleton
    @Provides
    fun providePeliculasRemoteRepositoryInterface(
        peliculasRemoteRepository: PeliculasRemoteRepository
    ) : PeliculasRemoteRepositoryInterface =
        peliculasRemoteRepository
}