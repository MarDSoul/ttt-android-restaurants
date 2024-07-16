package ttt.mardsoul.restaurants.utils

interface Mapper<F, T> {
	fun map(from: F): T
}