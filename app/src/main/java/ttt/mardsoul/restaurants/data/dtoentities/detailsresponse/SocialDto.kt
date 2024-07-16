package ttt.mardsoul.restaurants.data.dtoentities.detailsresponse


import com.google.gson.annotations.SerializedName

data class SocialDto(
	@SerializedName("id")
	val id: Int?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("organization")
	val organization: Int?,
	@SerializedName("type")
	val type: Int?,
	@SerializedName("url")
	val url: String?
)