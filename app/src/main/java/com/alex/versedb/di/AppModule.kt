package com.alex.versedb.di

import android.content.Context
import androidx.room.Room
import com.alex.versedb.data.local.PoemDao
import com.alex.versedb.data.local.PoetryDatabase
import com.alex.versedb.data.remote.PoetryApiService
import com.alex.versedb.data.repository.PoetryRepositoryImpl
import com.alex.versedb.domain.PoetryRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePoetryApi(): PoetryApiService {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        return Retrofit.Builder()
            .baseUrl("https://poetrydb.org/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PoetryApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePoetryDatabase(@ApplicationContext context: Context): PoetryDatabase {
        return Room.databaseBuilder(
            context,
            PoetryDatabase::class.java,
            "poetry.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePoemDao(database: PoetryDatabase): PoemDao {
        return database.poemDao()
    }

    @Provides
    @Singleton
    fun providePoetryRepository(api: PoetryApiService, dao: PoemDao): PoetryRepository {
        return PoetryRepositoryImpl(api, dao)
    }
}
