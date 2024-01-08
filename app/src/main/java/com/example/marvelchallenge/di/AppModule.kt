package com.example.marvelchallenge.di

import com.example.marvelchallenge.data.remote.MarvelApi
import com.example.marvelchallenge.data.repository.CharactersRepositoryImpl
import com.example.marvelchallenge.data.repository.ComicsRepositoryImpl
import com.example.marvelchallenge.data.repository.EventsRepositoryImpl
import com.example.marvelchallenge.data.repository.SeriesRepositoryImpl
import com.example.marvelchallenge.domain.repository.CharactersRepository
import com.example.marvelchallenge.domain.repository.ComicRepository
import com.example.marvelchallenge.domain.repository.EventsRepository
import com.example.marvelchallenge.domain.repository.SeriesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
  @Singleton
  @Provides
  fun providesMoshi() = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  @Singleton
  @Provides
  fun providesOkHttpClient() = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

  @Singleton
  @Provides
  fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
    .baseUrl("https://gateway.marvel.com/v1/public/") // replace with your actual base URL
    .client(okHttpClient)
    .addConverterFactory(
      MoshiConverterFactory.create(
        moshi
      )
    )
    .build()

  @Singleton
  @Provides
  fun providesCoroutineIODispatcher() = Dispatchers.IO

  @Singleton
  @Provides
  fun providesMarvelApi(retrofit: Retrofit): MarvelApi = retrofit.create(MarvelApi::class.java)

  @InstallIn(SingletonComponent::class)
  @Module
  interface Binding {
    @Binds
    fun provideCharactersRepository(impl: CharactersRepositoryImpl): CharactersRepository

    @Binds
    fun providesComicRepository(impl: ComicsRepositoryImpl): ComicRepository

    @Binds
    fun providesEventsRepository( impl: EventsRepositoryImpl): EventsRepository

    @Binds
    fun provideSeriesRepository( impl: SeriesRepositoryImpl): SeriesRepository
  }
}