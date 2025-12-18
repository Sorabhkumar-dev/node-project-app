package com.sorabh.node.pojo

import androidx.compose.ui.graphics.vector.ImageVector
import com.sorabh.node.utils.TopBarEvent
import org.jetbrains.compose.resources.StringResource

data class AppBar(
    val title: StringResource,
    val icon: ImageVector? = null,
    val event: TopBarEvent? = null
)
