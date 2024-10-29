package com.diego.matesanz.arcaneum.data

fun String.toHttps(): String = replace(Regex("^http://"), "https://")
