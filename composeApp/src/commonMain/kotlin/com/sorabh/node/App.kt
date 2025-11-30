package com.sorabh.node

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sorabh.node.nav.AppNavigation
import com.sorabh.node.theme.BlackAndWhiteScheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(colorScheme = BlackAndWhiteScheme) {
        val navController = rememberNavController()
        AppNavigation(navController = navController)
    }
}