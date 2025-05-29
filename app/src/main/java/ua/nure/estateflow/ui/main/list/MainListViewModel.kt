package ua.nure.estateflow.ui.main.list

import android.R
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.estateflow.data.datasource.DataSourceResponse
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import ua.nure.estateflow.data.local.entity.Property
import ua.nure.estateflow.data.remote.property.dto.PropertyType
import ua.nure.estateflow.data.remote.property.dto.TransactionType
import ua.nure.estateflow.ui.main.list.MainList.Event.*
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainListViewModel @Inject constructor(
    private val propertyDataSource: PropertyDataSource,
    private val profileDataSource: ProfileDataSource,
) : ViewModel() {
    private val TAG by lazy { MainListViewModel::class.simpleName }

    val filter = MutableStateFlow<Filter>(Filter())

    init {
        viewModelScope.launch { 
            combine(
                filter,
                propertyDataSource.get()
            ) { filter, list ->
                _state.update { 
                    it.copy(
                        properties = if(
                            filter.search.isNotEmpty()
                            || filter.roomFrom != null
                            || filter.roomTo != null
                            || filter.priceFrom != null
                            || filter.priceTo != null
                            || filter.areaFrom != null
                            || filter.areaTo != null
                            || !filter.isForRent
                            || !filter.isForPurchase
                            || !filter.isHouse
                            || !filter.isApartment
                                ) {

                            list.filter { prop ->
                                val price = prop.propertyEntity.price.toIntOrNull()
                                val area = prop.propertyEntity.size.toFloatOrNull()

                                (if(filter.search.isNotEmpty()) {
                                    prop.propertyEntity.title.contains(filter.search, ignoreCase = true)
                                            || prop.propertyEntity.address.contains(filter.search, ignoreCase = true)
                                            || prop.propertyEntity.description.contains(filter.search, ignoreCase = true)
                                } else true)
                                        &&
                                        (if(filter.roomFrom != null && filter.roomTo !=null){
                                            prop.propertyEntity.rooms >= filter.roomFrom && prop.propertyEntity.rooms <= filter.roomTo
                                        } else { true })
                                        &&
                                        (if(filter.roomFrom != null) {
                                            prop.propertyEntity.rooms >= filter.roomFrom
                                        } else true)
                                        &&
                                        (if(filter.roomTo != null) {
                                            prop.propertyEntity.rooms <= filter.roomTo
                                        } else true)
                                        &&
                                        (if( price != null && filter.priceFrom != null && filter.priceTo != null) {
                                            price >= filter.priceFrom && price <= filter.priceTo
                                        } else true)
                                        &&
                                        ( if(price != null && filter.priceFrom != null) {
                                            price >= filter.priceFrom
                                        } else true)
                                        &&
                                        (if(price != null && filter.priceTo != null) {
                                            price <= filter.priceTo
                                        } else true)
                                        &&
                                        (if( area != null && filter.areaFrom != null && filter.areaTo != null) {
                                            area >= filter.areaFrom && area <= filter.areaTo
                                        } else true)
                                        &&
                                        (if(area != null && filter.areaFrom != null) {
                                            area >= filter.areaFrom
                                        } else true)
                                        &&
                                        (if( area != null && filter.areaTo != null) {
                                            area <= filter.areaTo
                                        } else true)
                                        &&
                                        (if(!filter.isForRent) {
                                            prop.propertyEntity.transactionType != TransactionType.rent
                                        } else true)
                                        &&
                                        (if(!filter.isForPurchase) {
                                            prop.propertyEntity.transactionType != TransactionType.sale
                                        } else true)
                                        &&
                                        (if(!filter.isHouse) {
                                            prop.propertyEntity.propertyType != PropertyType.house
                                        } else true)
                                        &&
                                        (if(!filter.isApartment) {
                                            prop.propertyEntity.propertyType != PropertyType.apartment
                                        } else true)
                            }
                        } else list
                    )
                }
            }.collect {  }
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

        viewModelScope.launch {
            profileDataSource.profileFlow.debounce { 200 }.collect {
                it?.let { profile ->
                    _state.update {
                        it.copy(
                            profile = profile
                        )
                    }

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
                _event.emit(OnNavigate(destination = action.destination))
            }
            is MainList.Action.OnSearch -> filter.value = filter.value.copy(search = action.search)
            is MainList.Action.OnRoomsFrom -> filter.value = filter.value.copy(roomFrom = action.rooms)
            is MainList.Action.OnRoomsTo -> filter.value = filter.value.copy(roomTo = action.rooms)
            is MainList.Action.OnApartment -> filter.value = filter.value.copy(isApartment = action.checked)
            is MainList.Action.OnAreaFrom -> filter.value = filter.value.copy(areaFrom = action.area)
            is MainList.Action.OnAreaTo -> filter.value = filter.value.copy(areaTo = action.area)
            is MainList.Action.OnForPurchase -> filter.value = filter.value.copy(isForPurchase = action.checked)
            is MainList.Action.OnForRent -> filter.value = filter.value.copy(isForRent = action.checked)
            is MainList.Action.OnHose -> filter.value = filter.value.copy(isHouse = action.checked)
            is MainList.Action.OnPriceFrom -> filter.value = filter.value.copy(priceFrom = action.price)
            is MainList.Action.OnPriceTo -> filter.value = filter.value.copy(priceTo = action.price)
        }
    }

    data class Filter(
        val search: String = "",
        val roomFrom: Int? = null,
        val roomTo: Int? = null,
        val priceFrom: Int? = null,
        val priceTo: Int? = null,
        val areaFrom: Int? = null,
        val areaTo: Int? = null,
        val isForRent: Boolean = true,
        val isForPurchase: Boolean = true,
        val isHouse: Boolean = true,
        val isApartment: Boolean = true,
    )
}