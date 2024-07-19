package ttt.mardsoul.restaurants.utils

import ttt.mardsoul.restaurants.domain.entities.OrganizationEntity
import ttt.mardsoul.restaurants.ui.screendetails.OrganizationDetailUiEntity

object MapperModelToDetailsUi : Mapper<OrganizationEntity, OrganizationDetailUiEntity> {
	override fun map(from: OrganizationEntity): OrganizationDetailUiEntity {
		return OrganizationDetailUiEntity(
			id = from.id ?: 0,
			name = from.name ?: "",
			imageListUrl =
			if (from.imageListUrl.isNullOrEmpty()) {
				emptyList()
			} else {
				from.imageListUrl.map { it ?: "" }
			},
			isFavorite = from.isFavorite ?: false,
			rating = from.rate ?: 0f,
			averageCheck = from.averageCheck ?: "",
			location = from.location ?: "",
			cuisines =
			if (from.cuisines.isNullOrEmpty()) {
				emptyList()
			} else {
				from.cuisines.map { it ?: "" }
			},
			description = from.description ?: ""
		)
	}
}