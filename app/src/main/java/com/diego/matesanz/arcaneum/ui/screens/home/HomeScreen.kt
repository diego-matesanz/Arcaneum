package com.diego.matesanz.arcaneum.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onBookClick: (Book) -> Unit,
    onCamClick: () -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.searchBooks("Brandon Sanderson")
    }

    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Normal),
                        )
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    columns = GridCells.Adaptive(180.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = padding,
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SearchBar(
                            onCamClick = onCamClick,
                            onSearch = viewModel::searchBooks,
                        )
                    }
                    items(state.books) { book ->
                        BookItem(book = book, onClick = onBookClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    onCamClick: () -> Unit,
    onSearch: (String) -> Unit,
) {
    var textSearch by remember { mutableStateOf("") }
    TextField(
        value = textSearch,
        onValueChange = { textSearch = it },
        label = { Text(text = stringResource(id = R.string.search_label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(textSearch) }
        ),
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = {
                onSearch(textSearch)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = onCamClick) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = stringResource(id = R.string.open_camera),
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
    )
}

@Composable
private fun BookItem(book: Book, onClick: (Book) -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = { onClick(book) }),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1 / 1.5F)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Gray),
            model = book.coverImage,
            contentDescription = book.title,
        )
        BookInfo(
            title = book.title,
            author = book.authors.firstOrNull() ?: "",
        )
    }
}

@Composable
private fun BookInfo(
    title: String,
    author: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
        )
        Text(
            text = author,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
        )
    }
}
