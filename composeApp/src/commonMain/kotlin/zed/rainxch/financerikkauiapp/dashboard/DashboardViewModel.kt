package zed.rainxch.financerikkauiapp.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zed.rainxch.financerikkauiapp.data.Transaction

data class DashboardState(
    val selectedTransaction: Transaction? = null,
    val selectedDateRange: String = "jun-2025",
)

sealed interface DashboardAction {
    data class SelectTransaction(val transaction: Transaction) : DashboardAction
    data object DismissTransaction : DashboardAction
    data object CancelOrder : DashboardAction
    data class SelectDateRange(val range: String) : DashboardAction
}

class DashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.SelectTransaction -> {
                _state.update { it.copy(selectedTransaction = action.transaction) }
            }

            DashboardAction.DismissTransaction -> {
                _state.update { it.copy(selectedTransaction = null) }
            }

            DashboardAction.CancelOrder -> {
                _state.update { it.copy(selectedTransaction = null) }
            }

            is DashboardAction.SelectDateRange -> {
                _state.update { it.copy(selectedDateRange = action.range) }
            }
        }
    }
}
