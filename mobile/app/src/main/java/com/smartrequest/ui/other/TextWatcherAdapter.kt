package com.smartrequest.ui.other

import android.text.Editable
import android.text.TextWatcher

open class TextWatcherAdapter(): TextWatcher {

	override fun afterTextChanged(editable: Editable) {
	}

	override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
	}

	override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
	}
}