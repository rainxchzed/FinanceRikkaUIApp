package zed.rainxch.financerikkauiapp.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.IconButton
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.scaffold.Scaffold
import zed.rainxch.rikkaui.components.ui.scrollarea.ScrollArea
import zed.rainxch.rikkaui.components.ui.select.Select
import zed.rainxch.rikkaui.components.ui.select.SelectOption
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.components.ui.toast.LocalToastHostState
import zed.rainxch.rikkaui.components.ui.topappbar.TopAppBar
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.data.Transaction
import zed.rainxch.financerikkauiapp.navigation.Screen
import zed.rainxch.financerikkauiapp.theme.DashboardSemanticColors

private val dateOptions = listOf(
    SelectOption("jan-2025", "Jan 2025"),
    SelectOption("feb-2025", "Feb 2025"),
    SelectOption("mar-2025", "Mar 2025"),
    SelectOption("apr-2025", "Apr 2025"),
    SelectOption("may-2025", "May 2025"),
    SelectOption("jun-2025", "Jun 2025"),
)

@Composable
fun DashboardScreen(
    onNavigate: (Screen) -> Unit,
) {
    val toastHostState = LocalToastHostState.current
    val scope = rememberCoroutineScope()
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }
    var selectedDateRange by remember { mutableStateOf("jun-2025") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth > 800.dp
        val isCompact = maxWidth < 600.dp

        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar
            if (isWideScreen) {
                Sidebar(
                    currentScreen = Screen.Dashboard,
                    onNavigate = onNavigate,
                )
            }

            // Main Content
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = "Portfolio Overview",
                        navigationIcon = if (!isWideScreen) {
                            {
                                IconButton(
                                    icon = RikkaIcons.Menu,
                                    contentDescription = "Menu",
                                    onClick = { },
                                )
                            }
                        } else {
                            {}
                        },
                        actions = {
                            if (!isCompact) {
                                Select(
                                    selectedValue = selectedDateRange,
                                    onValueChange = { selectedDateRange = it },
                                    options = dateOptions,
                                )
                            }
                            IconButton(
                                icon = RikkaIcons.Mail,
                                contentDescription = "Notifications",
                                onClick = {
                                    scope.launch {
                                        toastHostState.show("No new notifications")
                                    }
                                },
                            )
                        },
                    )
                },
                modifier = Modifier.weight(1f),
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
                        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.xl),
                    ) {
                        // Stat Cards — 2x2 grid on compact, single row on wide
                        StatCards(isCompact = isCompact)

                        // Chart Section with Tabs
                        PortfolioChartSection(isCompact = isCompact)

                        // Transactions Table
                        TransactionsTable(
                            onTransactionClick = { transaction ->
                                selectedTransaction = transaction
                            },
                            isCompact = isCompact,
                        )

                        // Watchlist Grid
                        WatchlistGrid(isCompact = isCompact)
                    }
                }
            }
        }

        // Transaction Detail Sheet
        selectedTransaction?.let { transaction ->
            TransactionSheet(
                transaction = transaction,
                isOpen = true,
                onDismiss = { selectedTransaction = null },
                onCancelOrder = {
                    selectedTransaction = null
                    scope.launch {
                        toastHostState.show("Order cancelled")
                    }
                },
            )
        }
    }
}

@Composable
fun Sidebar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(220.dp)
            .fillMaxHeight()
            .background(DashboardSemanticColors.sidebarBg)
            .padding(vertical = RikkaTheme.spacing.lg, horizontal = RikkaTheme.spacing.md),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            // Logo / Brand
            Text(
                text = "FinanceApp",
                variant = TextVariant.H3,
                color = RikkaTheme.colors.primary,
                modifier = Modifier.padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.lg,
                ),
            )

            Separator(modifier = Modifier.padding(vertical = RikkaTheme.spacing.md))

            // Navigation Items
            SidebarNavItem(
                icon = RikkaIcons.Star,
                label = "Dashboard",
                isSelected = currentScreen == Screen.Dashboard,
                onClick = { onNavigate(Screen.Dashboard) },
            )
            SidebarNavItem(
                icon = RikkaIcons.Eye,
                label = "Portfolio",
                isSelected = false,
                onClick = { },
            )
            SidebarNavItem(
                icon = RikkaIcons.ArrowRight,
                label = "Activity",
                isSelected = false,
                onClick = { },
            )
            SidebarNavItem(
                icon = RikkaIcons.Copy,
                label = "Cards",
                isSelected = false,
                onClick = { },
            )
            SidebarNavItem(
                icon = RikkaIcons.Settings,
                label = "Settings",
                isSelected = currentScreen == Screen.Settings,
                onClick = { onNavigate(Screen.Settings) },
            )
        }

        // Bottom section: version badge
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Separator(modifier = Modifier.padding(vertical = RikkaTheme.spacing.md))
            Badge(
                text = "v0.3.0",
                variant = BadgeVariant.Outline,
            )
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Text(
                text = "RikkaUI",
                variant = TextVariant.Muted,
            )
        }
    }
}

@Composable
private fun SidebarNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RikkaTheme.shapes.sm)
            .background(
                if (isSelected) DashboardSemanticColors.sidebarSelected
                else Color.Transparent,
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = RikkaTheme.spacing.md,
                vertical = RikkaTheme.spacing.sm,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) RikkaTheme.colors.primary else RikkaTheme.colors.onMuted,
        )
        Spacer(Modifier.width(RikkaTheme.spacing.sm))
        Text(
            text = label,
            variant = TextVariant.P,
            color = if (isSelected) RikkaTheme.colors.primary else RikkaTheme.colors.onMuted,
        )
    }
}
