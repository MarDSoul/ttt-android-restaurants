package ttt.mardsoul.restaurants.mock

import android.util.Log

object TestLogs {
	fun show(tagsEnum: TestTags, msg: String) {
		Log.d(tagsEnum.tag, msg)
	}
}

enum class TestTags(val tag: String) {
	VIEW_MODEL("VIEW_MODEL_TAG"),
	NAVIGATION("NAVIGATION_TAG"),
	LIST_SCREEN("LIST_SCREEN_TAG"),
	DETAILS_SCREEN("DETAILS_SCREEN_TAG"),
	ERROR_EVENT("ERROR_EVENT_TAG"),
	TOP_APP_BAR("TOP_APP_BAR_TAG")
}