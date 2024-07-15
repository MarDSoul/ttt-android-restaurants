package ttt.mardsoul.restaurants.ui

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ttt.mardsoul.restaurants.R
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme

@Composable
fun RestaurantsApp(modifier: Modifier = Modifier) {
	Scaffold(
		modifier = modifier,
		topBar = { RestaurantsTopBar() }
	) { paddingValues ->
		RestaurantsListScreen(modifier = Modifier.padding(paddingValues))
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsTopBar(
	modifier: Modifier = Modifier,
	canNavigateBack: Boolean = false,
	onNavigateBack: () -> Unit = {}
) {
	CenterAlignedTopAppBar(
		title = {
			Text(text = stringResource(R.string.list_app_bar_title))
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
			FavouriteIcon()
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
			contentDescription = null,
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
		RestaurantsTopBar(canNavigateBack = true)
	}
}