package com.sorabh.node

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sorabh.node.nav.AddTaskNav
import com.sorabh.node.nav.AllTaskNav
import com.sorabh.node.nav.AppNavigation
import com.sorabh.node.nav.BottomBar
import com.sorabh.node.nav.ImportantTaskNav
import com.sorabh.node.nav.RepeatTaskNav
import com.sorabh.node.nav.TodayTaskNav
import com.sorabh.node.theme.BlackAndWhiteDarkScheme
import com.sorabh.node.theme.BlackAndWhiteScheme
import com.sorabh.node.utils.DismissSnackBarEvent
import com.sorabh.node.utils.ShowSnackBarEvent
import com.sorabh.node.utils.TaskPriority
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.today_task
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<AppViewModel>()
    val appBarState = viewModel.appBarState.value
    val snackBarEvent = viewModel.snackBarEvent
    val snackBarIcon = remember { mutableStateOf<ImageVector?>(null) }
    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val topDestination =
        currentDestination?.hasRoute<TodayTaskNav>() == true || currentDestination?.hasRoute<ImportantTaskNav>() == true || currentDestination?.hasRoute<AllTaskNav>() == true || currentDestination?.hasRoute<RepeatTaskNav>() == true

    val themeIcon = viewModel.readTheme.collectAsState(false).value

    LaunchedEffect(Unit) {
        snackBarEvent.collect {
            when (it) {
                DismissSnackBarEvent -> snackBarHostState.currentSnackbarData?.dismiss()
                is ShowSnackBarEvent -> {
                    snackBarIcon.value = it.icon
                    snackBarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    MaterialTheme(colorScheme = if (themeIcon) BlackAndWhiteScheme else BlackAndWhiteDarkScheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AnimatedContent(
                                targetState = stringResource(
                                    appBarState?.title
                                        ?: Res.string.today_task
                                ),
                                transitionSpec = {
                                    slideInVertically { it } + fadeIn() togetherWith
                                            slideOutVertically { -it } + fadeOut()
                                },
                                label = ""
                            ) { target ->
                                Text(text = target)
                            }

                            IconButton(onClick = {
                                viewModel.writeTheme(!themeIcon)
                            }) {
                                Icon(
                                    imageVector = if (themeIcon) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }

                        }
                    },
                    actions = {
                        AnimatedContent(
                            targetState = appBarState?.icon,
                            transitionSpec = {
                                slideInVertically { it } + fadeIn() togetherWith
                                        slideOutVertically { -it } + fadeOut()
                            },
                            label = ""
                        ) { target ->
                            IconButton(onClick = {
                                appBarState?.event?.let { event ->
                                    viewModel.sendEvent(event)
                                }
                            }) {
                                target?.let {
                                    Icon(
                                        imageVector = target,
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        }
                    }
                )
            },
            bottomBar = {
                if (topDestination)
                    NavigationBar {
                        viewModel.bottomBar.forEach {
                            NavigationBarItem(
                                selected = currentDestination.hasRoute(it::class),
                                icon = {
                                    it as BottomBar
                                    Icon(
                                        imageVector = when (it) {
                                            AllTaskNav -> Icons.AutoMirrored.Rounded.Article
                                            ImportantTaskNav -> Icons.Rounded.Star
                                            RepeatTaskNav -> Icons.Rounded.Repeat
                                            TodayTaskNav -> Icons.Rounded.Today
                                        },
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                },
                                onClick = {
                                    navController.navigate(it) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.primary.copy(
                                        0.15f
                                    )
                                )
                            )
                        }
                    }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onBackground)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.visuals.message,
                            color = MaterialTheme.colorScheme.background,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        snackBarIcon.value?.let { imageVector ->
                            Icon(
                                imageVector = imageVector,
                                contentDescription = null,

                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                if (topDestination)
                    FloatingActionButton(onClick = {
                        navController.navigate(
                            AddTaskNav(
                                priority = if (currentDestination.hasRoute<ImportantTaskNav>()) TaskPriority.HIGH else TaskPriority.MEDIUM,
                                isRepeatable = currentDestination.hasRoute<RepeatTaskNav>()
                            )
                        )
                    }) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                    }
            }
        ) {
            AppNavigation(navController = navController, viewModel = viewModel, paddingValues = it)
        }
    }
}