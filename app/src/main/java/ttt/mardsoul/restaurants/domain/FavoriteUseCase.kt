package ttt.mardsoul.restaurants.domain

interface FavoriteUseCase {
	fun clickOnFavourite(itemId: Int, currentFavourite: Boolean)
}