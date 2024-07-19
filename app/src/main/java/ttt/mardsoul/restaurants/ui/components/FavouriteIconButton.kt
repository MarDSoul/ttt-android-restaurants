package ttt.mardsoul.restaurants.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ttt.mardsoul.restaurants.R

@Composable
fun FavouriteIconButton(
	isFavorite: Boolean,
	onFavoriteClick: () -> Unit
) {
	IconButton(onClick = onFavoriteClick) {
		Icon(
			imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
			contentDescription = stringResource(R.string.favourite_cd),
			tint = MaterialTheme.colorScheme.primary
		)
	}
}