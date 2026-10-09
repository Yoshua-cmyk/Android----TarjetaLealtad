package com.example.miclienteleal.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Transcribe
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miclienteleal.ui.theme.ThemeMode

@Preview (showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: ThemeMode = ThemeMode.SYSTEM,
    onThemeChange: (ThemeMode) -> Unit = {}
) {
    var showThemeDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            //Configuración de perfil
            Text(
                text = "Perfil",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Column {
                    AndroidInfoTile(
                        icon = Icons.Default.Person,
                        title = "Tu perfil",
                        value = "Que quieres ver en la página Perfil",
                        onClick = { }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    AndroidInfoTile(
                        icon = Icons.Default.AccountBox,
                        title = "Tu información",
                        value = "Editar",
                        onClick = { }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            //Configuración de apariencia
            Text(
                text = "Apariencia",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Column {
                    AndroidInfoTile(
                        icon = Icons.Default.Brush,
                        title = "Cambiar tema",
                        value = when (currentTheme) {
                            ThemeMode.LIGHT -> "Sonrisa del sol"
                            ThemeMode.SUNSET -> "Sombra del ocaso"
                            ThemeMode.DARK -> "Manto de luna"
                            ThemeMode.SYSTEM -> "Ritmo Natural"
                            ThemeMode.DYNAMIC -> "Baile del sol"
                        },
                        onClick = { showThemeDialog = true }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    AndroidInfoTile(
                        icon = Icons.Default.Translate,
                        title = "Cambiar idioma",
                        value = "Selecciona tu idioma preferidoß",
                        onClick = { }
                    )

                    AndroidInfoTile(
                        icon = Icons.Default.Animation,
                        title = "Animaciones",
                        value = "Activar o desactivar animaciones",
                        onClick = { }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    AndroidInfoTile(
                        icon = Icons.Default.Star,
                        title = "Indicador o sello",
                        value = "Selecciona tu sello de visitas",
                        onClick = { }
                    )
                }
            }

            if (showThemeDialog) {
                ThemeSelectionDialog(
                    currentTheme = currentTheme,
                    onThemeSelected = { newTheme ->
                        onThemeChange(newTheme)
                        showThemeDialog = false
                    },
                    onDismissRequest = { showThemeDialog = false }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            //Configuración de Notificaciones
            Text(
                text = "Sonidos",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Column {
                    AndroidInfoTile(
                        icon = Icons.Default.Notifications,
                        title = "Notificaciones",
                        value = "Activar o desactivar notificaciones",
                        onClick = { }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            //Configuración de accesibilidad
            Text(
                text = "Accesibilidad",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Column {
                    AndroidInfoTile(
                        icon = Icons.Default.Contrast,
                        title = "Contraste",
                        value = "Temas de alto contraste",
                        onClick = { }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    AndroidInfoTile(
                        icon = Icons.Default.ZoomIn,
                        title = "Ampliación y zoom",
                        value = "Activar o desactivar ampliación",
                        onClick = { }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    AndroidInfoTile(
                        icon = Icons.Default.RemoveRedEye,
                        title = "Daltonismo",
                        value = "Temas para personas con daltonismo",
                        onClick = { }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            //Configuración adicional
            Text(
                text = "Otros",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Column {
                    AndroidInfoTile(
                        icon = Icons.Default.Info,
                        title = "Información",
                        value = "Conoce más sobre esta app",
                        onClick = { }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ThemeSelectionDialog(
    currentTheme: ThemeMode = ThemeMode.SYSTEM,
    onThemeSelected: (ThemeMode) -> Unit = {},
    onDismissRequest: () -> Unit
) {
    var tempTheme by remember { mutableStateOf(currentTheme) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Usar el tema:",
                style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column{
                ThemeOptionRow(
                    text = "Sonrisa del sol (claro)",
                    selected = tempTheme == ThemeMode.LIGHT,
                    onClick = { tempTheme = ThemeMode.LIGHT }
                )
                ThemeOptionRow(
                    text = "Sombra del ocaso (atardecer)",
                    selected = tempTheme == ThemeMode.SUNSET,
                    onClick = { tempTheme = ThemeMode.SUNSET }
                )
                ThemeOptionRow(
                    text = "Manto de luna (Oscuro)",
                    selected = tempTheme == ThemeMode.DARK,
                    onClick = { tempTheme = ThemeMode.DARK }
                )
                ThemeOptionRow(
                    text = "Ritmo Natural (Del sistema)",
                    selected = tempTheme == ThemeMode.SYSTEM,
                    onClick = { tempTheme = ThemeMode.SYSTEM }
                )
                ThemeOptionRow(
                    text = "Baile del sol (Dinámico)",
                    selected = tempTheme == ThemeMode.DYNAMIC,
                    onClick = { tempTheme = ThemeMode.DYNAMIC }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onThemeSelected(tempTheme)
                    onDismissRequest()
                }
            ) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun ThemeOptionRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}