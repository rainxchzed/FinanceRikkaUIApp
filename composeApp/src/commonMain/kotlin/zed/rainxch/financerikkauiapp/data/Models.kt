package zed.rainxch.financerikkauiapp.data

enum class TransactionType {
    Buy, Sell, Dividend
}

data class Stock(
    val ticker: String,
    val name: String,
    val price: Double,
    val changePercent: Double,
)

data class Transaction(
    val ticker: String,
    val type: TransactionType,
    val quantity: Int,
    val price: Double,
    val date: String,
    val status: String = "Filled",
) {
    val total: Double get() = if (quantity > 0) quantity * price else price
    val fees: Double get() = if (type == TransactionType.Dividend) 0.0 else total * 0.001
}

data class Allocation(
    val category: String,
    val percent: Int,
    val colorIndex: Int,
)

data class PortfolioStats(
    val totalBalance: Double = 124_832.50,
    val monthlyChange: Double = 12.4,
    val monthlyChangeAmount: Double = 2_340.00,
    val topGainer: String = "AAPL",
    val topGainerChange: Double = 8.2,
)
