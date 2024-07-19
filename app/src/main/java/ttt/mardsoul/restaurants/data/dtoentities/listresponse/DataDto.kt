package ttt.mardsoul.restaurants.data.dtoentities.listresponse


import com.google.gson.annotations.SerializedName

data class DataDto(
	@SerializedName("averageCheck")
	val averageCheck: List<Any?>?,
	@SerializedName("cuisines")
	val cuisines: List<String?>?,
	@SerializedName("distance")
	val distance: Any?,
	@SerializedName("id")
	val id: Int?,
	@SerializedName("isFavorite")
	val isFavorite: Boolean?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("photo")
	val photo: String?,
	@SerializedName("rate")
	val rate: Double?
)