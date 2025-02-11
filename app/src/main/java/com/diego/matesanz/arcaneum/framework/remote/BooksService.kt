package com.diego.matesanz.arcaneum.framework.remote

import com.diego.matesanz.arcaneum.framework.remote.models.RemoteBooks
import com.diego.matesanz.arcaneum.framework.remote.models.RemoteBooks.RemoteBook
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksService {

    @GET("volumes")
    suspend fun findBooksBySearchText(@Query("q") search: String): RemoteBooks

    @GET("volumes/{volumeId}")
    suspend fun findBookById(@Path("volumeId") volumeId: String): RemoteBook
}
