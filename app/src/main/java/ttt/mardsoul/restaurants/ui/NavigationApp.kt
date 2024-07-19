package ttt.mardsoul.restaurants.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.mock.TestLogs
import ttt.mardsoul.restaurants.mock.TestTags
import ttt.mardsoul.restaurants.ui.components.ErrorEvent
import ttt.mardsoul.restaurants.ui.screendetails.RestaurantDetailsScreen
import ttt.mardsoul.restaurants.ui.screenlist.RestaurantsListScreen

enum class RestaurantScreen(@StringRes val titleId: Int) {
	ListScreen(R.string.list_app_bar_title),
	DetailsScreen(R.string.details_app_bar_title)
}

@Composable
fun NavigationApp(
	modifier: Modifier = Modifier,
	navController: NavHostController,
	viewModel: RestaurantsViewModel
) {
	val context = LocalContext.current
	ErrorEvent(sideEffectFlow = viewModel.errorEvent) {
		TestLogs.show(TestTags.ERROR_EVENT, "ErrorEvent: recomposition")
		if (it is ErrorEvent.ErrorMessage) {
			Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
		}
	}

	NavHost(navController = navController, startDestination = RestaurantScreen.ListScreen.name) {
		composable(RestaurantScreen.ListScreen.name) {
			val listUiState by viewModel.listUiState.collectAsStateWithLifecycle()

			RestaurantsListScreen(
				modifier = modifier,
				uiState = listUiState,
				onItemClick = { viewModel.getDetails(it) },
				onFavoriteClick = { id, isFavorite ->
					viewModel.onFavoriteClick(id, isFavorite)
				}
			)

			LaunchedEffect(key1 = Unit) {
				viewModel.navigateToDetails.collect {
					TestLogs.show(TestTags.NAVIGATION, "LaunchedEffect: navigate to details")
					navController.navigate(RestaurantScreen.DetailsScreen.name) {
						popUpTo(RestaurantScreen.ListScreen.name) { saveState = true }
					}
				}
			}
		}
		composable(route = RestaurantScreen.DetailsScreen.name) {
			val detailsUiState by viewModel.detailsUiState.collectAsStateWithLifecycle()
			if (detailsUiState is DetailsUiState.Success) {
				RestaurantDetailsScreen(
					modifier = modifier,
					organizationDetailUiEntity = (detailsUiState as DetailsUiState.Success).data,
					resetState = { viewModel.resetDetailsState() },
					disposableEffectKey = RestaurantScreen.DetailsScreen.name,
					onFavoriteClick = { id, isFavorite ->
						viewModel.onFavoriteClick(id, isFavorite)
					}
				)
				TestLogs.show(TestTags.NAVIGATION, "RestaurantDetailsScreen compose")
			}
		}
	}
}

