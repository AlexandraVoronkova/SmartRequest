package com.smartrequest.ui.other

import android.view.ViewGroup
import io.mobilife.upscreenmessage.UpScreenMessage


object UpScreenMessageTypes {

	const val TYPE_ERROR = 1
	const val TYPE_WARNING = 2
	const val TYPE_SUCCESS = 3

}

fun UpScreenMessage.showErrorMessage(message: String, container: ViewGroup) {
	UpScreenMessage.showMessage(message, UpScreenMessageTypes.TYPE_ERROR, container)
}

fun UpScreenMessage.showWarningMessage(message: String, container: ViewGroup) {
	UpScreenMessage.showMessage(message, UpScreenMessageTypes.TYPE_WARNING, container)
}

fun UpScreenMessage.showSuccessMessage(message: String, container: ViewGroup) {
	UpScreenMessage.showMessage(message, UpScreenMessageTypes.TYPE_SUCCESS, container)
}