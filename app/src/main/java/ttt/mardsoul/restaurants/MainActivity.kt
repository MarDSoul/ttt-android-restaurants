package ttt.mardsoul.restaurants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ttt.mardsoul.restaurants.ui.RestaurantsApp
import ttt.mardsoul.restaurants.ui.theme.RestaurantsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			RestaurantsTheme {
				RestaurantsApp()
			}
		}
	}
}