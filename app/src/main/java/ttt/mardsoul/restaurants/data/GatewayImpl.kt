package ttt.mardsoul.restaurants.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import ttt.mardsoul.restaurants.data.dtoentities.detailsresponse.RespondDetailsDto
import ttt.mardsoul.restaurants.data.dtoentities.listresponse.RespondDto
import ttt.mardsoul.restaurants.di.IoDispatcher
import ttt.mardsoul.restaurants.domain.Gateway
import ttt.mardsoul.restaurants.domain.NetworkRespond
import ttt.mardsoul.restaurants.domain.RespondErrors
import ttt.mardsoul.restaurants.utils.MapperDetailsToModel
import ttt.mardsoul.restaurants.utils.MapperDtoToModel
import javax.inject.Inject

class GatewayImpl @Inject constructor(
	private val api: RestaurantsApi,
	@IoDispatcher private val dispatcherIo: CoroutineDispatcher
) : Gateway {

	override suspend fun getRestaurants(): NetworkRespond {
		return getData { api.getRestaurants() }
	}

	override suspend fun getRestaurant(id: Int): NetworkRespond {
		return getData { api.getRestaurant(id) }
	}

	private suspend inline fun <reified T> getData(crossinline action: suspend () -> Response<T>): NetworkRespond =
		withContext(dispatcherIo) {
			try {
				val networkResponse = action.invoke()
				if (networkResponse.isSuccessful && networkResponse.body() != null) {
					when (T::class) {
						RespondDto::class -> {
							(networkResponse.body()!! as RespondDto).data
								.map { MapperDtoToModel.map(it!!) }
								.let { NetworkRespond.SuccessList(it) }
						}

						RespondDetailsDto::class -> {
							(networkResponse.body()!! as RespondDetailsDto)
								.let { MapperDetailsToModel.map(it) }
								.let { NetworkRespond.SuccessDetails(it) }
						}

						else -> {
							NetworkRespond.Error(RespondErrors.UNKNOWN_ERROR)
						}
					}
				} else {
					NetworkRespond.Error(RespondErrors.SERVER_ERROR)
				}
			} catch (e: Exception) {
				NetworkRespond.Error(RespondErrors.NETWORK_ERROR)
			}
		}
}


