package ttt.mardsoul.restaurants.domain.entities

data class OrganizationEntity(
	val id: Int? = null,
	val name: String? = null,
	val imageListUrl: List<String?>? = null,
	val isFavorite: Boolean? = null,
	val rate: Float? = null,
	val location: String? = null,
	val cuisines: List<String?>? = null,
	val averageCheck: String? = null,
	val description: String? = null,
)
