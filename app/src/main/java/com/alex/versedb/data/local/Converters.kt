package com.alex.versedb.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split("\n")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString("\n")
    }
}
