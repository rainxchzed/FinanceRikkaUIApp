package zed.rainxch.financerikkauiapp.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialog
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogAction
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogActionVariant
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogFooter
import zed.rainxch.rikkaui.components.ui.alertdialog.AlertDialogHeader
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.button.IconButton
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.checkbox.Checkbox
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.input.Input
import zed.rainxch.rikkaui.components.ui.label.Label
import zed.rainxch.rikkaui.components.ui.radio.RadioButton
import zed.rainxch.rikkaui.components.ui.scaffold.Scaffold
import zed.rainxch.rikkaui.components.ui.scrollarea.ScrollArea
import zed.rainxch.rikkaui.components.ui.select.Select
import zed.rainxch.rikkaui.components.ui.select.SelectOption
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.slider.Slider
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toast.LocalToastHostState
import zed.rainxch.rikkaui.components.ui.toggle.Toggle
import zed.rainxch.rikkaui.components.ui.topappbar.TopAppBar
import zed.rainxch.rikkaui.foundation.RikkaTheme

private val currencyOptions = listOf(
    SelectOption("USD", "USD"),
    SelectOption("EUR", "EUR"),
    SelectOption("GBP", "GBP"),
    SelectOption("JPY", "JPY"),
    SelectOption("CAD", "CAD"),
)

private val languageOptions = listOf(
    SelectOption("en", "English"),
    SelectOption("es", "Spanish"),
    SelectOption("fr", "French"),
    SelectOption("de", "German"),
    SelectOption("ja", "Japanese"),
)

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val toastHostState = LocalToastHostState.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Settings",
                navigationIcon = {
                    IconButton(
                        icon = RikkaIcons.ArrowLeft,
                        contentDescription = "Back",
                        onClick = onNavigateBack,
                    )
                },
            )
        },
    ) { paddingValues ->
        ScrollArea(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(RikkaTheme.spacing.lg),
                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.lg),
            ) {
                // Profile Section
                Card {
                    CardHeader {
                        Text("Profile", variant = TextVariant.H4)
                    }
                    CardContent {
                        Column(
                            modifier = Modifier.padding(RikkaTheme.spacing.md),
                            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
                        ) {
                            Label(text = "Display Name")
                            Input(
                                value = state.displayName,
                                onValueChange = { onAction(SettingsAction.SetDisplayName(it)) },
                                placeholder = "Enter your name",
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }

                // Preferences Section
                Card {
                    CardHeader {
                        Text("Preferences", variant = TextVariant.H4)
                    }
                    CardContent {
                        Column(
                            modifier = Modifier.padding(RikkaTheme.spacing.md),
                            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.lg),
                        ) {
                            // Toggles
                            SettingsToggleRow(
                                label = "Dark Mode",
                                description = "Use dark color scheme",
                                checked = state.darkMode,
                                onCheckedChange = { onAction(SettingsAction.SetDarkMode(it)) },
                            )

                            Separator()

                            SettingsToggleRow(
                                label = "Notifications",
                                description = "Receive push notifications",
                                checked = state.notifications,
                                onCheckedChange = { onAction(SettingsAction.SetNotifications(it)) },
                            )

                            Separator()

                            SettingsToggleRow(
                                label = "Biometric Auth",
                                description = "Use fingerprint or face ID",
                                checked = state.biometrics,
                                onCheckedChange = { onAction(SettingsAction.SetBiometrics(it)) },
                            )

                            Separator()

                            // Currency Select
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column {
                                    Text("Currency", variant = TextVariant.P)
                                    Text("Display currency", variant = TextVariant.Muted)
                                }
                                Select(
                                    selectedValue = state.currency,
                                    onValueChange = { onAction(SettingsAction.SetCurrency(it)) },
                                    options = currencyOptions,
                                    modifier = Modifier.width(140.dp),
                                )
                            }

                            Separator()

                            // Language Select
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column {
                                    Text("Language", variant = TextVariant.P)
                                    Text("Interface language", variant = TextVariant.Muted)
                                }
                                Select(
                                    selectedValue = state.language,
                                    onValueChange = { onAction(SettingsAction.SetLanguage(it)) },
                                    options = languageOptions,
                                    modifier = Modifier.width(140.dp),
                                )
                            }
                        }
                    }
                }

                // Chart & Display Section
                Card {
                    CardHeader {
                        Text("Chart & Display", variant = TextVariant.H4)
                    }
                    CardContent {
                        Column(
                            modifier = Modifier.padding(RikkaTheme.spacing.md),
                            verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.lg),
                        ) {
                            // Risk Tolerance Slider
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text("Risk Tolerance", variant = TextVariant.P)
                                    Text(
                                        text = when {
                                            state.riskTolerance < 0.33f -> "Conservative"
                                            state.riskTolerance < 0.66f -> "Moderate"
                                            else -> "Aggressive"
                                        },
                                        variant = TextVariant.Muted,
                                    )
                                }
                                Spacer(Modifier.height(RikkaTheme.spacing.sm))
                                Slider(
                                    value = state.riskTolerance,
                                    onValueChange = { onAction(SettingsAction.SetRiskTolerance(it)) },
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }

                            Separator()

                            // Chart Style Radio Group
                            Column(
                                verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                            ) {
                                Text("Chart Style", variant = TextVariant.P)
                                listOf("Line", "Candle", "Bar").forEach { style ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        RadioButton(
                                            selected = state.chartStyle == style,
                                            onClick = { onAction(SettingsAction.SetChartStyle(style)) },
                                        )
                                        Spacer(Modifier.width(RikkaTheme.spacing.sm))
                                        Text(style, variant = TextVariant.P)
                                    }
                                }
                            }

                            Separator()

                            // Checkbox
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = state.showPortfolioInNotification,
                                    onCheckedChange = { onAction(SettingsAction.SetShowPortfolioInNotification(it)) },
                                )
                                Spacer(Modifier.width(RikkaTheme.spacing.sm))
                                Text(
                                    "Show portfolio in notification bar",
                                    variant = TextVariant.P,
                                )
                            }
                        }
                    }
                }

                // Save & Danger Zone
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Button(
                        text = "Save Changes",
                        onClick = {
                            onAction(SettingsAction.SaveSettings)
                            scope.launch {
                                toastHostState.show("Settings saved successfully")
                            }
                        },
                        variant = ButtonVariant.Default,
                    )

                    Button(
                        text = "Delete Account",
                        onClick = { onAction(SettingsAction.SetShowDeleteDialog(true)) },
                        variant = ButtonVariant.Destructive,
                    )
                }

                Spacer(Modifier.height(RikkaTheme.spacing.xl))
            }
        }
    }

    // Delete Account Alert Dialog
    if (state.showDeleteDialog) {
        AlertDialog(
            open = true,
            onDismiss = { onAction(SettingsAction.SetShowDeleteDialog(false)) },
            onConfirm = {
                onAction(SettingsAction.ConfirmDeleteAccount)
                scope.launch {
                    toastHostState.show("Account deletion requested")
                }
            },
        ) {
            AlertDialogHeader(
                title = "Delete Account",
                description = "Are you sure you want to delete your account? This action cannot be undone and all your portfolio data will be permanently lost.",
            )
            AlertDialogFooter {
                AlertDialogAction(
                    text = "Cancel",
                    onClick = { onAction(SettingsAction.SetShowDeleteDialog(false)) },
                )
                AlertDialogAction(
                    text = "Delete",
                    onClick = {
                        onAction(SettingsAction.ConfirmDeleteAccount)
                        scope.launch {
                            toastHostState.show("Account deletion requested")
                        }
                    },
                    variant = AlertDialogActionVariant.Destructive,
                )
            }
        }
    }
}

@Composable
private fun SettingsToggleRow(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, variant = TextVariant.P)
            Text(description, variant = TextVariant.Muted)
        }
        Toggle(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}
