package zed.rainxch.financerikkauiapp.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.table.Table
import zed.rainxch.rikkaui.components.ui.table.TableCell
import zed.rainxch.rikkaui.components.ui.table.TableHeader
import zed.rainxch.rikkaui.components.ui.table.TableRow
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.data.FakeData
import zed.rainxch.financerikkauiapp.data.Transaction
import zed.rainxch.financerikkauiapp.data.TransactionType

// Column weight constants for consistent sizing
private const val COL_ASSET = 2.5f
private const val COL_TYPE = 1.5f
private const val COL_QTY = 1.5f
private const val COL_AMOUNT = 1.5f
private const val COL_DATE = 1.5f

@Composable
fun TransactionsTable(
    onTransactionClick: (Transaction) -> Unit,
    isCompact: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        CardHeader {
            Text(
                text = "Recent Transactions",
                variant = TextVariant.H4,
            )
        }
        CardContent {
            Table(modifier = Modifier.fillMaxWidth()) {
                TableHeader {
                    TableCell(modifier = Modifier.weight(COL_ASSET)) {
                        Text("Asset", variant = TextVariant.Muted)
                    }
                    TableCell(modifier = Modifier.weight(COL_TYPE)) {
                        Text("Type", variant = TextVariant.Muted)
                    }
                    if (!isCompact) {
                        TableCell(modifier = Modifier.weight(COL_QTY)) {
                            Text("Quantity", variant = TextVariant.Muted)
                        }
                    }
                    TableCell(modifier = Modifier.weight(COL_AMOUNT)) {
                        Text("Amount", variant = TextVariant.Muted)
                    }
                    if (!isCompact) {
                        TableCell(modifier = Modifier.weight(COL_DATE)) {
                            Text("Date", variant = TextVariant.Muted)
                        }
                    }
                }

                FakeData.transactions.take(if (isCompact) 5 else 8).forEach { transaction ->
                    TableRow(
                        onClick = { onTransactionClick(transaction) },
                    ) {
                        TableCell(modifier = Modifier.weight(COL_ASSET)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Avatar(
                                    fallback = FakeData.stockInitials(transaction.ticker),
                                    size = AvatarSize.Sm,
                                )
                                Spacer(Modifier.width(RikkaTheme.spacing.sm))
                                Column {
                                    Text(
                                        text = transaction.ticker,
                                        variant = TextVariant.P,
                                    )
                                    if (!isCompact) {
                                        val stock =
                                            FakeData.stocks.find { it.ticker == transaction.ticker }
                                        if (stock != null) {
                                            Text(
                                                text = stock.name,
                                                variant = TextVariant.Muted,
                                                maxLines = 1,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        TableCell(modifier = Modifier.weight(COL_TYPE)) {
                            val typeLabel = if (isCompact && transaction.type == TransactionType.Dividend) {
                                "Div"
                            } else {
                                transaction.type.name
                            }
                            Badge(
                                text = typeLabel,
                                variant = when (transaction.type) {
                                    TransactionType.Buy -> BadgeVariant.Default
                                    TransactionType.Sell -> BadgeVariant.Destructive
                                    TransactionType.Dividend -> BadgeVariant.Secondary
                                },
                            )
                        }
                        if (!isCompact) {
                            TableCell(modifier = Modifier.weight(COL_QTY)) {
                                Text(
                                    text = when (transaction.type) {
                                        TransactionType.Buy -> "+${transaction.quantity}"
                                        TransactionType.Sell -> "-${transaction.quantity}"
                                        TransactionType.Dividend -> "\u2014"
                                    },
                                    variant = TextVariant.P,
                                    maxLines = 1,
                                )
                            }
                        }
                        TableCell(modifier = Modifier.weight(COL_AMOUNT)) {
                            Text(
                                text = "$${formatAmount(transaction.total)}",
                                variant = TextVariant.P,
                                maxLines = 1,
                            )
                        }
                        if (!isCompact) {
                            TableCell(modifier = Modifier.weight(COL_DATE)) {
                                Text(
                                    text = transaction.date,
                                    variant = TextVariant.Muted,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatAmount(value: Double): String {
    val intPart = value.toLong()
    val decPart = ((value - intPart) * 100).toLong()
    val formatted = buildString {
        val s = intPart.toString()
        s.forEachIndexed { index, c ->
            if (index > 0 && (s.length - index) % 3 == 0) append(',')
            append(c)
        }
    }
    return "$formatted.${decPart.toString().padStart(2, '0')}"
}
