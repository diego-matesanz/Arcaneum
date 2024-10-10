package com.example.architectcoders.ui.screens.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.architectcoders.Book
import com.example.architectcoders.R
import com.example.architectcoders.books
import com.example.architectcoders.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailScreen() {
    val book = books.first()
    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = book.title) },
                    navigationIcon = {
                        IconButton(onClick = {/*TODO*/ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.go_back),
                            )
                        }
                    }
                )
            }
        ) { padding ->
            BookDetail(
                book = book,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun BookDetail(
    book: Book,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9F),
            model = book.coverUrl,
            contentDescription = book.title,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = book.title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}