package com.diego.matesanz.arcaneum.ui.screens.bookDetail.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.ui.common.components.CustomAsyncImage
import com.diego.matesanz.arcaneum.ui.common.components.NavigationBackTopBar
import com.diego.matesanz.arcaneum.ui.common.components.addToShelfButton.DropdownAddToShelfButton
import com.diego.matesanz.arcaneum.ui.screens.Screen
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel.BookDetailAction
import com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel.BookDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Screen(
        contentDescription = stringResource(id = R.string.detail_screen_accessibility_description),
    ) {
        Scaffold(
            topBar = {
                NavigationBackTopBar(
                    onBack = onBack,
                    scrollBehavior = scrollBehavior,
                    dominantColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { padding ->
            DetailContent(
                state = state,
                onBookmarked = { shelfId, book ->
                    viewModel.onAction(BookDetailAction.Bookmarked(shelfId, book))
                },
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Composable
private fun DetailContent(
    state: BookDetailViewModel.UiState,
    onBookmarked: (Int, Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        BookDetailLoader(modifier = modifier)
    } else {
        state.book?.let { book ->
            Box {
                BookDetail(
                    book = book,
                    modifier = modifier,
                )
                DropdownAddToShelfButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 80.dp),
                    shelves = state.shelves,
                    selectedShelfId = book.shelfId,
                    onShelfSelected = { shelfId -> onBookmarked(shelfId, book) }
                )
            }
        }
    }
}

@Composable
private fun BookDetail(
    book: Book,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            CustomAsyncImage(
                model = book.coverImage,
                contentDescription = stringResource(
                    R.string.book_cover_content_accessibility_description,
                    book.title
                ),
                modifier = Modifier
                    .height(270.dp)
                    .aspectRatio(BOOK_ASPECT_RATIO),
            )
            TitleSection(
                title = book.title,
                author = book.authors.first()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            InfoSection(
                rating = book.averageRating,
                pages = book.pageCount,
                language = book.language
            )
            if (book.description.isNotEmpty()) {
                DescriptionSection(book.description)
            }
        }
    }
}

@Composable
private fun TitleSection(
    title: String,
    author: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = author,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun InfoSection(
    rating: Double,
    pages: Int,
    language: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(16.dp),
    ) {
        if (rating > 0) {
            InfoItem(
                title = stringResource(id = R.string.rating),
                description = rating.toString()
            )
        }
        if (pages > 0) {
            InfoItem(
                title = stringResource(id = R.string.pages),
                description = pages.toString()
            )
        }
        if (language.isNotEmpty()) {
            InfoItem(
                title = stringResource(id = R.string.language),
                description = language
            )
        }
    }
}

@Composable
private fun InfoItem(
    title: String,
    description: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
        )

        var isExpanded by remember { mutableStateOf(false) }
        Text(
            modifier = Modifier.animateContentSize(),
            text = AnnotatedString.fromHtml(description),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            maxLines = if (isExpanded) Int.MAX_VALUE else 5,
            textAlign = TextAlign.Start,
        )
        Text(
            text = if (isExpanded) stringResource(id = R.string.read_less) else stringResource(id = R.string.read_more),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.End,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { isExpanded = !isExpanded }
        )
    }
}
