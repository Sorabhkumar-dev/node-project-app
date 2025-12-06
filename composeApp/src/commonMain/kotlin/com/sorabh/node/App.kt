package com.sorabh.node

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sorabh.node.nav.AddTaskNav
import com.sorabh.node.nav.AppNavigation
import com.sorabh.node.theme.BlackAndWhiteScheme
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.today_task
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val viewModel = koinInject<AppViewModel>()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val isAddTask = currentDestination?.hasRoute<AddTaskNav>() == true

    MaterialTheme(colorScheme = BlackAndWhiteScheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AnimatedVisibility(!isAddTask) {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                        title = {
                            Text(
                                text = stringResource(
                                    viewModel.appBarState.collectAsState().value?.title
                                        ?: Res.string.today_task
                                ),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        })
                }
            },
            bottomBar = {
                AnimatedVisibility(!isAddTask) {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.primary.copy(0.1f),
                        actions = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                viewModel.bottomBar.forEach {
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = it,
                                            contentDescription = null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(AddTaskNav)
                                },
                                containerColor = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    )
                }
            }
        ) {
            AppNavigation(navController = navController, paddingValues = it)
        }
    }
}