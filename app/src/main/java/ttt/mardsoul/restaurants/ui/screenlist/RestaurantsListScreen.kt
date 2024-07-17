package ttt.mardsoul.restaurants.ui.screenlist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.ui.ListUiState
import ttt.mardsoul.restaurants.ui.LoadingScreen
import ttt.mardsoul.restaurants.ui.components.TitleWithFavouriteRow
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme

@Composable
fun RestaurantsListScreen(
	modifier: Modifier = Modifier,
	uiState: ListUiState,
	onItemClick: (Int) -> Unit
) {
	val context = LocalContext.current

	when (uiState) {
		is ListUiState.Loading -> LoadingScreen(modifier)
		is ListUiState.Error -> {
			Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
		}

		is ListUiState.Success -> {
			LazyColumn(
				modifier = modifier,
				verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
			) {
				items(items = uiState.data, key = { it.id }) {
					OrganizationCard(
						onClickCard = { onItemClick(it.id) },
						organization = it
					)
				}
			}
		}
	}
}

@Composable
fun OrganizationCard(
	modifier: Modifier = Modifier,
	organization: OrganizationUiEntity,
	onClickCard: () -> Unit = {},
	onClickFavorite: () -> Unit = {}
) {
	Card(
		modifier = modifier.fillMaxWidth(),
		onClick = onClickCard,
		colors = CardColors(
			containerColor = MaterialTheme.colorScheme.surface,
			contentColor = MaterialTheme.colorScheme.onSurface,
			disabledContainerColor = MaterialTheme.colorScheme.surface,
			disabledContentColor = MaterialTheme.colorScheme.onSurface
		),
		shape = MaterialTheme.shapes.large
	) {
		AsyncImage(
			model = organization.imageUrl,
			contentDescription = organization.name,
			modifier = Modifier.height(dimensionResource(R.dimen.image_list_height)),
			contentScale = ContentScale.Crop
		)
		OrganizationCardDescription(
			organization = organization,
			onClickFavorite = onClickFavorite
		)
	}
}

@Composable
fun OrganizationCardDescription(
	modifier: Modifier = Modifier,
	organization: OrganizationUiEntity,
	onClickFavorite: () -> Unit = {}
) {
	Column(
		modifier = modifier.padding(
			start = dimensionResource(R.dimen.padding_normal),
			top = dimensionResource(R.dimen.padding_medium),
			end = dimensionResource(R.dimen.padding_normal),
			bottom = dimensionResource(R.dimen.padding_medium)
		)
	) {
		TitleWithFavouriteRow(
			name = organization.name,
			isFavorite = organization.isFavorite,
			onFavoriteClick = onClickFavorite
		)
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			RatingIcon(rating = organization.rating)
			Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
			Text(
				text = organization.description,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)
		}
	}
}

@Composable
fun RatingIcon(
	modifier: Modifier = Modifier,
	rating: Float
) {
	Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
		Icon(
			imageVector = Icons.Default.Star,
			contentDescription = stringResource(R.string.rating_cd),
			tint = MaterialTheme.colorScheme.primary
		)
		Spacer(modifier = Modifier.size(4.dp))
		Text(text = rating.toString())
	}
}


@Preview()
@Composable
fun ListItemCardPreview() {

	val organizationUiEntity = OrganizationUiEntity(
		id = 1,
		imageUrl = "https://s3-alpha-sig.figma.com/img/5a48/7afd/dde69baf653722e02cf712993cfe437a?Expires=1722211200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=GmavoSw6V6Xr2uuUSyP0Z8w~~~4c84-VpU8cCqXNLR8w2hVPR0R1jM72s1rEav3eYz3B1ZcMPp0GPhSHboM-m~VEiAduetdIlJ9Sz-rvWWrzEHKt~-b9IWgmEuSnPSuOP7zOEqdjNnRGlaElgtkttaztpjF9TBwSZKqmhQOFja92uCJnJlijf4uVc49F-mtyfhJUuMT8EdWsfSYhkvhXGxD29m0NyZgiwiIgS6XvUZTTXi64TfoP8VP0IKnGAVsZo9yUr7f-yKQR4405YzcWhORPk2jgpq5izY3aDT9E5xIWcEwX8bH2~omnukUx9z-S1J9gDWl5ntL~jAi2twR2cw__",
		name = "Restaurant Name",
		isFavorite = false,
		rating = 4.5f,
		description = "Long description of restaurant, long description of restaurant, long description of restaurant"
	)

	RestaurantsTheme {
		OrganizationCard(organization = organizationUiEntity)
	}
}