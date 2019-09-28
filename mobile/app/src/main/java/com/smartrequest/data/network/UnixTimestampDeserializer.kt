package com.smartrequest.data.network

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


class UnixTimestampDeserializer : JsonDeserializer<Date>() {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, context: DeserializationContext): Date {
		val unixTimestamp = parser.text.trim { it <= ' ' }
		return Date(TimeUnit.SECONDS.toMillis(java.lang.Long.valueOf(unixTimestamp)))
	}
}