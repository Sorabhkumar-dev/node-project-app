package com.sorabh.node.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.add_task
import node.composeapp.generated.resources.lets_kickstart_your_day
import node.composeapp.generated.resources.nothing_here_yet_but_you_re_just_one_tap_away_from_progress
import node.composeapp.generated.resources.todo_task_list
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun EmptyTaskState(
    image: DrawableResource = Res.drawable.todo_task_list,
    title: StringResource = Res.string.lets_kickstart_your_day,
    description: StringResource = Res.string.nothing_here_yet_but_you_re_just_one_tap_away_from_progress,
    buttonText: StringResource = Res.string.add_task,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 36.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily.Cursive,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(description),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onClick,
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text(text = stringResource(buttonText), color = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null
            )
        }

    }
}