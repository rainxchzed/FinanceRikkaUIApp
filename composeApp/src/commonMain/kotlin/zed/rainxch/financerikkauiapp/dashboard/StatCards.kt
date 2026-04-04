package zed.rainxch.financerikkauiapp.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.progress.Progress
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.data.FakeData
import zed.rainxch.financerikkauiapp.theme.DashboardSemanticColors

@Composable
fun StatCards(
    isCompact: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val stats = FakeData.portfolioStats
    val gap = RikkaTheme.spacing.md

    if (isCompact) {
        // 2x2 grid on narrow screens
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap),
            ) {
                StatCard(
                    label = "Total Balance",
                    value = "$${formatNumber(stats.totalBalance)}",
                    subValue = "+$${formatNumber(stats.monthlyChangeAmount)}",
                    isPositive = true,
                    modifier = Modifier.weight(1f),
                )
                StatCard(
                    label = "Monthly Change",
                    value = "+${stats.monthlyChange}%",
                    badgeText = "Trending",
                    isPositive = true,
                    modifier = Modifier.weight(1f),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap),
            ) {
                StatCard(
                    label = "Top Gainer",
                    value = stats.topGainer,
                    subValue = "+${stats.topGainerChange}%",
                    isPositive = true,
                    modifier = Modifier.weight(1f),
                )
                AllocationCard(
                    modifier = Modifier.weight(1f),
                )
            }
        }
    } else {
        // Single row on wide screens
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(gap),
        ) {
            StatCard(
                label = "Total Balance",
                value = "$${formatNumber(stats.totalBalance)}",
                subValue = "+$${formatNumber(stats.monthlyChangeAmount)}",
                isPositive = true,
                modifier = Modifier.weight(1f),
            )
            StatCard(
                label = "Monthly Change",
                value = "+${stats.monthlyChange}%",
                badgeText = "Trending",
                isPositive = true,
                modifier = Modifier.weight(1f),
            )
            StatCard(
                label = "Top Gainer",
                value = stats.topGainer,
                subValue = "+${stats.topGainerChange}%",
                isPositive = true,
                modifier = Modifier.weight(1f),
            )
            AllocationCard(
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    subValue: String? = null,
    badgeText: String? = null,
    isPositive: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Text(text = label, variant = TextVariant.Muted, maxLines = 1)
        Text(text = value, variant = TextVariant.H3, maxLines = 1)
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (subValue != null) {
                Icon(
                    imageVector = if (isPositive) RikkaIcons.ArrowUp else RikkaIcons.ArrowDown,
                    contentDescription = null,
                    tint = if (isPositive) DashboardSemanticColors.profit else DashboardSemanticColors.loss,
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = subValue,
                    variant = TextVariant.Small,
                    color = if (isPositive) DashboardSemanticColors.profit else DashboardSemanticColors.loss,
                    maxLines = 1,
                )
            }
            if (badgeText != null) {
                Badge(
                    text = badgeText,
                    variant = if (isPositive) BadgeVariant.Default else BadgeVariant.Destructive,
                )
            }
        }
    }
}

@Composable
private fun AllocationCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(text = "Allocation", variant = TextVariant.Muted, maxLines = 1)
        Text(text = "72% Stocks", variant = TextVariant.H3, maxLines = 1)
        Progress(
            progress = 0.72f,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

internal fun formatNumber(value: Double): String {
    val intPart = value.toLong()
    val decPart = ((value - intPart) * 100).toLong()
    val formatted = buildString {
        val s = intPart.toString()
        s.forEachIndexed { index, c ->
            if (index > 0 && (s.length - index) % 3 == 0) append(',')
            append(c)
        }
    }
    return if (decPart > 0) "$formatted.${decPart.toString().padStart(2, '0')}" else formatted
}
