package ttt.mardsoul.restaurants.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ttt.mardsoul.restaurants.data.FavoriteUseCaseImpl
import ttt.mardsoul.restaurants.data.GatewayImpl
import ttt.mardsoul.restaurants.domain.FavoriteUseCase
import ttt.mardsoul.restaurants.domain.Gateway
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

	@Binds
	@Singleton
	abstract fun bindGateway(gatewayImpl: GatewayImpl): Gateway

	@Binds
	@Singleton
	abstract fun bindFavoriteUseCase(favoriteUseCaseImpl: FavoriteUseCaseImpl): FavoriteUseCase
}