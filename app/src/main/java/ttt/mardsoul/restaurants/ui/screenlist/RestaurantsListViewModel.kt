package ttt.mardsoul.restaurants.ui.screenlist

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
import ttt.mardsoul.restaurants.utils.MapperModelToUi
import javax.inject.Inject

@HiltViewModel
class RestaurantsListViewModel @Inject constructor(
	private val gateway: Gateway
) : ViewModel() {

	private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
	val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

	init {
		getData()
	}

	private fun getData() {
		viewModelScope.launch {
			_uiState.emit(ListUiState.Loading)
			when (val respond = gateway.getRestaurants()) {
				is NetworkRespond.SuccessList -> {
					val data = respond.data.map { MapperModelToUi.map(it) }
					_uiState.emit(ListUiState.Success(data))
				}

				is NetworkRespond.Error -> {
					_uiState.emit(ListUiState.Error(respond.errors.message))
				}

				else -> {
					_uiState.emit(ListUiState.Error(RespondErrors.UNKNOWN_ERROR.message))
				}
			}
		}
	}
}

sealed interface ListUiState {
	data object Loading : ListUiState
	data class Success(val data: List<OrganizationUiEntity>) : ListUiState
	data class Error(val error: String) : ListUiState
}