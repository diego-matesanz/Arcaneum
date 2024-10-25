package com.diego.matesanz.arcaneum.data

fun String.toHttps(): String {
    return if (this.contains("http://")) {
        this.replace("http://", "https://")
    } else {
        this
    }
}
