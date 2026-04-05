package zed.rainxch.financerikkauiapp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface FinanceAppGraph : NavKey {

    @Serializable
    data object Dashboard : FinanceAppGraph

    @Serializable
    data object Portfolio : FinanceAppGraph

    @Serializable
    data object Activity : FinanceAppGraph

    @Serializable
    data object Cards : FinanceAppGraph

    @Serializable
    data object Settings : FinanceAppGraph
}
