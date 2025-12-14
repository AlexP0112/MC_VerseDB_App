package com.alex.versedb.data.repository

import com.alex.versedb.data.local.PoemDao
import com.alex.versedb.data.remote.PoetryApiService
import com.alex.versedb.domain.PoetryRepository
import com.alex.versedb.domain.model.Poem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PoetryRepositoryImpl @Inject constructor(
    private val api: PoetryApiService,
    private val dao: PoemDao
) : PoetryRepository {

    override suspend fun getPoemsByAuthor(author: String): List<Poem> {
        return api.getPoemsByAuthor(author)
    }

    override suspend fun getPoemsByTitle(title: String): List<Poem> {
        return api.getPoemsByTitle(title)
    }

    override fun getFavoritePoems(): Flow<List<Poem>> {
        return dao.getFavoritePoems()
    }

    override suspend fun addToFavorites(poem: Poem) {
        dao.addToFavorites(poem)
    }

    override suspend fun removeFromFavorites(poem: Poem) {
        dao.removeFromFavorites(poem.title, poem.author)
    }

    override fun isFavorite(poem: Poem): Flow<Boolean> {
        return dao.isFavorite(poem.title, poem.author)
    }
}
