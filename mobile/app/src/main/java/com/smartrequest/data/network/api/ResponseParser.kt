package com.smartrequest.data.network.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper


interface ResponseParser<out T> {

	fun parseResponse(objectMapper: ObjectMapper, jsonNode: JsonNode): T
	
}