package com.diego.matesanz.arcaneum.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.diego.matesanz.arcaneum.R

@Composable
fun AddShelfDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                TitleAndMessage(
                    title = stringResource(id = R.string.add_shelf_dialog_title),
                    message = stringResource(id = R.string.add_shelf_dialog_message),
                )
                ShelfNameTextField(
                    text = text,
                    isError = isError,
                    onValueChange = {
                        isError = false
                        text = it
                    },
                )
                ConfirmAndDismissButtons(
                    onDismissRequest = onDismissRequest,
                    onConfirmation = {
                        when {
                            text.isNotEmpty() -> {
                                onConfirmation(text)
                                onDismissRequest()
                            }

                            else -> isError = true
                        }
                    },
                    confirmButtonText = stringResource(id = R.string.add_shelf_dialog_confirm_button),
                    dismissButtonText = stringResource(id = R.string.add_shelf_dialog_dismiss_button),
                )
            }
        }
    }
}

@Composable
@Preview
private fun AddShelfDialogPreview() {
    AddShelfDialog(
        onDismissRequest = { },
        onConfirmation = { },
    )
}

@Composable
fun RemoveShelfDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete shelf icon"
                )
                TitleAndMessage(
                    title = stringResource(id = R.string.remove_shelf_dialog_title),
                    message = stringResource(id = R.string.remove_shelf_dialog_message),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    textAlign = TextAlign.Center,
                )
                ConfirmAndDismissButtons(
                    onDismissRequest = onDismissRequest,
                    onConfirmation = onConfirmation,
                    confirmButtonText = stringResource(id = R.string.remove_shelf_dialog_confirm_button),
                    dismissButtonText = stringResource(id = R.string.remove_shelf_dialog_dismiss_button),
                )
            }
        }
    }
}

@Composable
@Preview
private fun RemoveShelfDialogPreview() {
    RemoveShelfDialog(
        onDismissRequest = { },
        onConfirmation = { },
    )
}

@Composable
private fun TitleAndMessage(
    title: String,
    message: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    textAlign: TextAlign = TextAlign.Start,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = horizontalAlignment,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = textAlign,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            textAlign = textAlign,
        )
    }
}

@Composable
private fun ShelfNameTextField(
    text: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        textStyle = MaterialTheme.typography.bodyLarge,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = stringResource(id = R.string.add_shelf_dialog_error_message),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.add_shelf_dialog_text_field_placeholder),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
    )
}

@Composable
private fun ConfirmAndDismissButtons(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedButton(
            onClick = onDismissRequest,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = dismissButtonText,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
        Button(
            onClick = onConfirmation,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = confirmButtonText,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}
