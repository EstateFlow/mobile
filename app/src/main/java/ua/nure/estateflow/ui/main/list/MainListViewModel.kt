package ua.nure.estateflow.ui.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import ua.nure.estateflow.ui.signin.SignIn
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
    private val propertyDataSource: PropertyDataSource,
) : ViewModel() {
    init {
        viewModelScope.launch {
            propertyDataSource.getProperties().collect {}
        }
    }

    private val _event = MutableSharedFlow<MainList.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(MainList.State())
    val state = _state.asStateFlow()

    fun onAction(action: MainList.Action) = viewModelScope.launch {
        when(action) {
            is MainList.Action.OnNavigate -> TODO()
        }
    }
}