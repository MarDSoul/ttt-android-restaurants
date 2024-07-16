package ttt.mardsoul.restaurants.data.dtoentities.listresponse


import com.google.gson.annotations.SerializedName

data class RespondDto(
	@SerializedName("data")
	val `data`: List<DataDto?>?,
	@SerializedName("meta")
	val meta: MetaDto?
)