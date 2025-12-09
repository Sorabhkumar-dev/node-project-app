package com.sorabh.node.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun AddInput(
    modifier: Modifier,
    placeHolder: StringResource,
    maxLines: Int = 1,
    minLines: Int = 1,
    readOnly: Boolean = false,
    imageVector: ImageVector? = null,
    onIconBtnClick: () -> Unit = {},
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue,
        maxLines = maxLines,
        minLines = minLines,
        onValueChange = onValueChange,
        readOnly = readOnly,
        placeholder = {
            Text(
                text = stringResource(placeHolder),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingIcon = {
            imageVector?.let {
                IconButton(onClick = onIconBtnClick) {
                    Icon(imageVector = it, null)
                }
            }
        }
    )
}

@Composable
fun AddInput(
    modifier: Modifier,
    label: StringResource? = null,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    imageVector: ImageVector? = null,
    onIconBtnClick: () -> Unit = {},
    text: String,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        readOnly = readOnly,
        maxLines = maxLines,
        label = {
            label?.let {
                Text(
                    text = stringResource(label),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        leadingIcon = {
            imageVector?.let {
                IconButton(onClick = onIconBtnClick) {
                    Icon(imageVector = it, null, tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> OutlinedDropdown(
    modifier: Modifier = Modifier,
    label: String? = null,
    items: List<T>,
    selectedItem: T?,
    // Helper to convert your object T to a String for display
    itemLabel: (T) -> String = { it.toString() },
    onItemSelected: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    // We use ExposedDropdownMenuBox for the standard M3 behavior
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedItem?.let { itemLabel(it) } ?: "",
            onValueChange = {}, // Read only, value changes via selection
            readOnly = true,
            label = { label?.let { Text(text = label) } },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable) // Critical: links the text field to the menu
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.background
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = itemLabel(item),
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}