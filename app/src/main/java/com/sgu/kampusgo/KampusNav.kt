package com.sgu.kampusgo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

private fun NavHostController.openTab(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
@Composable
fun KampusApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val showBar = route == "events" || route == "notes" || route == "profile"
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = route == "events",
                        onClick = { navController.openTab("events") },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Events") },
                        label = { Text("Events") },
                    )
                    NavigationBarItem(
                        selected = route == "notes",
                        onClick = { navController.openTab("notes") },
                        icon = { Icon(Icons.Filled.Edit, contentDescription = "Notes") },
                        label = { Text("Notes") },
                    )
                    NavigationBarItem(
                        selected = route == "profile",
                        onClick = { navController.openTab("profile") },
                        icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "events",
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("events") {
                var showEmpty by remember { mutableStateOf(false) }
                Column(Modifier.fillMaxSize()) {
                    EventList(
                        events = if (showEmpty) emptyList() else sampleEvents,
                        onEventClick = { id -> navController.navigate("event/$id") },
                        modifier = Modifier.weight(1f),
                    )
                    Button(onClick = { showEmpty = !showEmpty }) {
                        Text(if (showEmpty) "Show events" else "Show empty")
                    }
                }
            }
            composable(
                route = "event/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    defaultValue = ""
                }),
            ) { entry ->
                val id = entry.arguments?.getString("id") ?: ""
                EventDetailScreen(
                    id = id,
                    onBack = { navController.popBackStack() },
                )
            }
            composable("notes") {
                NotesScreen()
            }
            composable("profile") {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    ProfileForm(modifier = Modifier.padding(24.dp))
                }
            }
        }
    }
}
