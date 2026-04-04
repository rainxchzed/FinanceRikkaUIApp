package zed.rainxch.financerikkauiapp.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsState(
    val darkMode: Boolean = true,
    val notifications: Boolean = true,
    val biometrics: Boolean = false,
    val currency: String = "USD",
    val language: String = "en",
    val displayName: String = "John Doe",
    val riskTolerance: Float = 0.5f,
    val chartStyle: String = "Line",
    val showPortfolioInNotification: Boolean = true,
    val showDeleteDialog: Boolean = false,
)

sealed interface SettingsAction {
    data class SetDarkMode(val enabled: Boolean) : SettingsAction
    data class SetNotifications(val enabled: Boolean) : SettingsAction
    data class SetBiometrics(val enabled: Boolean) : SettingsAction
    data class SetCurrency(val currency: String) : SettingsAction
    data class SetLanguage(val language: String) : SettingsAction
    data class SetDisplayName(val name: String) : SettingsAction
    data class SetRiskTolerance(val value: Float) : SettingsAction
    data class SetChartStyle(val style: String) : SettingsAction
    data class SetShowPortfolioInNotification(val enabled: Boolean) : SettingsAction
    data class SetShowDeleteDialog(val show: Boolean) : SettingsAction
    data object SaveSettings : SettingsAction
    data object ConfirmDeleteAccount : SettingsAction
}

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.SetDarkMode -> _state.update { it.copy(darkMode = action.enabled) }
            is SettingsAction.SetNotifications -> _state.update { it.copy(notifications = action.enabled) }
            is SettingsAction.SetBiometrics -> _state.update { it.copy(biometrics = action.enabled) }
            is SettingsAction.SetCurrency -> _state.update { it.copy(currency = action.currency) }
            is SettingsAction.SetLanguage -> _state.update { it.copy(language = action.language) }
            is SettingsAction.SetDisplayName -> _state.update { it.copy(displayName = action.name) }
            is SettingsAction.SetRiskTolerance -> _state.update { it.copy(riskTolerance = action.value) }
            is SettingsAction.SetChartStyle -> _state.update { it.copy(chartStyle = action.style) }
            is SettingsAction.SetShowPortfolioInNotification -> _state.update { it.copy(showPortfolioInNotification = action.enabled) }
            is SettingsAction.SetShowDeleteDialog -> _state.update { it.copy(showDeleteDialog = action.show) }
            SettingsAction.SaveSettings -> {
                // In a real app, persist to DataStore/DB here
            }
            SettingsAction.ConfirmDeleteAccount -> {
                _state.update { it.copy(showDeleteDialog = false) }
                // In a real app, trigger account deletion
            }
        }
    }
}
