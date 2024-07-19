package ttt.mardsoul.restaurants.utils

import ttt.mardsoul.restaurants.data.dtoentities.detailsresponse.RespondDetailsDto
import ttt.mardsoul.restaurants.domain.entities.OrganizationEntity

object MapperDetailsToModel : Mapper<RespondDetailsDto, OrganizationEntity> {
	override fun map(from: RespondDetailsDto): OrganizationEntity {
		return OrganizationEntity(
			id = from.id ?: 0,
			name = from.name ?: "",
			imageListUrl = from.photos ?: listOf(),
			isFavorite = from.isFavorite ?: false,
			rate = from.rate?.toFloat() ?: 0f,
			description = from.detailedInfo ?: "",
			location = from.location?.address ?: "",
			cuisines = from.cuisines ?: listOf(),
			averageCheck = from.averageCheck?.firstOrNull()?.toString() ?: "",
		)
	}
}