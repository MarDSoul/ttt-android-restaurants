package ttt.mardsoul.restaurants.domain

interface Gateway {
	suspend fun getRestaurants(): NetworkRespond
}