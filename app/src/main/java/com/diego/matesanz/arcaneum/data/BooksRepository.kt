package com.diego.matesanz.arcaneum.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class BooksRepository {

    suspend fun fetchPopularBooks(): List<Book> =
        withContext(Dispatchers.IO) {
            delay(2000)
            books
        }
}
