package ttt.mardsoul.restaurants.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val shapes = Shapes(
	small = RoundedCornerShape(4.dp),
	medium = RoundedCornerShape(8.dp),
	large = RoundedCornerShape(
		topStart = 24.dp,
		topEnd = 24.dp,
		bottomStart = 8.dp,
		bottomEnd = 8.dp
	)
)

val customShapes = Shapes(
	large = RoundedCornerShape(24.dp)
)