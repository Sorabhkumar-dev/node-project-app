package com.sorabh.node.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun AddInput(
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
    label: StringResource? = null,
    maxLines: Int = 1,
    minLines: Int = 1,
    readOnly: Boolean = false,
    color: Color? = null,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = textFieldValue,
        textStyle = textStyle,
        onValueChange = onValueChange,
        readOnly = readOnly,
        minLines = minLines,
        maxLines = maxLines,
        decorationBox = {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraSmall,
                elevation = CardDefaults.elevatedCardElevation(3.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                label?.let {
                    Text(
                        text = stringResource(label),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    it()
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@Composable
fun AddInput(
    modifier: Modifier,
    label: StringResource? = null,
    placeHolder: StringResource? = null,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    imageVector: ImageVector? = null,
    onIconBtnClick: () -> Unit = {},
    text: String,
    onValueChange: (String) -> Unit = {}
) {

    TextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        readOnly = readOnly,
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            placeHolder?.let {
                Text(
                    text = stringResource(placeHolder),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light
                )
            }
        },
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

@Composable
fun OutlinedAddInput(
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
fun OutlinedAddInput(
    modifier: Modifier,
    label: StringResource? = null,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    imageVector: ImageVector? = null,
    trailingIcon: ImageVector? = null,
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
        },
        trailingIcon = {
            trailingIcon?.let {
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
                Icon(imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, null)
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

@Stable
@Composable
fun <T> DropdownCard(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedItem: T?,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    label: @Composable (T) -> Unit,
    leadingIcon: (@Composable (T) -> Unit)? = null,
    cardColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = cardColor,
            contentColor = contentColor
        )
    ) {
        Column {

            // ---------- HEADER ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onExpandedChange(!expanded)
                    }
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    selectedItem?.let {
                        leadingIcon?.invoke(it)
                        Spacer(Modifier.width(8.dp))
                        label(it)
                    }
                }

                Icon(
                    imageVector = if (expanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }

            // ---------- CONTENT ----------
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    items.fastForEach { item ->
                        DropdownMenuItem(
                            text = { label(item) },
                            leadingIcon = {
                                leadingIcon?.invoke(item)
                            },
                            onClick = {
                                onItemSelected(item)
                                onExpandedChange(false)
                            }
                        )
                    }
                }
            }
        }
    }
}

