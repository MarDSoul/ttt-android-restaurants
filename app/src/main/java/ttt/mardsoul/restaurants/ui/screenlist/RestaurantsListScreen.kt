package ttt.mardsoul.restaurants.ui.screenlist

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.mock.MockData
import ttt.mardsoul.restaurants.mock.TestLogs
import ttt.mardsoul.restaurants.mock.TestTags
import ttt.mardsoul.restaurants.ui.ListUiState
import ttt.mardsoul.restaurants.ui.LoadingScreen
import ttt.mardsoul.restaurants.ui.components.FavouriteIconButton
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme

@Composable
fun RestaurantsListScreen(
	modifier: Modifier = Modifier,
	uiState: ListUiState,
	onItemClick: (Int) -> Unit,
	onFavoriteClick: (Int, Boolean) -> Unit
) {
	TestLogs.show(TestTags.LIST_SCREEN, "RestaurantsListScreen: recomposition")

	when (uiState) {
		is ListUiState.Loading -> LoadingScreen(modifier)

		is ListUiState.Success -> {
			LazyColumn(
				modifier = modifier,
				verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
			) {
				items(items = uiState.data, key = { it.id }) {
					OrganizationCard(
						onClickCard = { onItemClick(it.id) },
						organization = it,
						onFavoriteClick = { onFavoriteClick(it.id, it.isFavorite) }
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
	onClickCard: () -> Unit,
	onFavoriteClick: () -> Unit
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
			onFavoriteClick = onFavoriteClick
		)
	}
}

@Composable
fun OrganizationCardDescription(
	modifier: Modifier = Modifier,
	organization: OrganizationUiEntity,
	onFavoriteClick: () -> Unit
) {
	Column(
		modifier = modifier.padding(
			start = dimensionResource(R.dimen.padding_normal),
			top = dimensionResource(R.dimen.padding_medium),
			end = dimensionResource(R.dimen.padding_normal),
			bottom = dimensionResource(R.dimen.padding_medium)
		)
	) {
		Row(
			modifier = modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(text = organization.name, style = MaterialTheme.typography.titleMedium)
			FavouriteIconButton(
				isFavorite = organization.isFavorite,
				onFavoriteClick = onFavoriteClick
			)
		}
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
	RestaurantsTheme {
		OrganizationCard(
			organization = MockData.mockUiEntity,
			onClickCard = {},
			onFavoriteClick = {})
	}
}

@Preview(showBackground = true)
@Composable
fun RestaurantsListScreenPreview() {
	RestaurantsTheme {
		RestaurantsListScreen(
			uiState = ListUiState.Success(MockData.mockUiEntityList),
			onItemClick = {},
			onFavoriteClick = { _, _ -> })
	}
}