package ttt.mardsoul.restaurants.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ttt.mardsoul.restaurants.data.RestaurantsApi
import javax.inject.Singleton


private const val BASE_URL = "https://api.mycyprus.app/api/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit {
		return Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BASE_URL)
			.build()
	}

	@Provides
	@Singleton
	fun provideRestaurantsApi(retrofit: Retrofit): RestaurantsApi {
		return retrofit.create(RestaurantsApi::class.java)
	}
}