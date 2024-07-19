package ttt.mardsoul.restaurants.data.dtoentities.detailsresponse


import com.google.gson.annotations.SerializedName

data class ScheduleDto(
	@SerializedName("day")
	val day: Int?,
	@SerializedName("end")
	val end: Int?,
	@SerializedName("start")
	val start: Int?
)