package zed.rainxch.financerikkauiapp.data

object FakeData {

    val stocks = listOf(
        Stock("AAPL", "Apple Inc.", 198.20, +2.4),
        Stock("MSFT", "Microsoft Corp.", 442.10, -0.8),
        Stock("NVDA", "NVIDIA Corp.", 131.50, +5.1),
        Stock("AMZN", "Amazon.com", 189.30, +1.2),
        Stock("GOOG", "Alphabet Inc.", 178.90, +0.6),
        Stock("TSLA", "Tesla Inc.", 248.00, -1.9),
        Stock("META", "Meta Platforms", 512.40, +3.2),
        Stock("JPM", "JPMorgan Chase", 198.70, +0.4),
        Stock("V", "Visa Inc.", 284.60, +1.1),
        Stock("WMT", "Walmart Inc.", 165.30, -0.3),
    )

    val watchlist = stocks.take(4)

    val transactions = listOf(
        Transaction("AAPL", TransactionType.Buy, 50, 188.40, "2025-06-12"),
        Transaction("MSFT", TransactionType.Sell, 20, 405.00, "2025-06-11"),
        Transaction("TSLA", TransactionType.Buy, 10, 248.00, "2025-06-10"),
        Transaction("GOOG", TransactionType.Dividend, 0, 142.00, "2025-06-09"),
        Transaction("NVDA", TransactionType.Buy, 30, 128.50, "2025-06-08"),
        Transaction("META", TransactionType.Sell, 15, 505.20, "2025-06-07"),
        Transaction("AMZN", TransactionType.Buy, 25, 185.00, "2025-06-06"),
        Transaction("JPM", TransactionType.Dividend, 0, 98.50, "2025-06-05"),
        Transaction("V", TransactionType.Buy, 20, 280.10, "2025-06-04"),
        Transaction("AAPL", TransactionType.Sell, 10, 196.80, "2025-06-03"),
        Transaction("MSFT", TransactionType.Buy, 5, 438.20, "2025-06-02"),
        Transaction("TSLA", TransactionType.Sell, 5, 252.30, "2025-06-01"),
        Transaction("WMT", TransactionType.Buy, 40, 162.50, "2025-05-31"),
        Transaction("GOOG", TransactionType.Buy, 15, 175.40, "2025-05-30"),
        Transaction("NVDA", TransactionType.Sell, 10, 135.60, "2025-05-29"),
        Transaction("META", TransactionType.Buy, 8, 498.70, "2025-05-28"),
        Transaction("AMZN", TransactionType.Dividend, 0, 56.20, "2025-05-27"),
        Transaction("JPM", TransactionType.Buy, 12, 195.80, "2025-05-26"),
        Transaction("V", TransactionType.Sell, 8, 282.40, "2025-05-25"),
        Transaction("AAPL", TransactionType.Buy, 20, 192.10, "2025-05-24"),
    )

    val allocation = listOf(
        Allocation("Stocks", 72, 0),
        Allocation("Bonds", 15, 1),
        Allocation("Cash", 8, 2),
        Allocation("Crypto", 5, 3),
    )

    val portfolioStats = PortfolioStats()

    // 30 data points for portfolio chart (simulating 30-day performance)
    val chartData1D = listOf(
        124.0f, 124.5f, 123.8f, 124.2f, 125.1f, 124.8f, 125.5f, 126.0f,
        125.7f, 126.3f, 126.8f, 127.1f, 126.5f, 127.0f, 127.5f, 127.2f,
        127.8f, 128.2f, 127.9f, 128.5f, 129.0f, 128.7f, 129.3f, 129.8f,
        130.1f, 129.5f, 130.0f, 130.5f, 130.2f, 130.8f,
    )

    val chartData1W = listOf(
        118.0f, 119.2f, 118.5f, 120.1f, 121.3f, 120.8f, 122.0f, 123.5f,
        122.8f, 124.0f, 123.2f, 124.8f, 125.5f, 124.9f, 126.0f, 126.8f,
        125.5f, 127.0f, 128.2f, 127.5f, 128.8f, 129.5f, 128.9f, 130.0f,
        130.8f, 129.5f, 131.0f, 131.8f, 130.5f, 132.0f,
    )

    val chartData1M = listOf(
        110.0f, 112.5f, 111.0f, 114.2f, 113.5f, 116.0f, 115.2f, 118.0f,
        117.0f, 119.5f, 118.8f, 121.0f, 120.2f, 122.5f, 121.8f, 124.0f,
        123.0f, 125.5f, 124.5f, 127.0f, 126.0f, 128.5f, 127.5f, 130.0f,
        129.0f, 131.5f, 130.5f, 133.0f, 132.0f, 134.5f,
    )

    val chartData3M = listOf(
        95.0f, 98.2f, 96.5f, 100.1f, 99.0f, 103.5f, 101.8f, 106.0f,
        104.2f, 108.5f, 106.8f, 111.0f, 109.0f, 113.5f, 111.5f, 116.0f,
        114.0f, 118.5f, 116.5f, 121.0f, 119.0f, 123.5f, 121.5f, 126.0f,
        124.0f, 128.5f, 126.5f, 131.0f, 129.0f, 134.5f,
    )

    val chartData1Y = listOf(
        72.0f, 75.5f, 78.0f, 76.5f, 80.0f, 83.5f, 81.0f, 85.0f,
        88.5f, 86.0f, 90.0f, 93.5f, 91.0f, 95.0f, 98.5f, 96.0f,
        100.0f, 103.5f, 101.0f, 105.0f, 108.5f, 106.0f, 110.0f, 115.0f,
        112.0f, 118.0f, 122.0f, 119.0f, 125.0f, 130.0f,
    )

    fun chartDataForPeriod(index: Int): List<Float> = when (index) {
        0 -> chartData1D
        1 -> chartData1W
        2 -> chartData1M
        3 -> chartData3M
        4 -> chartData1Y
        else -> chartData1M
    }

    fun stockInitials(ticker: String): String {
        return when (ticker) {
            "AAPL" -> "AA"
            "MSFT" -> "MS"
            "NVDA" -> "NV"
            "AMZN" -> "AZ"
            "GOOG" -> "GO"
            "TSLA" -> "TS"
            "META" -> "ME"
            "JPM" -> "JP"
            "V" -> "VI"
            "WMT" -> "WM"
            else -> ticker.take(2)
        }
    }
}
