package com.smartrequest.extensions.shared

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.View.MeasureSpec



fun View.generateBitmap(performMeasure: Boolean = false): Bitmap {
	if (performMeasure) {
		val specWidth = MeasureSpec.makeMeasureSpec(0 /* any */, MeasureSpec.UNSPECIFIED)
		measure(specWidth, specWidth)
	}

	val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
	val canvas = Canvas(bitmap)

	if (performMeasure) {
		layout(0, 0, measuredWidth, measuredHeight)
	}
	draw(canvas)

	return bitmap
}