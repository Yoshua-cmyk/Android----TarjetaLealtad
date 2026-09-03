package com.example.miclienteleal

import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.miclienteleal.screens.FeedbackScreen
import com.example.miclienteleal.screens.HomeScreen
import com.example.miclienteleal.screens.ProfileScreen
import com.example.miclienteleal.screens.SettingsScreen
import com.example.miclienteleal.screens.LoginScreen
import com.example.miclienteleal.ui.theme.MiClienteLealTheme
import com.example.miclienteleal.ui.theme.ThemeMode
import com.example.miclienteleal.ThemePreferences
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val themePreferences = remember { ThemePreferences(context) }
            val scope = rememberCoroutineScope()

            val currentTheme by themePreferences.themeModeFlow.collectAsState(initial = ThemeMode.SYSTEM)
            val activeTheme = currentTheme ?: ThemeMode.SYSTEM

            MiClienteLealTheme(themeMode = activeTheme) {
                AppResponsive(
                    currentTheme = activeTheme,
                    onThemeChange = { newTheme ->
                        scope.launch {
                            themePreferences.saveThemeMode(newTheme)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun AppResponsive(
    currentTheme: ThemeMode = ThemeMode.SYSTEM,
    onThemeChange: (ThemeMode) -> Unit = {}
) {
    var currentScreen by remember { mutableStateOf("home") }

    BackHandler(enabled = currentScreen != "home") {
        currentScreen = "home"
    }

    val adaptiveInfo = LocalConfiguration.current
    val isTabletOrWideScreen = adaptiveInfo.orientation == Configuration.ORIENTATION_LANDSCAPE || adaptiveInfo.screenWidthDp >= 600

    if (isTabletOrWideScreen) {
        //Vista Horizontal, Tablet
        @OptIn(ExperimentalMaterial3Api::class)
        val topBarTitle = when (currentScreen) {
            "home" -> "Principal"
            "login" -> "Iniciar sesión o Registrarte"
            "profile" -> "Mi Perfil"
            "feedback" -> "Enviar comentarios"
            "settings" -> "Configuración"
            else -> ""
        }
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet (
                    modifier = Modifier.width(240.dp)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        Column {
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                                label = { Text("Inicio") },
                                selected = currentScreen == "home",
                                onClick = { currentScreen = "home" }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                                label = { Text("Mi Cuenta") },
                                selected = currentScreen == "profile",
                                onClick = { currentScreen = "profile" }
                            )
                        }
                        Spacer(modifier = Modifier.weight(1F))
                        NavigationDrawerItem(
                            icon = {Icon(Icons.Default.MailOutline, contentDescription = null)},
                            label = { Text("Comentarios")},
                            selected = currentScreen == "feedback",
                            onClick = {currentScreen = "feedback"}
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                            label = { Text("Configuración") },
                            selected = currentScreen == "settings",
                            onClick = { currentScreen = "settings" }
                        )
                    }
                }
            }
        ){
            Scaffold(
                topBar = {
                    @OptIn(ExperimentalMaterial3Api::class)
                    TopAppBar(
                        title = {Text(text = topBarTitle)}
                    )
                }
            ) {innerPadding ->
                MainContent(
                    currentScreen = currentScreen,
                    currentTheme = currentTheme,
                    onThemeChange = onThemeChange,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    } else {
        //Vista vertical
        var showMenu by remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                val topBarTitle = when (currentScreen) {
                    "home" -> "Principal"
                    "login" -> "Iniciar sesión o Registrarte"
                    "profile" -> "Mi Perfil"
                    "feedback" -> "Enviar comentarios"
                    "settings" -> "Configuración"
                    else -> "AndroidApp"
                }
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text(text = topBarTitle) },
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Ver más"
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false}
                        ) {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null
                                    )
                                },
                                text = { Text("Envia tus comentarios") },
                                onClick = {
                                    showMenu = false
                                    currentScreen = "feedback"
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = null
                                    )
                                },
                                text = { Text("Configuración") },
                                onClick = {
                                    showMenu = false
                                    currentScreen = "settings"
                                }
                            )

                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar{
                    NavigationBarItem(
                        selected = currentScreen == "home",
                        onClick = { currentScreen = "home" },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == "profile",
                        onClick = { currentScreen = "profile" },
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Mi cuenta") },
                        label = { Text("Mi Cuenta") }
                    )
                }
            }
        ) { innerPadding ->
            MainContent(
                currentScreen = currentScreen,
                currentTheme = currentTheme,
                onThemeChange = onThemeChange,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MainContent (
    currentScreen: String,
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit = {},
    modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        when (currentScreen) {
            "home" -> HomeScreen()
            "profile" -> ProfileScreen()
            "settings" -> SettingsScreen(currentTheme, onThemeChange)
            "feedback" -> FeedbackScreen()
            "login" -> LoginScreen()
        }
    }
}