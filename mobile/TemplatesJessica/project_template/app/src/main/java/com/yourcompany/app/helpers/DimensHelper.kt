package {{.answers.appPackageName}}.helpers

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue


object DimensHelper {

	fun dpToPx(context: Context, value: Float): Float {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
	}

	fun pxToDp(context: Context, value: Float): Float {
		val metrics = context.resources.displayMetrics
		val dp = value / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
		return dp
	}

}