package ttt.mardsoul.restaurants.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.ui.screendetails.RestaurantDetailsScreen
import ttt.mardsoul.restaurants.ui.screenlist.RestaurantsListScreen

enum class RestaurantScreen(@StringRes val titleId: Int) {
	ListScreen(R.string.list_app_bar_title),
	DetailsScreen(R.string.details_app_bar_title)

}

@Composable
fun NavigationApp(
	modifier: Modifier = Modifier,
	navController: NavHostController
) {
	NavHost(navController = navController, startDestination = RestaurantScreen.ListScreen.name) {
		composable(RestaurantScreen.ListScreen.name) {
			RestaurantsListScreen(modifier = modifier, onItemClick = { id ->
				Log.d("Navigation", "Navigating to details with ID: $id")
				navController.navigate("${RestaurantScreen.DetailsScreen.name}/$id")
			})
		}
		val route = "${RestaurantScreen.DetailsScreen.name}/{restaurantId}"
		val arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
		composable(route = route, arguments = arguments) { backStackEntry ->
			val restaurantId = backStackEntry.arguments?.getInt("restaurantId") ?: 0
			RestaurantDetailsScreen(modifier = modifier, restaurantId = restaurantId)
		}
	}
}

