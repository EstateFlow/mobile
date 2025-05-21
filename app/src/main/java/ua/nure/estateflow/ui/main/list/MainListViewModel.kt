package ua.nure.estateflow.ui.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
    private val propertyDataSource: PropertyDataSource,
) : ViewModel() {
    init {
        viewModelScope.launch {
            propertyDataSource.get().collect { list ->
                _state.update {
                    it.copy(
                        properties = list
                    )
                }
            }
        }

        viewModelScope.launch {
            propertyDataSource.load().collect {
                when(it) {
                    is DataSourceResponse.Error<*> -> {
                        it.message?.let {
                            _event.emit(MainList.Event.OnMessage(message = it))
                        }
                    }
                    DataSourceResponse.InProgress -> {}
                    is DataSourceResponse.Success<*> -> {}
                }
            }
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