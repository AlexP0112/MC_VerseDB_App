package com.alex.versedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alex.versedb.domain.model.Poem

@Database(entities = [Poem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PoetryDatabase : RoomDatabase() {
    abstract fun poemDao(): PoemDao
}
