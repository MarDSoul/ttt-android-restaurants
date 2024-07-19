package ttt.mardsoul.restaurants.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import ttt.mardsoul.restaurants.di.IoDispatcher
import ttt.mardsoul.restaurants.domain.FavoriteUseCase
import ttt.mardsoul.restaurants.domain.NetworkRespond
import ttt.mardsoul.restaurants.domain.RespondErrors
import javax.inject.Inject

class FavoriteUseCaseImpl @Inject constructor(
	private val api: RestaurantsApi,
	@IoDispatcher private val dispatcherIo: CoroutineDispatcher
) : FavoriteUseCase {
	override suspend fun clickOnFavorite(itemId: Int, currentFavourite: Boolean): NetworkRespond {
		return if (currentFavourite) {
			removeFromFavorite(itemId)
		} else {
			addToFavorite(itemId)
		}
	}

	private suspend fun addToFavorite(itemId: Int): NetworkRespond {
		return sendFavouriteRequest { api.addToFavorite(itemId) }
	}

	private suspend fun removeFromFavorite(itemId: Int): NetworkRespond {
		return sendFavouriteRequest { api.removeFromFavorite(itemId) }
	}

	private suspend fun sendFavouriteRequest(action: suspend () -> Response<Any?>): NetworkRespond =
		withContext(dispatcherIo) {
			try {
				val response = action.invoke()
				if (response.isSuccessful) {
					NetworkRespond.SuccessFavourite
				} else {
					NetworkRespond.Error(RespondErrors.SERVER_ERROR)
				}
			} catch (e: Exception) {
				NetworkRespond.Error(RespondErrors.NETWORK_ERROR)
			}
		}
}