package com.smartrequest.data.network.headers

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TimeZoneHeaderProvider @Inject constructor(): com.smartrequest.data.network.headers.HeaderProvider {

	private val dateFormat = SimpleDateFormat("Z")

	//region ===================== HeaderProvider ======================

	override fun provideHeader(): com.smartrequest.data.network.headers.RequestHeader {
		val timeZoneOffset = dateFormat.format(Date())
		return com.smartrequest.data.network.headers.RequestHeader("TimeZone", timeZoneOffset)
	}

	//endregion

}