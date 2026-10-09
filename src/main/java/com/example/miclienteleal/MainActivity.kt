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
import androidx.compose.material.icons.filled.Android
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miclienteleal.screens.FeedbackScreen
import com.example.miclienteleal.screens.HomeScreen
import com.example.miclienteleal.screens.ProfileScreen
import com.example.miclienteleal.screens.SettingsScreen
import com.example.miclienteleal.screens.LoginScreen
import com.example.miclienteleal.ui.theme.MiClienteLealTheme
import com.example.miclienteleal.ui.theme.ThemeMode
import com.example.miclienteleal.ThemePreferences
import com.example.miclienteleal.screens.ModifyProfileScreen
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
                    },
                    onNavigate = {}
                )
            }
        }
    }
}

object destinations {
    const val HOME = "HomeScreen"
    const val PROFILE = "ProfileScreen"
    const val MODIFY_PROFILE = "ModifyProfileScreen"
    const val SETTINGS = "SettingsScreen"
    const val FEEDBACK = "CommentsScreen"
    const val LOGIN = "LoginScreen"
}
@Preview
@Composable
fun AppResponsive(
    currentTheme: ThemeMode = ThemeMode.SYSTEM,
    onThemeChange: (ThemeMode) -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    var currentScreen by rememberSaveable() { mutableStateOf(destinations.HOME) }

    BackHandler(enabled = currentScreen != destinations.HOME) {
        currentScreen = destinations.HOME
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
                                icon = { Icon(Icons.Default.Home, contentDescription = "Principal") },
                                label = { Text("Inicio") },
                                selected = currentScreen == "home",
                                onClick = { currentScreen = destinations.HOME }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Mi cuenta") },
                                label = { Text("Mi Cuenta") },
                                selected = currentScreen == "profile",
                                onClick = { currentScreen = destinations.PROFILE }
                            )
                        }
                        Spacer(modifier = Modifier.weight(1F))
                        NavigationDrawerItem(
                            icon = {Icon(Icons.Default.MailOutline, contentDescription = "Enviar comentarios")},
                            label = { Text("Comentarios")},
                            selected = currentScreen == "feedback",
                            onClick = {currentScreen = destinations.FEEDBACK}
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
                            label = { Text("Configuración") },
                            selected = currentScreen == "settings",
                            onClick = { currentScreen = destinations.SETTINGS }
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
                    destinations.HOME -> "Principal"
                    destinations.LOGIN -> "Iniciar sesión o Registrarte"
                    destinations.PROFILE -> "Mi Perfil"
                    destinations.MODIFY_PROFILE -> "Editar Perfil"
                    destinations.FEEDBACK -> "Enviar comentarios"
                    destinations.SETTINGS -> "Configuración"
                    else -> ""
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
                                    currentScreen = destinations.FEEDBACK
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
                                    currentScreen = destinations.SETTINGS
                                }
                            )

                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar{
                    NavigationBarItem(
                        selected = currentScreen == destinations.HOME,
                        onClick = { currentScreen = destinations.HOME },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == destinations.PROFILE || currentScreen == destinations.MODIFY_PROFILE,
                        onClick = { currentScreen = destinations.PROFILE },
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
                onNavigate = { destinations -> currentScreen = destinations },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MainContent (
    currentScreen: String,
    currentTheme: ThemeMode,
    usrBirthday: String = "",
    onBirthdayChange: (String) -> Unit = {},
    onThemeChange: (ThemeMode) -> Unit = {},
    onNavigate: (String) -> Unit = {},
    modifier: Modifier = Modifier) {

    Box(modifier = modifier.fillMaxSize()) {
        when (currentScreen) {
            destinations.HOME -> HomeScreen()
            destinations.PROFILE -> ProfileScreen(
                onEditProfileClick = { onNavigate(destinations.MODIFY_PROFILE)
                })
            destinations.MODIFY_PROFILE -> ModifyProfileScreen(
                onNavBack = { onNavigate(destinations.PROFILE) },
                birthday = usrBirthday,
                onBirthdayChange = onBirthdayChange
            )
            destinations.SETTINGS -> SettingsScreen(currentTheme, onThemeChange)
            destinations.FEEDBACK -> FeedbackScreen()
            destinations.LOGIN -> LoginScreen()
        }
    }
}