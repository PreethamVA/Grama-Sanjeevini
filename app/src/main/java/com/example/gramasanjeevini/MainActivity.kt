package com.example.gramasanjeevini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gramasanjeevini.ui.EmergencyScreen
import com.example.gramasanjeevini.ui.MainViewModel
import com.example.gramasanjeevini.ui.PharmacistScreen
import com.example.gramasanjeevini.ui.SearchScreen
import com.example.gramasanjeevini.ui.theme.GramaSanjeeviniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GramaSanjeeviniTheme {
                GramaSanjeeviniApp()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Search : Screen("search", "Search", Icons.Filled.Home)
    object Emergency : Screen("emergency", "Emergency", Icons.Filled.Warning)
    object Pharmacist : Screen("pharmacist", "Pharmacist", Icons.Filled.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GramaSanjeeviniApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    
    val items = listOf(
        Screen.Search,
        Screen.Emergency,
        Screen.Pharmacist
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Search.route, Modifier.padding(innerPadding)) {
            composable(Screen.Search.route) { SearchScreen(viewModel) }
            composable(Screen.Emergency.route) { EmergencyScreen(viewModel) }
            composable(Screen.Pharmacist.route) { PharmacistScreen(viewModel) }
        }
    }
}
