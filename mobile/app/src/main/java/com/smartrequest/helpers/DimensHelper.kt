package com.smartrequest.helpers

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue


object DimensHelper {

	fun dpToPx(context: Context, value: Float): Float {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
	}

	fun dpToPx(context: Context, value: Int): Int {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics).toInt()
	}

	fun pxToDp(context: Context, value: Float): Float {
		val metrics = context.resources.displayMetrics
		val dp = value / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
		return dp
	}

}