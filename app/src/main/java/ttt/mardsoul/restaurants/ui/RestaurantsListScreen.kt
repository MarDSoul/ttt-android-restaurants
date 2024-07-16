package ttt.mardsoul.restaurants.ui

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ttt.mardsoul.restaurants.Mock
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme
import ttt.mardsoul.restaurants.ui.uientities.OrganizationUiEntity

@Composable
fun RestaurantsListScreen(
	modifier: Modifier = Modifier,
	viewModel: RestaurantsListViewModel = viewModel()
) {
	val uiState = viewModel.uiState.collectAsState()
	val context = LocalContext.current

	when (val state = uiState.value) {
		is ListUiState.Loading -> LoadingScreen(modifier)
		is ListUiState.Error -> {
			Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
		}

		is ListUiState.Success -> {
			LazyColumn(
				modifier = modifier,
				verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
			) {
				items(items = state.data, key = { it.id }) {
					OrganizationCard(organization = it)
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
			contentDescription = null,
			modifier = Modifier.height(160.dp),
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
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(text = organization.name)
			IconButton(onClick = onClickFavorite) {
				Icon(
					imageVector = if (organization.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
					contentDescription = null,
					tint = MaterialTheme.colorScheme.primary
				)
			}
		}
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			RatingIcon(rating = organization.rating)
			Spacer(modifier = Modifier.size(8.dp))
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
			contentDescription = null,
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
		OrganizationCard(organization = Mock.organizationUiEntity)
	}
}