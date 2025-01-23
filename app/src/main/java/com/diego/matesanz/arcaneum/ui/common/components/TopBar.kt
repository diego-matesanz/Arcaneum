package com.diego.matesanz.arcaneum.ui.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    title: String? = null,
    dominantColor: Color? = null,
    navigationIcon: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Normal),
                )
            }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = navigationIcon,
        colors = dominantColor?.let {
            TopAppBarDefaults.topAppBarColors(
                containerColor = dominantColor,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        } ?: TopAppBarDefaults.topAppBarColors(),
        expandedHeight = 48.dp
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavigationBackTopBar(
    onBack: () -> Unit,
    title: String? = null,
    dominantColor: Color? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopBar(
        title = title,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(
                        id = R.string.go_back_action_accessibility_description
                    ),
                )
            }
        },
        dominantColor = dominantColor,
    )
}
