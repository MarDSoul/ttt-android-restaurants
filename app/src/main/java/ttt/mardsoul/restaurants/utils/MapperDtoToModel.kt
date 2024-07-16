package ttt.mardsoul.restaurants.utils

import ttt.mardsoul.restaurants.data.dtoentities.listresponse.DataDto
import ttt.mardsoul.restaurants.domain.entities.OrganizationEntity

object MapperDtoToModel : Mapper<DataDto, OrganizationEntity> {
	override fun map(from: DataDto): OrganizationEntity {
		return OrganizationEntity(
			id = from.id ?: 0,
			name = from.name ?: "",
			imageUrl = from.photo ?: "",
			isFavorite = from.isFavorite ?: false,
			rate = from.rate?.toFloat() ?: 0f,
			description = if (!from.cuisines.isNullOrEmpty()) from.cuisines.joinToString(separator = ", ") else "",
		)
	}
}