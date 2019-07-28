package com.smartrequest.data.network.api

import com.fasterxml.jackson.core.type.TypeReference
import io.reactivex.Single
import java.io.File

interface ApiClient {

	fun <T> get(relativePath: String,
	            queryParams: Map<String, Any>?,
	            typeReference: TypeReference<T>): Single<T>

	fun <T> get(relativePath: String,
	            queryParams: Map<String, Any>?,
	            responseParser: com.smartrequest.data.network.api.ResponseParser<T>): Single<T>

	fun <T> post(relativePath: String, queryParams: Map<String, Any>?,
	             requestBody: Any?,
	             typeReference: TypeReference<T>): Single<T>

	fun <T> postFile(relativePath: String,
	                 file: File,
	                 requestBody: Any?,
	                 typeReference: TypeReference<T>): Single<T>

	fun <T> put(relativePath: String,
	            queryParams: Map<String, Any>?,
	            requestBody: Any?,
	            typeReference: TypeReference<T>): Single<T>

	fun <T> patch(relativePath: String,
	              queryParams: Map<String, Any>?,
	              requestBody: Any?,
	              typeReference: TypeReference<T>): Single<T>

	fun <T> delete(relativePath: String,
	               queryParams: Map<String, Any>?,
	               requestBody: Any?,
	               typeReference: TypeReference<T>): Single<T>

}