package com.alex.versedb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alex.versedb.domain.model.Poem
import kotlinx.coroutines.flow.Flow

@Dao
interface PoemDao {

    @Query("SELECT * FROM poems")
    fun getFavoritePoems(): Flow<List<Poem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(poem: Poem)

    @Query("DELETE FROM poems WHERE title = :title AND author = :author")
    suspend fun removeFromFavorites(title: String, author: String)

    @Query("SELECT EXISTS(SELECT 1 FROM poems WHERE title = :title AND author = :author)")
    fun isFavorite(title: String, author: String): Flow<Boolean>
}
