package com.materiapps.gloom.ui.screens.root

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.materiapps.gloom.utils.RootTab

class RootScreen : Screen {

    @Composable
    override fun Content() = Screen()

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Screen(
    ) {
        TabNavigator(tab = RootTab.HOME.tab) { nav ->
            Scaffold(
                bottomBar = { TabBar() }
            ) {
                nav.current.Content()
            }
        }
    }

    @Composable
    private fun TabBar() {
        val navigator = LocalTabNavigator.current

        NavigationBar {
            RootTab.values().forEach {
                NavigationBarItem(
                    selected = navigator.current == it.tab,
                    onClick = { navigator.current = it.tab },
                    icon = {
                        Icon(
                            painter = it.tab.options.icon!!,
                            contentDescription = it.tab.options.title
                        )
                    },
                    label = { Text(text = it.tab.options.title) }
                )
            }
        }
    }

}