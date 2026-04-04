package zed.rainxch.financerikkauiapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.ui.toast.LocalToastHostState
import zed.rainxch.rikkaui.components.ui.toast.ToastHost
import zed.rainxch.rikkaui.components.ui.toast.rememberToastHostState
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.navigation.AppNavigation
import zed.rainxch.financerikkauiapp.theme.FinanceDashboardTheme

@Composable
fun App() {
    FinanceDashboardTheme {
        val toastHostState = rememberToastHostState()

        CompositionLocalProvider(LocalToastHostState provides toastHostState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RikkaTheme.colors.background)
                    .safeDrawingPadding(),
            ) {
                AppNavigation()

                // Toast overlay
                ToastHost(hostState = toastHostState)
            }
        }
    }
}
