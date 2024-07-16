package ttt.mardsoul.restaurants.utils

import ttt.mardsoul.restaurants.domain.entities.OrganizationEntity
import ttt.mardsoul.restaurants.ui.uientities.OrganizationUiEntity

object MapperModelToUi : Mapper<OrganizationEntity, OrganizationUiEntity> {
	override fun map(from: OrganizationEntity): OrganizationUiEntity {
		return OrganizationUiEntity(
			id = from.id ?: 0,
			name = from.name ?: "",
			imageUrl = from.imageUrl ?: "",
			isFavorite = from.isFavorite ?: false,
			rating = from.rate?.toFloat() ?: 0f,
			description = from.description ?: ""
		)
	}
}