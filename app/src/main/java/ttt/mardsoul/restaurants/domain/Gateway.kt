package ttt.mardsoul.restaurants.domain

interface Gateway {
	suspend fun getRestaurants(): NetworkRespond
	suspend fun getRestaurant(id: Int): NetworkRespond
}