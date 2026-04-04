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
import zed.rainxch.rikkaui.components.ui.avatar.Avatar
import zed.rainxch.rikkaui.components.ui.avatar.AvatarSize
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.button.Button
import zed.rainxch.rikkaui.components.ui.button.ButtonVariant
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.sheet.Sheet
import zed.rainxch.rikkaui.components.ui.sheet.SheetContent
import zed.rainxch.rikkaui.components.ui.sheet.SheetFooter
import zed.rainxch.rikkaui.components.ui.sheet.SheetHeader
import zed.rainxch.rikkaui.components.ui.sheet.SheetSide
import zed.rainxch.rikkaui.components.ui.table.Table
import zed.rainxch.rikkaui.components.ui.table.TableCell
import zed.rainxch.rikkaui.components.ui.table.TableRow
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.data.FakeData
import zed.rainxch.financerikkauiapp.data.Transaction
import zed.rainxch.financerikkauiapp.data.TransactionType

@Composable
fun TransactionSheet(
    transaction: Transaction,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onCancelOrder: () -> Unit,
) {
    val stock = FakeData.stocks.find { it.ticker == transaction.ticker }

    Sheet(
        open = isOpen,
        onDismiss = onDismiss,
        side = SheetSide.Right,
    ) {
        SheetHeader(
            title = stock?.name ?: transaction.ticker,
            description = transaction.ticker,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Avatar(
                fallback = FakeData.stockInitials(transaction.ticker),
                size = AvatarSize.Lg,
            )
            Spacer(Modifier.width(RikkaTheme.spacing.md))
            Column {
                Text(
                    text = stock?.name ?: transaction.ticker,
                    variant = TextVariant.Large,
                )
                Text(
                    text = transaction.ticker,
                    variant = TextVariant.Muted,
                )
            }
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))
        Separator()
        Spacer(Modifier.height(RikkaTheme.spacing.md))

        SheetContent {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Order Status", variant = TextVariant.Muted)
                Badge(
                    text = transaction.status,
                    variant = if (transaction.status == "Filled") BadgeVariant.Default else BadgeVariant.Outline,
                )
            }

            Spacer(Modifier.height(RikkaTheme.spacing.lg))

            Table(modifier = Modifier.fillMaxWidth()) {
                DetailRow("Type", transaction.type.name)
                DetailRow(
                    "Quantity",
                    if (transaction.quantity > 0) "${transaction.quantity} shares" else "\u2014",
                )
                DetailRow("Price", "$${transaction.price}")
                DetailRow("Fees", "$${formatFees(transaction.fees)}")
                DetailRow("Total", "$${formatFees(transaction.total + transaction.fees)}")
                DetailRow("Date", transaction.date)
            }
        }

        SheetFooter {
            Button(
                text = "Close",
                onClick = onDismiss,
                variant = ButtonVariant.Outline,
            )
            if (transaction.status != "Filled") {
                Button(
                    text = "Cancel Order",
                    onClick = onCancelOrder,
                    variant = ButtonVariant.Destructive,
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    TableRow {
        TableCell(modifier = Modifier.weight(1f)) {
            Text(label, variant = TextVariant.Muted)
        }
        TableCell(modifier = Modifier.weight(1f)) {
            Text(value, variant = TextVariant.P)
        }
    }
}

private fun formatFees(value: Double): String {
    val intPart = value.toLong()
    val decPart = ((value - intPart).let { if (it < 0) -it else it } * 100).toLong()
    return "$intPart.${decPart.toString().padStart(2, '0')}"
}
