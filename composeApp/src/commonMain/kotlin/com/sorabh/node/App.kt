package com.sorabh.node

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.sorabh.node.nav.AddTaskNav
import com.sorabh.node.nav.AllTaskNav
import com.sorabh.node.nav.AppNavigation
import com.sorabh.node.nav.BottomBarHider
import com.sorabh.node.nav.ImportantTaskNav
import com.sorabh.node.nav.RepeatTaskNav
import com.sorabh.node.nav.TodayTaskNav
import com.sorabh.node.theme.BlackAndWhiteScheme
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.today_task
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val navController = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(AllTaskNav::class, AllTaskNav.serializer())
                    subclass(TodayTaskNav::class, TodayTaskNav.serializer())
                    subclass(RepeatTaskNav::class, RepeatTaskNav.serializer())
                    subclass(AddTaskNav::class, AddTaskNav.serializer())
                    subclass(ImportantTaskNav::class, ImportantTaskNav.serializer())
                }
            }
        },
        TodayTaskNav
    )

    val viewModel = koinViewModel<AppViewModel>()
    val appBarState = viewModel.appBarState.value

    MaterialTheme(colorScheme = BlackAndWhiteScheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
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
                AnimatedVisibility(navController.last() !is BottomBarHider) {
                    NavigationBar {
                        viewModel.bottomBar.forEach {
                            NavigationBarItem(
                                selected = false,
                                icon = {
                                    IconButton({}) {
                                        Icon(
                                            imageVector = it,
                                            contentDescription = null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                },
                                onClick = {}
                            )
                        }
                    }
                }
            }
        ) {
            AppNavigation(navBackStack = navController, viewModel = viewModel, paddingValues = it)
        }
    }
}