package ttt.mardsoul.restaurants.mock

import ttt.mardsoul.restaurants.ui.screendetails.OrganizationDetailUiEntity
import ttt.mardsoul.restaurants.ui.screenlist.OrganizationUiEntity

object MockData {

	val organizationUiEntity = OrganizationUiEntity(
		id = 1,
		imageUrl = "https://s3-alpha-sig.figma.com/img/5a48/7afd/dde69baf653722e02cf712993cfe437a?Expires=1722211200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=GmavoSw6V6Xr2uuUSyP0Z8w~~~4c84-VpU8cCqXNLR8w2hVPR0R1jM72s1rEav3eYz3B1ZcMPp0GPhSHboM-m~VEiAduetdIlJ9Sz-rvWWrzEHKt~-b9IWgmEuSnPSuOP7zOEqdjNnRGlaElgtkttaztpjF9TBwSZKqmhQOFja92uCJnJlijf4uVc49F-mtyfhJUuMT8EdWsfSYhkvhXGxD29m0NyZgiwiIgS6XvUZTTXi64TfoP8VP0IKnGAVsZo9yUr7f-yKQR4405YzcWhORPk2jgpq5izY3aDT9E5xIWcEwX8bH2~omnukUx9z-S1J9gDWl5ntL~jAi2twR2cw__",
		name = "Restaurant Name",
		isFavorite = false,
		rating = 4.5f,
		description = "Long description of restaurant, long description of restaurant, long description of restaurant"
	)

	val organizationDetailUiEntity = OrganizationDetailUiEntity(
		id = 0,
		name = "Restaurant Name",
		location = "11, Some street, Some city, Some country",
		cuisines = listOf("Short text", "Short text", "Short text", "Short text"),
		description = "Very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description " +
				"very-very-very-very-long-description",
		imageListUrl = listOf(),
		rating = 5f,
		averageCheck = "$ 150",
		isFavorite = true
	)
}