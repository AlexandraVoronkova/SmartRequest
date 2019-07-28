package com.smartrequest.data.network

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.util.*


class UnixTimestampSerializer : JsonSerializer<Date>() {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: Date, gen: JsonGenerator, serializers: SerializerProvider) {
		gen.writeNumber(value.time / 1000)
	}
}