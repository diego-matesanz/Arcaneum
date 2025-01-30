package com.diego.matesanz.arcaneum.data.datasource.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteBooks(
    @SerialName("items") val items: List<RemoteBook>
) {
    @Serializable
    data class RemoteBook(
        @SerialName("id") val id: String? = null,
        @SerialName("volumeInfo") val volumeInfo: VolumeInfo? = null,
    ) {
        @Serializable
        data class VolumeInfo(
            @SerialName("title") val title: String? = null,
            @SerialName("authors") val authors: List<String>? = null,
            @SerialName("description") val description: String? = null,
            @SerialName("pageCount") val pageCount: Int? = null,
            @SerialName("averageRating") val averageRating: Double? = null,
            @SerialName("ratingsCount") val ratingsCount: Int? = null,
            @SerialName("imageLinks") val imageLinks: ImageLinks? = null,
            @SerialName("language") val language: String? = null,
        ) {
            @Serializable
            data class ImageLinks(
                @SerialName("thumbnail") val thumbnail: String? = null
            )
        }
    }
}
