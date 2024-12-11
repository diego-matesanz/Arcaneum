package com.diego.matesanz.arcaneum.data.datasource.database

import androidx.room.TypeConverter
import com.diego.matesanz.arcaneum.extensions.fromJson
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromListString(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toListString(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value)
        } catch (e: Exception) {
            listOf()
        }
    }
}
