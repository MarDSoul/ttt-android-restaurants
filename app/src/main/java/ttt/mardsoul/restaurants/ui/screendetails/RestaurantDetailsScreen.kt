package ttt.mardsoul.restaurants.ui.screendetails

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.ui.LoadingScreen
import ttt.mardsoul.restaurants.ui.components.TitleWithFavouriteRow
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme

@Composable
fun RestaurantDetailsScreen(
	modifier: Modifier = Modifier,
	viewModel: RestaurantDetailsViewModel = hiltViewModel(),
	restaurantId: Int
) {

	LaunchedEffect(Unit) {
		viewModel.getDetails(restaurantId)
	}

	val uiState = viewModel.uiState.collectAsState()
	val context = LocalContext.current

	when (val state = uiState.value) {
		is DetailsUiState.Loading -> LoadingScreen(modifier)
		is DetailsUiState.Error -> {
			Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
		}

		is DetailsUiState.Success -> {
			Column(modifier = modifier) {
				PhotoPager(
					modifier = Modifier.height(dimensionResource(R.dimen.image_details_height)),
					photosListUrl = state.data.imageListUrl
				)
				HeaderInfo(detailUiEntity = state.data)
				Description(modifier = Modifier.weight(1f), detailUiEntity = state.data)
			}
		}
	}
}

@Composable
fun PhotoPager(
	modifier: Modifier = Modifier,
	photosListUrl: List<String>
) {
	val pagerState = rememberPagerState(pageCount = { photosListUrl.size })
	HorizontalPager(state = pagerState, modifier = modifier, beyondViewportPageCount = 2) { page ->
		AsyncImage(model = photosListUrl[page], contentDescription = null)
	}
}

@Composable
fun HeaderInfo(
	modifier: Modifier = Modifier,
	detailUiEntity: OrganizationDetailUiEntity,
	onFavoriteClick: () -> Unit = {}
) {
	Column(modifier = modifier) {
		TitleWithFavouriteRow(
			name = detailUiEntity.name,
			isFavorite = detailUiEntity.isFavorite,
			onFavoriteClick = onFavoriteClick
		)
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			StarRatingBar(rating = detailUiEntity.rating)
			Text(text = detailUiEntity.averageCheck, color = Color.Gray)
		}
	}

}

@Composable
fun Description(
	modifier: Modifier = Modifier,
	detailUiEntity: OrganizationDetailUiEntity

) {
	val scrollState = rememberScrollState()
	Column(
		modifier = modifier.verticalScroll(scrollState),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
	) {
		Text(text = stringResource(R.string.location), style = MaterialTheme.typography.labelMedium)
		Text(text = detailUiEntity.location, style = MaterialTheme.typography.bodyMedium)
		Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
		Text(text = stringResource(R.string.cuisines), style = MaterialTheme.typography.labelMedium)
		Text(
			text = detailUiEntity.cuisines.joinToString(separator = ", "),
			style = MaterialTheme.typography.bodyMedium
		)
		Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
		Text(
			text = stringResource(R.string.description),
			style = MaterialTheme.typography.labelMedium
		)
		Text(text = detailUiEntity.description, style = MaterialTheme.typography.bodyMedium)
	}
}

@Composable
fun StarRatingBar(
	modifier: Modifier = Modifier,
	selectedColor: Color = MaterialTheme.colorScheme.primary,
	unselectedColor: Color = Color.Gray,
	rating: Float = 0f
) {
	Row(modifier = modifier.wrapContentWidth()) {
		for (i in 1..5) {
			when {
				rating > i -> {
					Icon(
						imageVector = Icons.Default.Star,
						contentDescription = null,
						tint = selectedColor
					)
				}

				rating < i -> {
					Icon(
						imageVector = Icons.Default.Star,
						contentDescription = null,
						tint = unselectedColor
					)
				}
			}
		}
		Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
		Text(text = rating.toString(), color = MaterialTheme.colorScheme.primary)
	}
}

@Preview
@Composable
fun StartRatingBarPreview(modifier: Modifier = Modifier) {
	RestaurantsTheme {
		StarRatingBar(rating = 4.5f)
	}
}

