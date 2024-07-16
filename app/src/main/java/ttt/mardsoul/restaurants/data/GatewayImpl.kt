package ttt.mardsoul.restaurants.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import ttt.mardsoul.restaurants.di.ApplicationScope
import ttt.mardsoul.restaurants.di.IoDispatcher
import ttt.mardsoul.restaurants.domain.Gateway
import ttt.mardsoul.restaurants.domain.NetworkRespond
import ttt.mardsoul.restaurants.domain.RespondErrors
import ttt.mardsoul.restaurants.utils.MapperDtoToModel
import javax.inject.Inject

class GatewayImpl @Inject constructor(
	private val api: RestaurantsApi,
	@ApplicationScope private val applicationScope: CoroutineScope,
	@IoDispatcher private val dispatcherIo: CoroutineDispatcher
) : Gateway {

	override suspend fun getRestaurants(): NetworkRespond {
		val job = applicationScope.async(dispatcherIo) {
			try {
				val networkResponse = api.getRestaurants()
				if (networkResponse.isSuccessful && networkResponse.body() != null) {
					networkResponse.body()!!.data
						?.map { MapperDtoToModel.map(it!!) }
						?.let { NetworkRespond.Success(it) }
				} else {
					NetworkRespond.Error(RespondErrors.SERVER_ERROR)
				}
			} catch (e: Exception) {
				NetworkRespond.Error(RespondErrors.NETWORK_ERROR)
			}
		}

		return job.await()!!
	}

}


