package zed.rainxch.financerikkauiapp.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.data.FakeData
import zed.rainxch.financerikkauiapp.data.Stock

@Composable
fun WatchlistGrid(
    isCompact: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val gap = RikkaTheme.spacing.md

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Watchlist",
            variant = TextVariant.H4,
        )
        Spacer(Modifier.height(gap))

        if (isCompact) {
            // 2x2 grid on compact screens
            val items = FakeData.watchlist
            Column(
                verticalArrangement = Arrangement.spacedBy(gap),
            ) {
                for (i in items.indices step 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gap),
                    ) {
                        WatchlistCard(
                            stock = items[i],
                            modifier = Modifier.weight(1f),
                        )
                        if (i + 1 < items.size) {
                            WatchlistCard(
                                stock = items[i + 1],
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap),
            ) {
                FakeData.watchlist.forEach { stock ->
                    WatchlistCard(
                        stock = stock,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun WatchlistCard(
    stock: Stock,
    modifier: Modifier = Modifier,
) {
    val isPositive = stock.changePercent >= 0

    Card(modifier = modifier) {
        // Card already applies padding(spacing.lg) internally
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stock.ticker,
                variant = TextVariant.Large,
            )
            Badge(
                text = "${if (isPositive) "+" else ""}${stock.changePercent}%",
                variant = if (isPositive) BadgeVariant.Default else BadgeVariant.Destructive,
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = "$${stock.price}",
            variant = TextVariant.H3,
        )

        Text(
            text = stock.name,
            variant = TextVariant.Muted,
        )
    }
}
