package ttt.mardsoul.restaurants.ui.screendetails

import androidx.compose.runtime.Stable

@Stable
data class OrganizationDetailUiEntity(
	val id: Int,
	val imageListUrl: List<String>,
	val name: String,
	val isFavorite: Boolean,
	val rating: Float,
	val averageCheck: String,
	val location: String,
	val cuisines: List<String>,
	val description: String
)
