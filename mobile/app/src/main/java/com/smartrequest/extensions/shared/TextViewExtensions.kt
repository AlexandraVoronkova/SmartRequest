package com.smartrequest.extensions.shared

import android.graphics.Typeface
import android.support.annotation.FontRes
import android.support.v4.content.res.ResourcesCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.widget.TextView


fun TextView.addTypefaceSpan(textPart: String, @FontRes fontResId: Int) {
	val typeface: Typeface = ResourcesCompat.getFont(context, fontResId)!!
	addSpan(textPart, com.smartrequest.ui.other.span.CustomTypefaceSpan(typeface))
}


fun TextView.isEllipsized(): Boolean {
	// Initialize the resulting variable
	var result = false
	// Check if the supplied TextView is not null
	// Check if ellipsizing the text is enabled
	val truncateAt = this.ellipsize
	if (truncateAt != null && TextUtils.TruncateAt.MARQUEE != truncateAt) {
		// Retrieve the layout in which the text is rendered
		val layout = this.layout
		if (layout != null) {
			// Iterate all lines to search for ellipsized text
			for (index in 0 until layout.lineCount) {
				// Check if characters have been ellipsized away within this line of text
				result = layout.getEllipsisCount(index) > 0
				// Stop looping if the ellipsis character has been found
				if (result) {
					break
				}
			}
		}
	}
	return result
}

//region ===================== Private ======================

private fun TextView.addSpan(textPart: String, span: Any) {
	val text = text
	val string = text.toString()

	val start = string.indexOf(textPart)
	val end = start + textPart.length
	if (start == -1) {
		return
	}

	if (text is Spannable) {
		text.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
	} else {
		val s = SpannableString.valueOf(text)
		s.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		setText(s)
	}
}

//endregion