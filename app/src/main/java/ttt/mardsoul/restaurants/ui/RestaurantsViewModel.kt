package ttt.mardsoul.restaurants.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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
class RestaurantsViewModel @Inject constructor(
	private val gateway: Gateway,
	private val favoriteUseCase: FavoriteUseCase,
	@DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

	private lateinit var restaurantsList: List<OrganizationUiEntity>
	private lateinit var restaurantsFilteredList: List<OrganizationUiEntity>

	private val _appBarState = MutableStateFlow(AppBarState())
	val appBarState: StateFlow<AppBarState> = _appBarState.asStateFlow()

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
			TestLogs.show(TestTags.VIEW_MODEL, "ViewModel: getList")
			_listUiState.emit(ListUiState.Loading)
			when (val respond = gateway.getRestaurants()) {
				is NetworkRespond.SuccessList -> {
					val data = respond.data.map { MapperModelToUi.map(it) }
					restaurantsList = data
					updateCountFavorite()
					_listUiState.emit(ListUiState.Success(restaurantsList))
					TestLogs.show(TestTags.VIEW_MODEL, "ViewModel: list data emitted")
				}

				is NetworkRespond.Error -> {
					sendErrorEvent(respond.errors)
				}

				else -> {
					sendErrorEvent(RespondErrors.UNKNOWN_ERROR)
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
					sendErrorEvent(respond.errors)
				}

				else -> {
					sendErrorEvent(RespondErrors.UNKNOWN_ERROR)
				}
			}
		}
	}

	fun resetDetailsState() {
		_detailsUiState.value = DetailsUiState.Empty
		TestLogs.show(TestTags.VIEW_MODEL, "ViewModel: resetDetailsState")
	}

	private suspend fun sendErrorEvent(error: RespondErrors) {
		_errorEvent.send(ErrorEvent.ErrorMessage(error.message))
	}

	fun filterList() {
		viewModelScope.launch(defaultDispatcher) {
			val isFavoritesSorted = _listUiState.value is ListUiState.Success &&
					(_listUiState.value as ListUiState.Success).isFavoritesSorted
			if (!isFavoritesSorted) {
				val sortedList = restaurantsList.filter { it.isFavorite }
				_listUiState.emit(ListUiState.Success(sortedList, true))
			} else {
				_listUiState.emit(ListUiState.Success(restaurantsList, false))
			}
			updateIsFavoritesFiltered()
		}
	}

	fun onFavoriteClick(id: Int, currentFavorite: Boolean) {
		viewModelScope.launch {
			var tmpList: List<OrganizationUiEntity>? = restaurantsList
			_listUiState.update { curState ->
				val list = restaurantsList.toMutableList()
				list.indexOfFirst { it.id == id }.let { index ->
					if (index != -1) {
						list[index] = list[index].copy(isFavorite = !currentFavorite)
					}
				}
				restaurantsList = list.toList()
				updateFilteredList()
				if (_appBarState.value.isFavoritesFiltered) {
					(curState as ListUiState.Success).copy(data = restaurantsFilteredList)
				} else {
					(curState as ListUiState.Success).copy(data = restaurantsList)
				}
			}

			var copyDetails: DetailsUiState.Success? = null
			_detailsUiState.update { curState ->
				if (curState is DetailsUiState.Success) {
					copyDetails = curState
					curState.copy(data = curState.data.copy(isFavorite = !currentFavorite))
				} else {
					curState
				}
			}

			updateCountFavorite()

			val respond = favoriteUseCase.clickOnFavorite(id, currentFavorite)
			if (respond is NetworkRespond.SuccessFavourite) {
				tmpList = null
				copyDetails = null
			} else {
				delay(3_000L) // TODO("Imitation network delay after error")
				_errorEvent.send(ErrorEvent.ErrorMessage(RespondErrors.UNKNOWN_ERROR.message))

				restaurantsList = tmpList!!
				updateFilteredList()
				_listUiState.update { curState ->
					if (_appBarState.value.isFavoritesFiltered) {
						(curState as ListUiState.Success).copy(data = restaurantsFilteredList)
					} else {
						(curState as ListUiState.Success).copy(data = restaurantsList)
					}
				}

				copyDetails?.let {
					_detailsUiState.update { _ -> it }
				}
				updateCountFavorite()
			}
		}
	}

	private fun updateCountFavorite() {
		_appBarState.update { curState ->
			curState.copy(countFavorite = restaurantsList.count { it.isFavorite })
		}
	}

	private fun updateIsFavoritesFiltered() {
		_appBarState.update { curState ->
			curState.copy(isFavoritesFiltered = !curState.isFavoritesFiltered)
		}
	}

	private fun updateFilteredList() {
		restaurantsFilteredList = restaurantsList.filter { it.isFavorite }
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

data class AppBarState(
	val countFavorite: Int = 0,
	val isFavoritesFiltered: Boolean = false
)