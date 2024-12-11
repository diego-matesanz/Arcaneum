package com.diego.matesanz.arcaneum.data.datasource.remote

import com.diego.matesanz.arcaneum.data.datasource.remote.RemoteResult.RemoteBook
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksService {

    @GET("volumes")
    suspend fun findBooksBySearchText(@Query("q") search: String): RemoteResult

    @GET("volumes/{volumeId}")
    suspend fun findBookById(@Path("volumeId") volumeId: String): RemoteBook
}
