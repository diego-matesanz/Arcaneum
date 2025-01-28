package com.diego.matesanz.arcaneum.data.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.toHttps(): String = replace(Regex("^http://"), "https://")

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
