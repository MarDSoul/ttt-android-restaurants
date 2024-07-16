package ttt.mardsoul.restaurants.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ttt.mardsoul.restaurants.data.GatewayImpl
import ttt.mardsoul.restaurants.domain.Gateway

@Module
@InstallIn(SingletonComponent::class)
abstract class GatewayModule {

	@Binds
	abstract fun bindGateway(gatewayImpl: GatewayImpl): Gateway
}