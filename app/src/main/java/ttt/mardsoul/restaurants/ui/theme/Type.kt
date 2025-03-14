package ttt.mardsoul.restaurants.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
	labelMedium = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Medium,
		fontSize = 20.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp
	),
	bodyMedium = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp
	),
	titleMedium = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Bold,
		fontSize = 16.sp,
		lineHeight = 20.sp,
		letterSpacing = 0.1.sp
	),
	titleLarge = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Medium,
		fontSize = 20.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.sp
	)
)