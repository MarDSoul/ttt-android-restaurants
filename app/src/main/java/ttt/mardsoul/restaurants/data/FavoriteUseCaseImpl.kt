package ttt.mardsoul.restaurants.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import ttt.mardsoul.restaurants.di.ApplicationScope
import ttt.mardsoul.restaurants.di.IoDispatcher
import ttt.mardsoul.restaurants.domain.FavoriteUseCase
import javax.inject.Inject

class FavoriteUseCaseImpl @Inject constructor(
	private val api: RestaurantsApi,
	@ApplicationScope private val applicationScope: CoroutineScope,
	@IoDispatcher private val dispatcherIo: CoroutineDispatcher
) : FavoriteUseCase {
	override fun clickOnFavourite(itemId: Int, currentFavourite: Boolean) {
		if (currentFavourite) {
			removeFromFavourite(itemId)
		} else {
			addToFavourite(itemId)
		}
	}

	private fun addToFavourite(itemId: Int) {
		TODO("Not yet implemented")

	}

	private fun removeFromFavourite(itemId: Int) {
		TODO("Not yet implemented")
	}

}