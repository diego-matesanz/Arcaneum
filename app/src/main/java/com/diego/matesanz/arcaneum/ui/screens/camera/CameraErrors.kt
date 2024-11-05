package com.diego.matesanz.arcaneum.ui.screens.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.R

@Composable
fun BookResultError(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp,
            )
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(R.drawable.ic_error),
                contentDescription = stringResource(R.string.error_scanning_state_accessibility_description),
            )
            Text(
                text = stringResource(R.string.error_scanning_book),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}
