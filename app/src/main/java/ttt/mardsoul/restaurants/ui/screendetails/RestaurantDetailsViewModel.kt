package ttt.mardsoul.restaurants.ui.screendetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ttt.mardsoul.restaurants.domain.Gateway
import ttt.mardsoul.restaurants.domain.NetworkRespond
import ttt.mardsoul.restaurants.domain.RespondErrors
import ttt.mardsoul.restaurants.utils.MapperModelToDetailsUi
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
	private val gateway: Gateway
) : ViewModel() {

	private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
	val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

	fun getDetails(id: Int) {
		viewModelScope.launch {
			_uiState.emit(DetailsUiState.Loading)
			when (val respond = gateway.getRestaurant(id)) {
				is NetworkRespond.SuccessDetails -> {
					val data = MapperModelToDetailsUi.map(respond.data)
					_uiState.emit(DetailsUiState.Success(data))
				}

				is NetworkRespond.Error -> {
					_uiState.emit(DetailsUiState.Error(respond.errors.message))
				}

				else -> {
					_uiState.emit(DetailsUiState.Error(RespondErrors.UNKNOWN_ERROR.message))
				}
			}
		}
	}
}

sealed interface DetailsUiState {
	data object Loading : DetailsUiState
	data class Success(val data: OrganizationDetailUiEntity) : DetailsUiState
	data class Error(val error: String) : DetailsUiState
}