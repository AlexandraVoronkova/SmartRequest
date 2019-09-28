package com.smartrequest.ui.widget

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.smartrequest.R


class AspectRatioImageView : AppCompatImageView {

	var heightRatio: Double = 0.0
		set(ratio) {
			if (ratio != heightRatio) {
				field = ratio
				requestLayout()
			}
		}

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
		parseAttributes(context, attrs)
	}

	constructor(context: Context) : super(context) {}

	private fun parseAttributes(context: Context, attrs: AttributeSet) {
		//Typeface.createFromAsset doesn't work in the layout editor. Skipping...
		if (isInEditMode()) {
			return
		}

		val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
		val aspectRatio = styledAttrs.getFloat(R.styleable.AspectRatioImageView_aspectRatio, 0.0f).toDouble()
		styledAttrs.recycle()

		if (aspectRatio != 0.0) {
			heightRatio = aspectRatio
		}
	}

	protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		if (heightRatio > 0.0) {
			// set the image views size
			val width = MeasureSpec.getSize(widthMeasureSpec)
			val height = (width * heightRatio).toInt()
			setMeasuredDimension(width, height)
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		}
	}
}