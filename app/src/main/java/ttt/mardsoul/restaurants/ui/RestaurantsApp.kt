package ttt.mardsoul.restaurants.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.mock.TestLogs
import ttt.mardsoul.restaurants.mock.TestTags
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme

@Composable
fun RestaurantsApp(modifier: Modifier = Modifier) {
	val navController = rememberNavController()
	val backStackEntry by navController.currentBackStackEntryAsState()
	val currentScreen = RestaurantScreen.valueOf(
		backStackEntry?.destination?.route ?: RestaurantScreen.ListScreen.name
	)

	val viewModel: RestaurantsViewModel = hiltViewModel()
	val appBarState by viewModel.appBarState.collectAsState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			RestaurantsTopBar(
				currentScreen = currentScreen,
				appBarState = appBarState,
				canNavigateBack = navController.previousBackStackEntry != null,
				onNavigateBack = { navController.navigateUp() },
				onClickFilter = { viewModel.filterList() }
			)
		}
	) { paddingValues ->
		NavigationApp(
			modifier = Modifier.padding(paddingValues),
			navController = navController,
			viewModel = viewModel
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsTopBar(
	modifier: Modifier = Modifier,
	appBarState: AppBarState,
	currentScreen: RestaurantScreen,
	canNavigateBack: Boolean = false,
	onNavigateBack: () -> Unit = {},
	onClickFilter: () -> Unit = {}
) {
	TestLogs.show(
		TestTags.TOP_APP_BAR,
		"RestaurantsTopBar: recomposition with ${currentScreen.name}, " +
				"count: ${appBarState.countFavorite}"
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
			if (currentScreen != RestaurantScreen.DetailsScreen) {
				FavouriteIcon(
					modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
					countFavorite = appBarState.countFavorite,
					isFiltered = appBarState.isFavoritesFiltered,
					onClick = onClickFilter
				)
			}
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
	countFavorite: Int,
	isFiltered: Boolean,
	onClick: () -> Unit
) {
	TestLogs.show(
		TestTags.TOP_APP_BAR,
		"FavouriteIcon: recomposition, " +
				"count: $countFavorite, " +
				"isFiltered: $isFiltered"
	)
	IconButton(onClick = onClick, modifier = modifier) {
		Icon(
			imageVector = if (isFiltered) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
			contentDescription = stringResource(R.string.favourite_cd),
			tint = MaterialTheme.colorScheme.primary
		)
		Text(
			text = countFavorite.toString(),
			color = if (isFiltered) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
			style = MaterialTheme.typography.labelSmall
		)
	}
}

@Preview
@Composable
fun FavouriteIconPreview(modifier: Modifier = Modifier) {
	RestaurantsTheme {
		FavouriteIcon(
			countFavorite = 2,
			isFiltered = true,
			onClick = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun RestaurantsTopBarPreview(modifier: Modifier = Modifier) {
	RestaurantsTheme {
		RestaurantsTopBar(
			appBarState = AppBarState(countFavorite = 2, isFavoritesFiltered = true),
			currentScreen = RestaurantScreen.ListScreen
		)
	}
}

@Preview(showBackground = true)
@Composable
fun RestaurantsTopBarDetailsPreview(modifier: Modifier = Modifier) {
	RestaurantsTheme {
		RestaurantsTopBar(
			appBarState = AppBarState(),
			canNavigateBack = true,
			currentScreen = RestaurantScreen.DetailsScreen
		)
	}
}