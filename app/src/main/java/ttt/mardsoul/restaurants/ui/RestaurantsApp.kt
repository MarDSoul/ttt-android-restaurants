package ttt.mardsoul.restaurants.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.mock.TestLogs
import ttt.mardsoul.restaurants.mock.TestTags
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme

@Composable
fun RestaurantsApp(modifier: Modifier = Modifier) {
	val navController = rememberNavController()

	val currentScreen = RestaurantScreen.valueOf(
		navController.currentBackStackEntry?.destination?.route ?: RestaurantScreen.ListScreen.name
	)

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			RestaurantsTopBar(
				currentScreen = currentScreen,
				canNavigateBack = navController.previousBackStackEntry != null,
				onNavigateBack = { navController.navigateUp() })
		}
	) { paddingValues ->
		NavigationApp(modifier = Modifier.padding(paddingValues), navController = navController)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsTopBar(
	modifier: Modifier = Modifier,
	currentScreen: RestaurantScreen,
	canNavigateBack: Boolean = false,
	onNavigateBack: () -> Unit = {}
) {
	TestLogs.show(
		TestTags.TOP_APP_BAR,
		"RestaurantsTopBar: recomposition with ${currentScreen.name}"
	)
	CenterAlignedTopAppBar(
		title = {
			Text(text = stringResource(currentScreen.titleId))
		},
		modifier = modifier,
		navigationIcon = {
			if (canNavigateBack)
				IconButton(onClick = onNavigateBack) {
					Icon(
						imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
						contentDescription = stringResource(R.string.navigate_back_cd)
					)
				}
		},
		actions = {
			FavouriteIcon(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
		},
		colors = TopAppBarColors(
			containerColor = MaterialTheme.colorScheme.background,
			scrolledContainerColor = MaterialTheme.colorScheme.background,
			navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
			titleContentColor = MaterialTheme.colorScheme.onBackground,
			actionIconContentColor = MaterialTheme.colorScheme.primary
		)
	)
}

@Composable
fun FavouriteIcon(
	modifier: Modifier = Modifier,
	count: Int = 0
) {
	Box(modifier = modifier, contentAlignment = Alignment.Center) {
		Icon(
			imageVector = Icons.Filled.Favorite,
			contentDescription = stringResource(R.string.favourite_cd),
			tint = MaterialTheme.colorScheme.primary
		)
		Text(
			text = count.toString(),
			color = MaterialTheme.colorScheme.onPrimary,
			style = MaterialTheme.typography.labelSmall
		)
	}
}

@Preview
@Composable
fun FavouriteIconPreview(modifier: Modifier = Modifier) {
	RestaurantsTheme {
		FavouriteIcon()
	}
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RestaurantsTopBarPreview(modifier: Modifier = Modifier) {
	RestaurantsTheme {
		RestaurantsTopBar(currentScreen = RestaurantScreen.ListScreen)
	}
}