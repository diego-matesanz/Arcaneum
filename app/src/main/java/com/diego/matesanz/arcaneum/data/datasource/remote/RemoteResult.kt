package com.diego.matesanz.arcaneum.data.datasource.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteResult(
    @SerialName("kind") val kind: String,
    @SerialName("totalItems") val totalItems: Int,
    @SerialName("items") val items: List<RemoteBook>
) {
    @Serializable
    data class RemoteBook(
        @SerialName("kind") val kind: String? = null,
        @SerialName("id") val id: String? = null,
        @SerialName("etag") val etag: String? = null,
        @SerialName("selfLink") val selfLink: String? = null,
        @SerialName("volumeInfo") val volumeInfo: VolumeInfo? = null,
        @SerialName("saleInfo") val saleInfo: SaleInfo? = null,
        @SerialName("accessInfo") val accessInfo: AccessInfo? = null,
        @SerialName("searchInfo") val searchInfo: SearchInfo? = null
    ) {
        @Serializable
        data class VolumeInfo(
            @SerialName("title") val title: String? = null,
            @SerialName("authors") val authors: List<String>? = null,
            @SerialName("publisher") val publisher: String? = null,
            @SerialName("publishedDate") val publishedDate: String? = null,
            @SerialName("description") val description: String? = null,
            @SerialName("industryIdentifiers") val industryIdentifiers: List<IndustryIdentifier>? = null,
            @SerialName("readingModes") val readingModes: ReadingModes? = null,
            @SerialName("pageCount") val pageCount: Int? = null,
            @SerialName("printType") val printType: String? = null,
            @SerialName("categories") val categories: List<String>? = null,
            @SerialName("averageRating") val averageRating: Double? = null,
            @SerialName("ratingsCount") val ratingsCount: Int? = null,
            @SerialName("maturityRating") val maturityRating: String? = null,
            @SerialName("allowAnonLogging") val allowAnonLogging: Boolean? = null,
            @SerialName("contentVersion") val contentVersion: String? = null,
            @SerialName("panelizationSummary") val panelizationSummary: PanelizationSummary? = null,
            @SerialName("imageLinks") val imageLinks: ImageLinks? = null,
            @SerialName("language") val language: String? = null,
            @SerialName("previewLink") val previewLink: String? = null,
            @SerialName("infoLink") val infoLink: String? = null,
            @SerialName("canonicalVolumeLink") val canonicalVolumeLink: String? = null,
            @SerialName("subtitle") val subtitle: String? = null,
            @SerialName("comicsContent") val comicsContent: Boolean? = null
        ) {
            @Serializable
            data class IndustryIdentifier(
                @SerialName("type") val type: String? = null,
                @SerialName("identifier") val identifier: String? = null
            )

            @Serializable
            data class ReadingModes(
                @SerialName("text") val text: Boolean? = null,
                @SerialName("image") val image: Boolean? = null
            )

            @Serializable
            data class PanelizationSummary(
                @SerialName("containsEpubBubbles") val containsEpubBubbles: Boolean? = null,
                @SerialName("containsImageBubbles") val containsImageBubbles: Boolean? = null,
                @SerialName("imageBubbleVersion") val imageBubbleVersion: String? = null
            )

            @Serializable
            data class ImageLinks(
                @SerialName("smallThumbnail") val smallThumbnail: String? = null,
                @SerialName("thumbnail") val thumbnail: String? = null
            )
        }

        @Serializable
        data class SaleInfo(
            @SerialName("country") val country: String? = null,
            @SerialName("saleability") val saleability: String? = null,
            @SerialName("isEbook") val isEbook: Boolean? = null,
            @SerialName("listPrice") val listPrice: ListPrice? = null,
            @SerialName("retailPrice") val retailPrice: RetailPrice? = null,
            @SerialName("buyLink") val buyLink: String? = null,
            @SerialName("offers") val offers: List<Offer>? = null
        ) {
            @Serializable
            data class ListPrice(
                @SerialName("amount") val amount: Double? = null,
                @SerialName("currencyCode") val currencyCode: String? = null
            )

            @Serializable
            data class RetailPrice(
                @SerialName("amount") val amount: Double? = null,
                @SerialName("currencyCode") val currencyCode: String? = null
            )

            @Serializable
            data class Offer(
                @SerialName("finskyOfferType") val finskyOfferType: Int? = null,
                @SerialName("listPrice") val listPrice: ListPrice? = null,
                @SerialName("retailPrice") val retailPrice: RetailPrice? = null,
                @SerialName("giftable") val giftable: Boolean? = null
            ) {
                @Serializable
                data class ListPrice(
                    @SerialName("amountInMicros") val amountInMicros: Int? = null,
                    @SerialName("currencyCode") val currencyCode: String? = null
                )

                @Serializable
                data class RetailPrice(
                    @SerialName("amountInMicros") val amountInMicros: Int? = null,
                    @SerialName("currencyCode") val currencyCode: String? = null
                )
            }
        }

        @Serializable
        data class AccessInfo(
            @SerialName("country") val country: String? = null,
            @SerialName("viewability") val viewability: String? = null,
            @SerialName("embeddable") val embeddable: Boolean? = null,
            @SerialName("publicDomain") val publicDomain: Boolean? = null,
            @SerialName("textToSpeechPermission") val textToSpeechPermission: String? = null,
            @SerialName("epub") val epub: Epub? = null,
            @SerialName("pdf") val pdf: Pdf? = null,
            @SerialName("webReaderLink") val webReaderLink: String? = null,
            @SerialName("accessViewStatus") val accessViewStatus: String? = null,
            @SerialName("quoteSharingAllowed") val quoteSharingAllowed: Boolean? = null
        ) {
            @Serializable
            data class Epub(
                @SerialName("isAvailable") val isAvailable: Boolean? = null,
                @SerialName("acsTokenLink") val acsTokenLink: String? = null
            )

            @Serializable
            data class Pdf(
                @SerialName("isAvailable") val isAvailable: Boolean? = null,
                @SerialName("acsTokenLink") val acsTokenLink: String? = null
            )
        }

        @Serializable
        data class SearchInfo(
            @SerialName("textSnippet") val textSnippet: String? = null
        )
    }
}
