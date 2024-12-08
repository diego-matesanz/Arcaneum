package com.diego.matesanz.arcaneum.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.ui.common.CustomAsyncImage
import com.diego.matesanz.arcaneum.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onBookClick: (Book) -> Unit,
    onCamClick: () -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message = message)
            viewModel.onMessageShown()
        }
    }

    Screen(
        contentDescription = stringResource(id = R.string.home_screen_accessibility_description),
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = { HomeTopBar(scrollBehavior = scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            HomeContent(
                state = state,
                onBookClick = onBookClick,
                onBookmarked = viewModel::onBookmarked,
                onCamClick = onCamClick,
                onSearch = viewModel::fetchBooksBySearch,
                contentPadding = padding,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Normal),
            )
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun HomeContent(
    state: HomeViewModel.UiState,
    onBookClick: (Book) -> Unit,
    onBookmarked: () -> Unit,
    onCamClick: () -> Unit,
    onSearch: (String) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = contentPadding,
    ) {
        item {
            SearchBar(
                searchText = state.searchText,
                onCamClick = onCamClick,
                onSearch = onSearch,
            )
        }
        when {
            state.isLoading -> item { HomeLoader() }
            state.isError -> item { HomeError() }
            state.books.isEmpty() && state.searchText.isEmpty() -> item { HomeEmpty() }
            else -> {
                itemsIndexed(state.books) { index, book ->
                    BookItem(
                        book = book,
                        onClick = onBookClick,
                        onBookmarked = onBookmarked
                    )
                    if (index < state.books.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .padding(horizontal = 16.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    searchText: String,
    onCamClick: () -> Unit,
    onSearch: (String) -> Unit,
) {
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var search by remember { mutableStateOf(searchText) }
    TextField(
        value = search,
        onValueChange = { search = it },
        label = { Text(text = stringResource(id = R.string.search_label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(search)
                localFocusManager.clearFocus()
                keyboardController?.hide()
            },
        ),
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = {
                onSearch(search)
                localFocusManager.clearFocus()
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_action_accessibility_description),
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = onCamClick) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = stringResource(id = R.string.open_camera_action_accessibility_description),
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small),
    )
}

@Composable
private fun BookItem(
    book: Book,
    onClick: (Book) -> Unit,
    onBookmarked: () -> Unit
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
            TitleAndAuthorsSection(
                title = book.title,
                authors = book.authors,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                RatingSection(
                    averageRating = book.averageRating,
                    ratingsCount = book.ratingsCount,
                )

                var bookSaved by remember { mutableStateOf(false) }
                IconButton(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        bookSaved = !bookSaved
                        onBookmarked()
                    },
                ) {
                    Icon(
                        imageVector = if (bookSaved) Icons.Filled.BookmarkAdded else Icons.Outlined.BookmarkAdd,
                        contentDescription = stringResource(id = R.string.bookmark_action_accessibility_description),
                    )
                }
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

@Composable
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
}
