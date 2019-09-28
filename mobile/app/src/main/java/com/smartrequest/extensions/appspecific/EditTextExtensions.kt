package com.smartrequest.extensions.appspecific

import android.widget.EditText
import com.smartrequest.helpers.FormatHelper
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun EditText.configurePhoneNumberFormatter(needFocusChangeListener: Boolean = true): MaskedTextChangedListener {
	return this.configureFormatter(FormatHelper.RUSSIAN_PHONE_NUMBER_FORMAT, needFocusChangeListener)
}

fun EditText.configureCardNumberFormatter(needFocusChangeListener: Boolean = true) {
	this.configureFormatter(FormatHelper.CARD_NUMBER_FORMAT, needFocusChangeListener)
}

fun EditText.configureCardExpiredDateFormatter(needFocusChangeListener: Boolean = true) {
	this.configureFormatter(FormatHelper.CARD_EXPIRED_DATE_FORMAT, needFocusChangeListener)
}

private fun EditText.configureFormatter(format: String, needFocusChangeListener: Boolean): MaskedTextChangedListener {
	val listener = MaskedTextChangedListener(format, this, null)
	this.addTextChangedListener(listener)
	if (needFocusChangeListener) {
		this.onFocusChangeListener = listener
	}

	return listener
}