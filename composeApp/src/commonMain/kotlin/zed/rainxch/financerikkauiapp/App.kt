package zed.rainxch.financerikkauiapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.ui.toast.LocalToastHostState
import zed.rainxch.rikkaui.components.ui.toast.ToastHost
import zed.rainxch.rikkaui.components.ui.toast.rememberToastHostState
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.dashboard.DashboardScreen
import zed.rainxch.financerikkauiapp.navigation.Screen
import zed.rainxch.financerikkauiapp.settings.SettingsScreen
import zed.rainxch.financerikkauiapp.theme.FinanceDashboardTheme

@Composable
fun App() {
    FinanceDashboardTheme {
        val toastHostState = rememberToastHostState()

        CompositionLocalProvider(LocalToastHostState provides toastHostState) {
            var currentScreen by remember { mutableStateOf(Screen.Dashboard) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RikkaTheme.colors.background)
                    .safeDrawingPadding(),
            ) {
                when (currentScreen) {
                    Screen.Dashboard -> DashboardScreen(
                        onNavigate = { currentScreen = it },
                    )
                    Screen.Settings -> SettingsScreen(
                        onNavigate = { currentScreen = it },
                    )
                }

                // Toast overlay
                ToastHost(hostState = toastHostState)
            }
        }
    }
}
