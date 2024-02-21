package com.rajit.bottomnavbarcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rajit.bottomnavbarcompose.ui.theme.BottomNavBarComposeTheme
import com.rajit.bottomnavbarcompose.ui.theme.Purple40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavBarComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScaffold()
                }
            }
        }
    }
}

@Composable
fun MyScaffold() {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = { MyTopAppBar(currentBackStackEntry, navController) },
        bottomBar = { MyBottomNavigationBar(currentBackStackEntry, navController) },
        floatingActionButton = { MyFloatingActionButton(currentBackStackEntry) }
    ) { contentPadding ->
        MyNavigationController(contentPadding, navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {

    val currentRoute = currentBackStackEntry?.destination?.route

    val topAppBarTitle = if (currentRoute != null && currentRoute == "detail") {
        "Heritage Details"
    } else {
        "World Heritages"
    }

    Log.i("MainActivity", "Current Route: $currentRoute")

    TopAppBar(
        title = { Text(topAppBarTitle) },
        navigationIcon = {
            if (topAppBarTitle == "Heritage Details") {
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Up Button"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Purple40,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun MyFloatingActionButton(currentBackStackEntry: NavBackStackEntry?) {

    val mContext = LocalContext.current
    val currentRoute = currentBackStackEntry?.destination?.route

    FloatingActionButton(
        onClick = {
            Toast.makeText(mContext, "Fab Clicked!", Toast.LENGTH_SHORT).show()
        }
    ) {

        val fabIcon = if (currentRoute != "detail") {
            Icons.Default.Home
        } else {
            Icons.Default.BookmarkBorder
        }

        Icon(
            imageVector = fabIcon,
            contentDescription = "Fab Button"
        )
    }
}

@Composable
fun MyBottomNavigationBar(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavHostController
) {

    val currentRoute = currentBackStackEntry?.destination?.route

    if (currentRoute != "detail") {

        NavigationBar {

            // Home
            NavigationBarItem(
                selected = currentRoute == "main" || currentRoute == null,
                onClick = {
                    if(currentRoute != "main" || currentRoute != null) {
                        navController.navigate("main")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                },
                label = { Text(text = "Home") }
            )

            // Search
            NavigationBarItem(
                selected = currentRoute == "search",
                onClick = {
                    if(currentRoute != "search") {
                        navController.navigate("search")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                label = { Text(text = "Search") }
            )

            // Favourites
            NavigationBarItem(
                selected = currentRoute == "favourites",
                onClick = {
                    if(currentRoute != "favourites"){
                        navController.navigate("favourites")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Favourites"
                    )
                },
                label = { Text(text = "Favourites") }
            )

        }
    }

}

@Composable
fun MyNavigationController(
    padding: PaddingValues,
    navController: NavHostController
) {

    Box(modifier = Modifier.padding(padding)) {
        NavHost(navController = navController, startDestination = "main") {
            composable(route = "main") {
                HomeScreen(navController)
            }

            composable(route = "search") {
                SearchScreen(navController)
            }

            composable(route = "favourites") {
                FavouritesScreen()
            }

            composable(route = "detail") {
                DetailScreen()
            }
        }
    }
}

@Composable
fun DetailScreen() {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Details Screen")
        }
    }
}

@Composable
fun FavouritesScreen() {

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Favourites Screen")
        }
    }

}

@Composable
fun SearchScreen(navController: NavHostController) {

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Search Screen")

            Button(onClick = { navController.navigate("detail") }) {
                Text(text = "Navigate To Detail Screen")
            }
        }
    }

}

@Composable
fun HomeScreen(navController: NavHostController) {

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Home Screen")

            Button(onClick = { navController.navigate("detail") }) {
                Text(text = "Navigate To Detail Screen")
            }
        }
    }

}
