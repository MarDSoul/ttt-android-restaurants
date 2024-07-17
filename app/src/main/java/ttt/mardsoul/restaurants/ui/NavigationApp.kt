package ttt.mardsoul.restaurants.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.ui.screendetails.RestaurantDetailsScreen
import ttt.mardsoul.restaurants.ui.screenlist.RestaurantsListScreen
import ttt.mardsoul.restaurants.utils.NAVIGATION_TAG

enum class RestaurantScreen(@StringRes val titleId: Int) {
	ListScreen(R.string.list_app_bar_title),
	DetailsScreen(R.string.details_app_bar_title)

}

@Composable
fun NavigationApp(
	modifier: Modifier = Modifier,
	navController: NavHostController
) {
	val viewModel: RestaurantsListAndDetailsViewModel = hiltViewModel()

	NavHost(navController = navController, startDestination = RestaurantScreen.ListScreen.name) {
		composable(RestaurantScreen.ListScreen.name) {
			val listUiState by viewModel.listUiState.collectAsStateWithLifecycle()

			RestaurantsListScreen(
				modifier = modifier,
				uiState = listUiState,
				onItemClick = { viewModel.getDetails(it) })
			Log.d(NAVIGATION_TAG, "RestaurantsListScreen compose")


			LaunchedEffect(key1 = Unit) {
				viewModel.navigateToDetails.collect {
					Log.d(NAVIGATION_TAG, "LaunchedEffect: navigate to details")
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
					disposableEffectKey = RestaurantScreen.DetailsScreen.name
				)
				Log.d(NAVIGATION_TAG, "RestaurantDetailsScreen compose")
			}
		}
	}
}

