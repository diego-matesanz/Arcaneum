package com.diego.matesanz.arcaneum.ui.common.components.books.booksList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.ui.common.components.CustomAsyncImage
import com.diego.matesanz.arcaneum.ui.common.components.addToShelfButton.ModalAddToShelfButton

@Composable
fun BookItem(
    book: Book,
    shelves: List<Shelf>,
    onClick: (Book) -> Unit,
    onBookmarked: (Int, Book) -> Unit
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .clickable(onClick = { onClick(book) }),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CustomAsyncImage(
            model = book.coverImage,
            contentDescription = stringResource(
                R.string.book_cover_content_accessibility_description,
                book.title
            ),
            modifier = Modifier
                .height(180.dp)
                .aspectRatio(BOOK_ASPECT_RATIO),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                TitleAndAuthorsSection(
                    title = book.title,
                    authors = book.authors,
                )
                /*RatingSection(
                    averageRating = book.averageRating,
                    ratingsCount = book.ratingsCount,
                )*/
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                ModalAddToShelfButton(
                    modifier = Modifier.padding(end = 8.dp),
                    shelves = shelves,
                    selectedShelfId = book.shelfId,
                    onShelfSelected = { shelfId -> onBookmarked(shelfId, book) }
                )
            }
        }
    }
}

@Composable
private fun TitleAndAuthorsSection(
    title: String,
    authors: List<String>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
        )

        var authorsText = StringBuilder()
        authors.forEachIndexed { index, author ->
            authorsText.append(author)
            if (index < authors.lastIndex) {
                authorsText.append(", ")
            }
        }
        Text(
            text = authorsText.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
        )
    }
}

/*@Composable
private fun RatingSection(
    averageRating: Double,
    ratingsCount: Int,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (averageRating > 0) {
            Text(
                text = "${stringResource(R.string.rating)}: $averageRating",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
        }
        if (ratingsCount > 0) {
            Text(
                text = "$ratingsCount ${stringResource(R.string.ratings)}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
        }
    }
}*/
