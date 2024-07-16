package ttt.mardsoul.restaurants.ui.uientities

import androidx.compose.runtime.Stable

@Stable
data class OrganizationUiEntity(
	val id: Int,
	val imageUrl: String,
	val name: String,
	val isFavorite: Boolean,
	val rating: Float,
	val description: String,
)
