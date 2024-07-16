package ttt.mardsoul.restaurants.domain

import ttt.mardsoul.restaurants.domain.entities.OrganizationEntity

sealed class NetworkRespond {
	class SuccessList(val data: List<OrganizationEntity>) : NetworkRespond()
	class SuccessDetails(val data: OrganizationEntity) : NetworkRespond()
	class Error(val errors: RespondErrors) : NetworkRespond()
}

enum class RespondErrors(val message: String) {
	NETWORK_ERROR("Network error"),
	SERVER_ERROR("Server error"),
	UNKNOWN_ERROR("Something went wrong")
}