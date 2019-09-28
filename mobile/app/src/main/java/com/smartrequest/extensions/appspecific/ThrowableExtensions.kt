package com.smartrequest.extensions.appspecific

import android.util.MalformedJsonException
import com.smartrequest.R
import com.fasterxml.jackson.core.JsonProcessingException
import java.io.IOException

fun Throwable.getErrorMessage(): String? {

	val context = com.smartrequest.components.AppContext.instance
	val errorDescription: String?
	errorDescription = when (this) {
		is com.smartrequest.data.network.ServerError -> {
			this.message
		}
		is MalformedJsonException,
		is JsonProcessingException,
		is com.smartrequest.data.network.api.ResponseParserException -> {
			context.getString(R.string.error_message_internal_error_occurred_try_again_later)
		}
		is IOException -> {
			context.getString(R.string.error_message_network_unavailable_check_internet_connection)
		}
		else -> context.getString(R.string.error_message_internal_error_occurred_try_again_later)
	}

	return errorDescription
}