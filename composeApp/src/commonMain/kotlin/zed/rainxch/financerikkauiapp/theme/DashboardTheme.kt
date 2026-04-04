package zed.rainxch.financerikkauiapp.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import zed.rainxch.rikkaui.foundation.RikkaColors
import zed.rainxch.rikkaui.foundation.RikkaStylePreset
import zed.rainxch.rikkaui.foundation.RikkaTheme

// Custom dark palette — emerald/navy, NOT Material's blue/purple
val DashboardColors = RikkaColors(
    background = Color(0xFF0A0F1A),        // Deep navy
    onBackground = Color(0xFFE2E8F0),      // Cool gray text
    surface = Color(0xFF111827),            // Slightly lighter navy
    onSurface = Color(0xFFE2E8F0),
    primary = Color(0xFF10B981),           // Emerald green
    onPrimary = Color(0xFF022C22),
    secondary = Color(0xFF1E293B),         // Slate
    onSecondary = Color(0xFFE2E8F0),
    muted = Color(0xFF334155),             // Slate muted
    onMuted = Color(0xFF94A3B8),
    destructive = Color(0xFFEF4444),       // Red for losses
    onDestructive = Color(0xFFFFFFFF),
    warning = Color(0xFFF59E0B),           // Amber
    onWarning = Color(0xFF451A03),
    success = Color(0xFF10B981),           // Emerald
    onSuccess = Color(0xFF022C22),
    border = Color(0xFF1E293B),
    ring = Color(0xFF10B981),
    inverseSurface = Color(0xFFE2E8F0),
    onInverseSurface = Color(0xFF0A0F1A),
    primaryTinted = Color(0xFF064E3B),
    onPrimaryTinted = Color(0xFF6EE7B7),
    destructiveTinted = Color(0xFF450A0A),
    onDestructiveTinted = Color(0xFFFCA5A5),
    primaryHover = Color(0xFF059669),
    primaryPressed = Color(0xFF047857),
    destructiveHover = Color(0xFFDC2626),
    destructivePressed = Color(0xFFB91C1C),
    secondaryHover = Color(0xFF243147),
    secondaryPressed = Color(0xFF2A3A55),
)

// Extra semantic colors for the finance dashboard
object DashboardSemanticColors {
    val profit = Color(0xFF10B981)
    val loss = Color(0xFFEF4444)
    val neutral = Color(0xFF94A3B8)
    val dividend = Color(0xFF6366F1)
    val chartGrid = Color(0xFF1E293B)
    val sidebarBg = Color(0xFF0D1321)
    val sidebarSelected = Color(0xFF10B981).copy(alpha = 0.12f)
}

@Composable
fun FinanceDashboardTheme(content: @Composable () -> Unit) {
    RikkaTheme(
        colors = DashboardColors,
        preset = RikkaStylePreset.Nova,
        content = content,
    )
}
