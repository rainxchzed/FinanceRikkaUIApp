package zed.rainxch.financerikkauiapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import zed.rainxch.financerikkauiapp.dashboard.DashboardRoot
import zed.rainxch.financerikkauiapp.dashboard.DashboardViewModel
import zed.rainxch.financerikkauiapp.settings.SettingsRoot
import zed.rainxch.financerikkauiapp.settings.SettingsViewModel
import zed.rainxch.financerikkauiapp.stub.StubScreen

@Composable
fun AppNavigation() {
    val navStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(
                        FinanceAppGraph.Dashboard::class,
                        FinanceAppGraph.Dashboard.serializer(),
                    )
                    subclass(
                        FinanceAppGraph.Portfolio::class,
                        FinanceAppGraph.Portfolio.serializer(),
                    )
                    subclass(
                        FinanceAppGraph.Activity::class,
                        FinanceAppGraph.Activity.serializer(),
                    )
                    subclass(
                        FinanceAppGraph.Cards::class,
                        FinanceAppGraph.Cards.serializer(),
                    )
                    subclass(
                        FinanceAppGraph.Settings::class,
                        FinanceAppGraph.Settings.serializer(),
                    )
                }
            }
        },
        FinanceAppGraph.Dashboard,
    )

    NavDisplay(
        backStack = navStack,
        onBack = {
            navStack.removeLastOrNull()
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<FinanceAppGraph.Dashboard> {
                val viewModel: DashboardViewModel = viewModel { DashboardViewModel() }
                DashboardRoot(
                    viewModel = viewModel,
                    onNavigate = { route ->
                        navStack.add(route)
                    },
                )
            }

            entry<FinanceAppGraph.Portfolio> {
                StubScreen(
                    title = "Portfolio",
                    onNavigateBack = { navStack.removeLastOrNull() },
                )
            }

            entry<FinanceAppGraph.Activity> {
                StubScreen(
                    title = "Activity",
                    onNavigateBack = { navStack.removeLastOrNull() },
                )
            }

            entry<FinanceAppGraph.Cards> {
                StubScreen(
                    title = "Cards",
                    onNavigateBack = { navStack.removeLastOrNull() },
                )
            }

            entry<FinanceAppGraph.Settings> {
                val viewModel: SettingsViewModel = viewModel { SettingsViewModel() }
                SettingsRoot(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navStack.removeLastOrNull()
                    },
                )
            }
        },
    )
}
