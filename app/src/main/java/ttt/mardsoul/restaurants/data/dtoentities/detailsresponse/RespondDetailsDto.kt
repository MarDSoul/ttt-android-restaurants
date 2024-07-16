package ttt.mardsoul.restaurants.data.dtoentities.detailsresponse


import com.google.gson.annotations.SerializedName

data class RespondDetailsDto(
	@SerializedName("averageCheck")
	val averageCheck: List<Any?>?,
	@SerializedName("categoryName")
	val categoryName: String?,
	@SerializedName("cuisines")
	val cuisines: List<String?>?,
	@SerializedName("detailedInfo")
	val detailedInfo: String?,
	@SerializedName("discount")
	val discount: Int?,
	@SerializedName("distance")
	val distance: Any?,
	@SerializedName("email")
	val email: Any?,
	@SerializedName("id")
	val id: Int?,
	@SerializedName("isFavorite")
	val isFavorite: Boolean?,
	@SerializedName("location")
	val location: LocationDto?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("phones")
	val phones: List<String?>?,
	@SerializedName("photos")
	val photos: List<String?>?,
	@SerializedName("rate")
	val rate: Double?,
	@SerializedName("rateCount")
	val rateCount: Int?,
	@SerializedName("review")
	val review: Any?,
	@SerializedName("reviewCount")
	val reviewCount: Int?,
	@SerializedName("schedule")
	val schedule: List<ScheduleDto?>?,
	@SerializedName("serviceLanguages")
	val serviceLanguages: List<String?>?,
	@SerializedName("services")
	val services: List<Any?>?,
	@SerializedName("socials")
	val socials: List<SocialDto?>?,
	@SerializedName("urls")
	val urls: List<String?>?
)