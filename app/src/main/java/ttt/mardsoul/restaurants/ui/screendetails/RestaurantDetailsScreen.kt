package ttt.mardsoul.restaurants.ui.screendetails

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.ui.components.FavouriteIconButton
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme
import ttt.mardsoul.restaurants.ui.theme.customShapes
import ttt.mardsoul.restaurants.utils.DETAILS_SCREEN_TAG

@Composable
fun RestaurantDetailsScreen(
	modifier: Modifier = Modifier,
	organizationDetailUiEntity: OrganizationDetailUiEntity,
	resetState: () -> Unit,
	disposableEffectKey: Any = Unit
) {
	Log.d(DETAILS_SCREEN_TAG, "RestaurantDetailsScreen recomposed")

	Column(modifier = modifier) {
		PhotoPager(
			modifier = Modifier
				.height(dimensionResource(R.dimen.image_details_height))
				.fillMaxWidth(),
			photosListUrl = organizationDetailUiEntity.imageListUrl
		)
		HeaderInfo(detailUiEntity = organizationDetailUiEntity)
		Description(modifier = Modifier.weight(1f), detailUiEntity = organizationDetailUiEntity)
	}

	DisposableEffect(disposableEffectKey) {
		onDispose {
			Log.d(DETAILS_SCREEN_TAG, "RestaurantDetailsScreen onDispose")
			resetState()
		}
	}
}

@Composable
fun PhotoPager(
	modifier: Modifier = Modifier,
	photosListUrl: List<String>
) {
	val pagerState = rememberPagerState(pageCount = { photosListUrl.size })
	val contentPadding = PaddingValues(
		start = if (pagerState.currentPage == 0) {
			dimensionResource(R.dimen.padding_zero)
		} else {
			dimensionResource(R.dimen.padding_large)
		},
		end = if (pagerState.currentPage == pagerState.pageCount - 1) {
			dimensionResource(R.dimen.padding_zero)
		} else {
			dimensionResource(R.dimen.padding_large)
		}
	)
	HorizontalPager(
		state = pagerState,
		modifier = modifier,
		beyondViewportPageCount = 2,
		pageSpacing = dimensionResource(R.dimen.padding_small),
		contentPadding = contentPadding
	) { page ->
		Card(
			modifier = Modifier
				.fillMaxSize(),
			shape = customShapes.large
		) {
			AsyncImage(
				model = photosListUrl[page],
				contentDescription = null,
				modifier = Modifier.fillMaxSize(),
				contentScale = ContentScale.Crop
			)
		}
	}
}

@Composable
fun HeaderInfo(
	modifier: Modifier = Modifier,
	detailUiEntity: OrganizationDetailUiEntity,
	onFavoriteClick: () -> Unit = {}
) {
	Column(modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(text = detailUiEntity.name, style = MaterialTheme.typography.titleLarge)
			FavouriteIconButton(
				isFavorite = detailUiEntity.isFavorite,
				onFavoriteClick = onFavoriteClick
			)
		}

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
	val textColor = Color.Gray
	Column(
		modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
	) {
		with(detailUiEntity) {
			if (location.isNotBlank()) {
				Text(
					text = stringResource(R.string.location),
					style = MaterialTheme.typography.labelMedium
				)
				Text(
					text = detailUiEntity.location,
					style = MaterialTheme.typography.bodyMedium,
					color = textColor
				)
				Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
			}

			if (cuisines.isNotEmpty()) {
				Text(
					text = stringResource(R.string.cuisines),
					style = MaterialTheme.typography.labelMedium
				)
				Text(
					text = detailUiEntity.cuisines.joinToString(separator = ", "),
					style = MaterialTheme.typography.bodyMedium,
					color = textColor
				)
				Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_small)))
			}

			if (description.isNotBlank()) {
				val textLineBreak = LineBreak.Paragraph.copy(
					strategy = LineBreak.Strategy.Balanced
				)

				Text(
					text = stringResource(R.string.description),
					style = MaterialTheme.typography.labelMedium
				)
				Text(
					text = detailUiEntity.description,
					modifier = Modifier.verticalScroll(scrollState),
					style = MaterialTheme.typography.bodyMedium.copy(
						lineBreak = textLineBreak,
						hyphens = Hyphens.Auto
					),
					color = textColor
				)
			}
		}
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
				rating >= i -> {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailScreenPreview(modifier: Modifier = Modifier) {

	val mockData = OrganizationDetailUiEntity(
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

	RestaurantsTheme {
		RestaurantDetailsScreen(
			organizationDetailUiEntity = mockData,
			resetState = { }
		)
	}
}

