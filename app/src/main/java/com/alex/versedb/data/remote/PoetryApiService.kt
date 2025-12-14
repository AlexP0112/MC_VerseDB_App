package com.alex.versedb.data.remote

import com.alex.versedb.domain.model.Poem
import retrofit2.http.GET
import retrofit2.http.Path

interface PoetryApiService {

    @GET("author/{author}")
    suspend fun getPoemsByAuthor(@Path("author") author: String): List<Poem>

    @GET("title/{title}")
    suspend fun getPoemsByTitle(@Path("title") title: String): List<Poem>
}
