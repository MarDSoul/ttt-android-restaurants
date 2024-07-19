package ttt.mardsoul.restaurants.data.dtoentities.detailsresponse


import com.google.gson.annotations.SerializedName

data class LocationDto(
	@SerializedName("address")
	val address: String?,
	@SerializedName("city")
	val city: String?,
	@SerializedName("district")
	val district: Int?,
	@SerializedName("id")
	val id: Int?,
	@SerializedName("latitude")
	val latitude: Double?,
	@SerializedName("longitude")
	val longitude: Double?,
	@SerializedName("organization")
	val organization: Int?
)