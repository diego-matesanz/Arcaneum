package com.diego.matesanz.arcaneum.framework.remote

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

internal class BooksClient(
    val apiKey: String,
) {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(::apiKeyAsQuery)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val instance = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create<BooksService>()

    private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request().newBuilder()
            .url(
                chain.request().url
                    .newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build()
            )
            .build()
    )
}
