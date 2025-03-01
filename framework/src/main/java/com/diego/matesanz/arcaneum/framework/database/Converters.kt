package com.diego.matesanz.arcaneum.framework.database

import android.util.Log
import androidx.room.TypeConverter
import com.diego.matesanz.arcaneum.framework.fromJson
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class Converters {

    @TypeConverter
    fun fromListString(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toListString(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value)
        } catch (e: JsonSyntaxException) {
            Log.e("Converters", "Error converting string to list", e)
            listOf()
        }
    }
}
