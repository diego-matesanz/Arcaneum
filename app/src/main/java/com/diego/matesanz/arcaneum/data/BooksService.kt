package com.diego.matesanz.arcaneum.data

import com.diego.matesanz.arcaneum.data.RemoteResult.RemoteBook
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksService {

    @GET("volumes")
    suspend fun fetchBooksBySearchText(@Query("q") search: String): RemoteResult

    @GET("volumes/{volumeId}")
    suspend fun fetchBookById(@Path("volumeId") volumeId: String): RemoteBook
}
