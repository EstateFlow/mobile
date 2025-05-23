package ua.nure.estateflow.ui.main.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import javax.inject.Inject

@HiltViewModel
class MainListViewModel @Inject constructor(
    private val propertyDataSource: PropertyDataSource,
) : ViewModel() {
    private val TAG by lazy { MainListViewModel::class.simpleName }
    val search = MutableStateFlow("")
    init {
        viewModelScope.launch {
            combine(
                search,
                propertyDataSource.get()
            ) { search, list ->
                _state.update {
                    it.copy(
                        properties = list.filter { prop ->
                            Log.d(TAG, "list: ${prop.propertyEntity.id} -> ${prop.propertyEntity.title}")
                            if(search.isNotEmpty()) {
                                prop.propertyEntity.title.contains(search, ignoreCase = true)
                                        || prop.propertyEntity.address.contains(search, ignoreCase = true)
                                        || prop.propertyEntity.description.contains(search, ignoreCase = true)
                            } else true
                        }
                    )
                }
            }.collect {}
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
            is MainList.Action.OnNavigate -> {
                _event.emit(MainList.Event.OnNavigate(destination = action.destination))
            }
            is MainList.Action.OnSearch -> {
                search.value = action.search
            }
        }
    }
}