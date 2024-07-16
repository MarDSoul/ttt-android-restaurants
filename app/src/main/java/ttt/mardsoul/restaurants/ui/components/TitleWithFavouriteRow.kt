package ttt.mardsoul.restaurants.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ttt.mardsoul.restaurants.R

@Composable
fun TitleWithFavouriteRow(
	modifier: Modifier = Modifier,
	name: String,
	isFavorite: Boolean,
	onFavoriteClick: () -> Unit = {}
) {
	Row(
		modifier = modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(text = name)
		IconButton(onClick = onFavoriteClick) {
			Icon(
				imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
				contentDescription = stringResource(R.string.favourite_cd),
				tint = MaterialTheme.colorScheme.primary
			)
		}
	}
}