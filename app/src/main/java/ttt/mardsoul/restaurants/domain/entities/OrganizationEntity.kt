package ttt.mardsoul.restaurants.domain.entities

data class OrganizationEntity(
	val id: Int? = null,
	val name: String? = null,
	val imageUrl: String? = null,
	val isFavorite: Boolean? = null,
	val rate: Float? = null,
	val description: String? = null,
)
