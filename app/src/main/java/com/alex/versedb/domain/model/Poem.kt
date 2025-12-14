package com.alex.versedb.domain.model

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "poems", primaryKeys = ["title", "author"])
data class Poem(
    val title: String,
    val author: String,
    val lines: List<String>
)
