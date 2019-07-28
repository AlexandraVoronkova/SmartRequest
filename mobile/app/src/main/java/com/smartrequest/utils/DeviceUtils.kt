package com.smartrequest.utils

import android.app.Activity
import android.content.res.Resources
import android.os.Build


object DeviceUtils {

	fun getStatusBarHeight(resources: Resources): Int {
		val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
		return if (resourceId > 0)
			resources.getDimensionPixelSize(resourceId)
		else
			Math.ceil(((if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) 24 else 25) * resources.displayMetrics.density).toDouble()).toInt()
	}

	fun getActionBarHeight(activity: Activity): Int {
		var actionBarHeight = 0
		val styledAttributes = activity.theme.obtainStyledAttributes(intArrayOf(android.support.v7.appcompat.R.attr.actionBarSize))
		actionBarHeight = styledAttributes.getDimension(0, 0f).toInt()
		styledAttributes.recycle()
		return actionBarHeight
	}

}