package ttt.mardsoul.restaurants.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ttt.mardsoul.restaurants.di.DefaultDispatcher
import ttt.mardsoul.restaurants.domain.FavoriteUseCase
import ttt.mardsoul.restaurants.domain.Gateway
import ttt.mardsoul.restaurants.domain.NetworkRespond
import ttt.mardsoul.restaurants.domain.RespondErrors
import ttt.mardsoul.restaurants.mock.TestLogs
import ttt.mardsoul.restaurants.mock.TestTags
import ttt.mardsoul.restaurants.ui.screendetails.OrganizationDetailUiEntity
import ttt.mardsoul.restaurants.ui.screenlist.OrganizationUiEntity
import ttt.mardsoul.restaurants.utils.MapperModelToDetailsUi
import ttt.mardsoul.restaurants.utils.MapperModelToUi
import javax.inject.Inject

@HiltViewModel
class RestaurantsListAndDetailsViewModel @Inject constructor(
	private val gateway: Gateway,
	private val favoriteUseCase: FavoriteUseCase,
	@DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

	private lateinit var restaurantsList: List<OrganizationUiEntity>

	private val _listUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
	val listUiState: StateFlow<ListUiState> = _listUiState.asStateFlow()

	private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Empty)
	val detailsUiState: StateFlow<DetailsUiState> = _detailsUiState.asStateFlow()
	private val _navigateToDetails = MutableSharedFlow<Unit>()
	val navigateToDetails: SharedFlow<Unit> = _navigateToDetails.asSharedFlow()

	private val _errorEvent = Channel<ErrorEvent>(capacity = Channel.BUFFERED)
	val errorEvent: Flow<ErrorEvent>
		get() = _errorEvent.receiveAsFlow()

	init {
		getData()
	}

	private fun getData() {
		viewModelScope.launch {
			_listUiState.emit(ListUiState.Loading)
			when (val respond = gateway.getRestaurants()) {
				is NetworkRespond.SuccessList -> {
					val data = respond.data.map { MapperModelToUi.map(it) }
					restaurantsList = data
					_listUiState.emit(ListUiState.Success(restaurantsList))
					TestLogs.show(TestTags.VIEW_MODEL, "ViewModel: list data emitted")
				}

				is NetworkRespond.Error -> {
					_errorEvent.send(ErrorEvent.ErrorMessage(respond.errors.message))
				}

				else -> {
					_errorEvent.send(ErrorEvent.ErrorMessage(RespondErrors.UNKNOWN_ERROR.message))
				}
			}
		}
	}

	fun getDetails(id: Int) {
		viewModelScope.launch {
			TestLogs.show(TestTags.VIEW_MODEL, "ViewModel: getDetails called for id: $id")
			when (val respond = gateway.getRestaurant(id)) {
				is NetworkRespond.SuccessDetails -> {
					val data = MapperModelToDetailsUi.map(respond.data)
					_detailsUiState.emit(DetailsUiState.Success(data))
					_navigateToDetails.emit(Unit)
					TestLogs.show(TestTags.VIEW_MODEL, "ViewModel: details data emitted")
				}

				is NetworkRespond.Error -> {
					_errorEvent.send(ErrorEvent.ErrorMessage(respond.errors.message))
				}

				else -> {
					_errorEvent.send(ErrorEvent.ErrorMessage(RespondErrors.UNKNOWN_ERROR.message))
				}
			}
		}
	}

	fun resetDetailsState() {
		_detailsUiState.value = DetailsUiState.Empty
		TestLogs.show(TestTags.VIEW_MODEL, "ViewModel: resetDetailsState")
	}

	fun filterList(isFavoritesSorted: Boolean) {
		viewModelScope.launch(defaultDispatcher) {
			if (!isFavoritesSorted) {
				val sortedList = restaurantsList.filter { it.isFavorite }
				_listUiState.emit(ListUiState.Success(sortedList, true))
			} else {
				_listUiState.emit(ListUiState.Success(restaurantsList, false))
			}
		}
	}

}

sealed interface ListUiState {
	data object Loading : ListUiState
	data class Success(
		val data: List<OrganizationUiEntity>,
		val isFavoritesSorted: Boolean = false
	) : ListUiState
}

sealed interface DetailsUiState {
	data object Empty : DetailsUiState
	data class Success(val data: OrganizationDetailUiEntity) : DetailsUiState
}

sealed interface ErrorEvent {
	data class ErrorMessage(val error: String) : ErrorEvent
}