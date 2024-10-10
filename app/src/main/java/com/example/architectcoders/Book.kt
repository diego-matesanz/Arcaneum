package com.example.architectcoders

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val coverUrl: String,
)

val books = (1..100).map { index ->
    Book(
        id = index,
        title = "Book $index",
        author = "Author $index",
        coverUrl = "https://picsum.photos/200/300?random=$index",
    )
}
