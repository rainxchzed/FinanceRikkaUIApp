package zed.rainxch.financerikkauiapp.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.tabs.Tab
import zed.rainxch.rikkaui.components.ui.tabs.TabContent
import zed.rainxch.rikkaui.components.ui.tabs.TabList
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.financerikkauiapp.data.FakeData
import zed.rainxch.financerikkauiapp.theme.DashboardSemanticColors

private val chartPeriods = listOf("1D", "1W", "1M", "3M", "1Y")

@Composable
fun PortfolioChartSection(
    isCompact: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var selectedPeriod by remember { mutableStateOf(2) } // Default to 1M

    Card(modifier = modifier.fillMaxWidth()) {
        CardHeader {
            if (isCompact) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
                ) {
                    Text(
                        text = "Portfolio Performance",
                        variant = TextVariant.H4,
                    )
                    TabList {
                        chartPeriods.forEachIndexed { index, period ->
                            Tab(
                                selected = selectedPeriod == index,
                                onClick = { selectedPeriod = index },
                                text = period,
                            )
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Portfolio Performance",
                        variant = TextVariant.H4,
                        modifier = Modifier.weight(1f),
                    )
                    TabList {
                        chartPeriods.forEachIndexed { index, period ->
                            Tab(
                                selected = selectedPeriod == index,
                                onClick = { selectedPeriod = index },
                                text = period,
                            )
                        }
                    }
                }
            }
        }
        CardContent {
            TabContent(selectedIndex = selectedPeriod) {
                val data = FakeData.chartDataForPeriod(selectedPeriod)

                // Y-axis labels + chart
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Chart with Y-axis hints
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Y-axis labels
                        val maxVal = data.max()
                        val minVal = data.min()
                        Column(
                            modifier = Modifier.height(200.dp).padding(end = RikkaTheme.spacing.sm),
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                        ) {
                            Text("$${maxVal.toInt()}", variant = TextVariant.Small, color = RikkaTheme.colors.onMuted)
                            Text("$${((maxVal + minVal) / 2).toInt()}", variant = TextVariant.Small, color = RikkaTheme.colors.onMuted)
                            Text("$${minVal.toInt()}", variant = TextVariant.Small, color = RikkaTheme.colors.onMuted)
                        }

                        // Chart canvas
                        PortfolioChart(
                            data = data,
                            modifier = Modifier.weight(1f).height(200.dp),
                        )
                    }

                    // X-axis labels
                    Spacer(Modifier.height(RikkaTheme.spacing.xs))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 40.dp),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    ) {
                        val labels = when (selectedPeriod) {
                            0 -> listOf("9:30", "11:00", "12:30", "2:00", "4:00")
                            1 -> listOf("Mon", "Tue", "Wed", "Thu", "Fri")
                            2 -> listOf("Week 1", "Week 2", "Week 3", "Week 4")
                            3 -> listOf("Apr", "May", "Jun")
                            4 -> listOf("Jul", "Oct", "Jan", "Apr", "Jun")
                            else -> emptyList()
                        }
                        labels.forEach { label ->
                            Text(label, variant = TextVariant.Small, color = RikkaTheme.colors.onMuted)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PortfolioChart(data: List<Float>, modifier: Modifier = Modifier) {
    val lineColor = RikkaTheme.colors.primary
    val gridColor = DashboardSemanticColors.chartGrid
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            RikkaTheme.colors.primary.copy(alpha = 0.3f),
            Color.Transparent,
        ),
    )

    Canvas(modifier = modifier) {
        val padding = 4.dp.toPx()
        val chartWidth = size.width - padding * 2
        val chartHeight = size.height - padding * 2
        val spacing = chartWidth / (data.size - 1)
        val maxVal = data.max()
        val minVal = data.min()
        val range = if (maxVal - minVal > 0f) maxVal - minVal else 1f

        // Draw horizontal grid lines
        for (i in 0..4) {
            val y = padding + chartHeight * i / 4f
            drawLine(
                color = gridColor,
                start = Offset(padding, y),
                end = Offset(size.width - padding, y),
                strokeWidth = 1.dp.toPx(),
            )
        }

        val path = Path()
        val fillPath = Path()

        data.forEachIndexed { i, value ->
            val x = padding + i * spacing
            val y = padding + chartHeight - ((value - minVal) / range * chartHeight)
            if (i == 0) {
                path.moveTo(x, y)
                fillPath.moveTo(x, padding + chartHeight)
                fillPath.lineTo(x, y)
            } else {
                path.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }

        fillPath.lineTo(padding + chartWidth, padding + chartHeight)
        fillPath.close()

        drawPath(fillPath, gradientBrush)
        drawPath(path, lineColor, style = Stroke(width = 2.dp.toPx()))

        // Draw the last data point as a dot
        val lastX = padding + (data.size - 1) * spacing
        val lastY = padding + chartHeight - ((data.last() - minVal) / range * chartHeight)
        drawCircle(
            color = lineColor,
            radius = 4.dp.toPx(),
            center = Offset(lastX, lastY),
        )
        drawCircle(
            color = lineColor.copy(alpha = 0.3f),
            radius = 8.dp.toPx(),
            center = Offset(lastX, lastY),
        )
    }
}
