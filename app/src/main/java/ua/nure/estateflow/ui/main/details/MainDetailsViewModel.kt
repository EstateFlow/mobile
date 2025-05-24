package ua.nure.estateflow.ui.main.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import ua.nure.estateflow.ui.main.details.MainDetails.Event.*
import javax.inject.Inject

@HiltViewModel
class MainDetailsViewModel @Inject constructor(
    private val propertyDataSource: PropertyDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _event = MutableSharedFlow<MainDetails.Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow<MainDetails.State>(MainDetails.State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            propertyDataSource.getById(id = savedStateHandle.get<String>("id") ?: "").collect { prop ->
                _state.update {
                    it.copy(
                        property = prop
                    )
                }
            }
        }
    }

    fun onAction(action: MainDetails.Action) = viewModelScope.launch {
        when(action) {
            is MainDetails.Action.OnNavigate -> {
                _event.emit(OnNavigate(destination = action.destination))
            }

            MainDetails.Action.OnBack -> {
                _event.emit(OnBack)
            }
        }
    }
}