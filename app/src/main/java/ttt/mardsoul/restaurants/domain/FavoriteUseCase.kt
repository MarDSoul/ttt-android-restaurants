package ttt.mardsoul.restaurants.domain

interface FavoriteUseCase {
	suspend fun clickOnFavorite(itemId: Int, currentFavourite: Boolean): NetworkRespond
}