package com.alex.versedb.domain

import com.alex.versedb.domain.model.Poem
import kotlinx.coroutines.flow.Flow

interface PoetryRepository {
    suspend fun getPoemsByAuthor(author: String): List<Poem>
    suspend fun getPoemsByTitle(title: String): List<Poem>
    fun getFavoritePoems(): Flow<List<Poem>>
    suspend fun addToFavorites(poem: Poem)
    suspend fun removeFromFavorites(poem: Poem)
    fun isFavorite(poem: Poem): Flow<Boolean>
}
