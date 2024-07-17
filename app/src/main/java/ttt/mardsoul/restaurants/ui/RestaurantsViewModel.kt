package ttt.mardsoul.restaurants.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ttt.mardsoul.restaurants.domain.Gateway
import ttt.mardsoul.restaurants.domain.NetworkRespond
import ttt.mardsoul.restaurants.domain.RespondErrors
import ttt.mardsoul.restaurants.ui.screendetails.OrganizationDetailUiEntity
import ttt.mardsoul.restaurants.ui.screenlist.OrganizationUiEntity
import ttt.mardsoul.restaurants.utils.MapperModelToDetailsUi
import ttt.mardsoul.restaurants.utils.MapperModelToUi
import ttt.mardsoul.restaurants.utils.VIEW_MODEL_TAG
import javax.inject.Inject

@HiltViewModel
class RestaurantsListAndDetailsViewModel @Inject constructor(
	private val gateway: Gateway
) : ViewModel() {

	private val _listUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
	val listUiState: StateFlow<ListUiState> = _listUiState.asStateFlow()

	private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Empty)
	val detailsUiState: StateFlow<DetailsUiState> = _detailsUiState.asStateFlow()
	private val _navigateToDetails = MutableSharedFlow<Unit>()
	val navigateToDetails: SharedFlow<Unit> = _navigateToDetails.asSharedFlow()

	init {
		getData()
	}

	private fun getData() {
		viewModelScope.launch {
			_listUiState.emit(ListUiState.Loading)
			when (val respond = gateway.getRestaurants()) {
				is NetworkRespond.SuccessList -> {
					val data = respond.data.map { MapperModelToUi.map(it) }
					_listUiState.emit(ListUiState.Success(data))
					Log.d(VIEW_MODEL_TAG, "list data emitted")
				}

				is NetworkRespond.Error -> {
					_listUiState.emit(ListUiState.Error(respond.errors.message))
				}

				else -> {
					_listUiState.emit(ListUiState.Error(RespondErrors.UNKNOWN_ERROR.message))
				}
			}
		}
	}

	fun getDetails(id: Int) {
		viewModelScope.launch {
			Log.d(VIEW_MODEL_TAG, "getDetails called for id: $id")
			when (val respond = gateway.getRestaurant(id)) {
				is NetworkRespond.SuccessDetails -> {
					val data = MapperModelToDetailsUi.map(respond.data)
					_detailsUiState.emit(DetailsUiState.Success(data))
					_navigateToDetails.emit(Unit)
					Log.d(VIEW_MODEL_TAG, "_navigateToDetails emitted")
				}

				is NetworkRespond.Error -> {
					_listUiState.emit(ListUiState.Error(respond.errors.message))
				}

				else -> {
					_listUiState.emit(ListUiState.Error(RespondErrors.UNKNOWN_ERROR.message))
				}
			}
		}
	}

	fun resetDetailsState() {
		_detailsUiState.value = DetailsUiState.Empty
		Log.d(VIEW_MODEL_TAG, "_detailsUiState: DetailsUiState.Empty")
	}
}

sealed interface ListUiState {
	data object Loading : ListUiState
	data class Success(val data: List<OrganizationUiEntity>) : ListUiState
	data class Error(val error: String) : ListUiState
}

sealed interface DetailsUiState {
	data object Empty : DetailsUiState
	data class Success(val data: OrganizationDetailUiEntity) : DetailsUiState
}