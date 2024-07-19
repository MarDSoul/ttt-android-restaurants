package ttt.mardsoul.restaurants.data.dtoentities.listresponse


import com.google.gson.annotations.SerializedName

data class MetaDto(
	@SerializedName("currentPage")
	val currentPage: Int?,
	@SerializedName("pageCount")
	val pageCount: Int?,
	@SerializedName("perPage")
	val perPage: Int?,
	@SerializedName("totalCount")
	val totalCount: Int?
)