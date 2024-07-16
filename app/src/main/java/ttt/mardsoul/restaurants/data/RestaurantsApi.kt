package ttt.mardsoul.restaurants.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ttt.mardsoul.restaurants.BuildConfig.TOKEN
import ttt.mardsoul.restaurants.data.dtoentities.detailsresponse.RespondDetailsDto
import ttt.mardsoul.restaurants.data.dtoentities.listresponse.RespondDto

interface RestaurantsApi {

	@Headers("Authorization: Token $TOKEN")
	@GET("internship/organizations/category/1/organizations/")
	suspend fun getRestaurants(): Response<RespondDto>

	@Headers("Authorization: Token $TOKEN")
	@GET("internship/organization/{id}/")
	suspend fun getRestaurant(@Path("id") id: Int): Response<RespondDetailsDto>
}