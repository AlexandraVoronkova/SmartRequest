package com.smartrequest.data.network.api

import android.net.Uri
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Single
import io.reactivex.SingleEmitter
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.internal.http.HttpMethod
import timber.log.Timber
import java.io.File
import java.io.IOException
import javax.inject.Inject

class ApiClientImpl @Inject constructor(private val httpClient: OkHttpClient,
                                        private val objectMapper: ObjectMapper,
                                        private val baseUrl: String) : com.smartrequest.data.network.api.ApiClient {


	companion object {
		private val CONNECTION_TIMEOUT = 30L
		private val CONTENT_TYPE_JSON = "application/json"
		private val FILE_MIME_TYPE = "application/octet-stream"
		private val FILE_MEDIA_TYPE = MediaType.parse(com.smartrequest.data.network.api.ApiClientImpl.Companion.FILE_MIME_TYPE)

		private val METHOD_GET = "GET"
		private val METHOD_POST = "POST"
		private val METHOD_PUT = "PUT"
		private val METHOD_PATCH = "PATCH"
		private val METHOD_DELETE = "DELETE"

	}

	//region ===================== Public ======================

	override fun <T> get(relativePath: String,
	                     queryParams: Map<String, Any>?,
	                     typeReference: TypeReference<T>): Single<T> {
		return sendRequest(com.smartrequest.data.network.api.ApiClientImpl.Companion.METHOD_GET, relativePath, queryParams, null, typeReference)
	}

	override fun <T> get(relativePath: String, queryParams: Map<String, Any>?, responseParser: com.smartrequest.data.network.api.ResponseParser<T>): Single<T> {
		return sendRequest(com.smartrequest.data.network.api.ApiClientImpl.Companion.METHOD_GET, relativePath, queryParams, null, responseParser)
	}

	override fun <T> post(relativePath: String, queryParams: Map<String, Any>?,
	                      requestBody: Any?,
	                      typeReference: TypeReference<T>): Single<T> {
		return sendRequest(com.smartrequest.data.network.api.ApiClientImpl.Companion.METHOD_POST, relativePath, queryParams, requestBody, typeReference)
	}

	override fun <T> postFile(relativePath: String,
	                          file: File,
	                          requestBody: Any?,
	                          typeReference: TypeReference<T>): Single<T> {
		return sendFileRequest(com.smartrequest.data.network.api.ApiClientImpl.Companion.METHOD_POST, relativePath, file, requestBody, typeReference, null)
	}

	override fun <T> put(relativePath: String,
	                     queryParams: Map<String, Any>?,
	                     requestBody: Any?,
	                     typeReference: TypeReference<T>): Single<T> {
		return sendRequest(com.smartrequest.data.network.api.ApiClientImpl.Companion.METHOD_PUT, relativePath, queryParams, requestBody, typeReference)
	}

	override fun <T> patch(relativePath: String,
	                       queryParams: Map<String, Any>?,
	                       requestBody: Any?,
	                       typeReference: TypeReference<T>): Single<T> {
		return sendRequest(com.smartrequest.data.network.api.ApiClientImpl.Companion.METHOD_PATCH, relativePath, queryParams, requestBody, typeReference)
	}

	override fun <T> delete(relativePath: String,
	                        queryParams: Map<String, Any>?,
	                        requestBody: Any?,
	                        typeReference: TypeReference<T>): Single<T> {
		return sendRequest(com.smartrequest.data.network.api.ApiClientImpl.Companion.METHOD_DELETE, relativePath, queryParams, requestBody, typeReference)
	}

	//endregion


	//region ==================== Internal request logic ====================


	private fun getHttpBody(requestBody: Any?): RequestBody? {
		return if (requestBody != null) {
			RequestBody.create(MediaType.parse(com.smartrequest.data.network.api.ApiClientImpl.Companion.CONTENT_TYPE_JSON), objectMapper.writeValueAsString(requestBody))
		} else {
			RequestBody.create(MediaType.parse(com.smartrequest.data.network.api.ApiClientImpl.Companion.CONTENT_TYPE_JSON), objectMapper.writeValueAsString(emptyMap<Any, Any>()))
		}
	}

	private fun getFileRequestBody(file: File, additionalBodyParams: Any?): RequestBody? {
		val builder =  MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("file", "file",
						RequestBody.create(MultipartBody.FORM, file))
		if (additionalBodyParams != null) {
			builder.addFormDataPart("params", objectMapper.writeValueAsString(additionalBodyParams))
		}
		return builder.build()
	}

	@Throws(com.smartrequest.data.network.api.InvalidUrlException::class)
	private fun getHttpUrl(relativePath: String, pathParams: Map<String, Any>?): HttpUrl {
		val url: HttpUrl = HttpUrl.parse(baseUrl.plus(relativePath)) ?: throw com.smartrequest.data.network.api.InvalidUrlException()
		val urlBuilder = url.newBuilder()

		pathParams?.forEach { entry ->
			urlBuilder.addEncodedQueryParameter(entry.key, Uri.encode(entry.value.toString()))
		}

		return urlBuilder.build()
	}

	private fun <T> sendRequest(httpMethod: String,
	                            relativePath: String,
	                            pathParams: Map<String, Any>?,
	                            requestBody: Any?,
	                            typeReference: TypeReference<T>): Single<T> {
		return sendRequest(httpMethod, relativePath, pathParams, requestBody, typeReference, null)
	}

	private fun <T> sendRequest(httpMethod: String,
	                            relativePath: String,
	                            pathParams: Map<String, Any>?,
	                            requestBody: Any?,
	                            responseParser: com.smartrequest.data.network.api.ResponseParser<T>): Single<T> {
		return sendRequest(httpMethod, relativePath, pathParams, requestBody, null, responseParser)
	}

	private fun <T> sendRequest(
			httpMethod: String,
			relativePath: String,
			pathParams: Map<String, Any>?,
			requestBody: Any?,
			typeReference: TypeReference<T>?,
			responseParser: com.smartrequest.data.network.api.ResponseParser<T>?): Single<T> {

		return Single.create(fun(emitter: SingleEmitter<T>) {

			val body = when (HttpMethod.permitsRequestBody(httpMethod)) {
				true -> this@ApiClientImpl.getHttpBody(requestBody)
				false -> null
			}

			val url = this@ApiClientImpl.getHttpUrl(relativePath, pathParams)

			val request = Request.Builder()
					.url(url)
					.method(httpMethod, body)
					.build()

			makeRequest(emitter, request, typeReference, responseParser)
		})
	}

	private fun <T> sendFileRequest(
			httpMethod: String,
			relativePath: String,
			file: File,
			additionalBodyParams: Any?,
			typeReference: TypeReference<T>?,
			responseParser: com.smartrequest.data.network.api.ResponseParser<T>?): Single<T> {

		return Single.create(fun(emitter: SingleEmitter<T>) {

			val body = when (HttpMethod.permitsRequestBody(httpMethod)) {
				true -> this@ApiClientImpl.getFileRequestBody(file, additionalBodyParams)
				false -> null
			}

			val url = this@ApiClientImpl.getHttpUrl(relativePath, null)

			val request = Request.Builder()
					.url(url)
					.method(httpMethod, body)
					.build()

			makeRequest(emitter, request, typeReference, responseParser)
		})
	}

	private fun <T> makeRequest(emitter: SingleEmitter<T>,
	                            request: Request,
	                            typeReference: TypeReference<T>?,
	                            responseParser: com.smartrequest.data.network.api.ResponseParser<T>?) {
		try {
			val response = httpClient.newCall(request).execute()
			val responseString = response.body()!!.string()
			val jsonNode = objectMapper.readTree(responseString)
					?: throw com.smartrequest.data.network.api.ResponseParserException()

			if (com.smartrequest.data.Config.DEBUG) {
				Timber.w("Request response: %s", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode))
			}

			if (jsonNode.has("error")) {
				val error = objectMapper.readValue<com.smartrequest.data.network.ServerError>(jsonNode.traverse(), com.smartrequest.data.network.ServerError::class.java)
				emitter.tryOnError(error)
			} else {
				if (typeReference != null) {
					val result = objectMapper.readValue<T>(jsonNode.traverse(), typeReference)
					if (!emitter.isDisposed) {
						emitter.onSuccess(result)
					}
				} else if (responseParser != null) {
					val result = responseParser.parseResponse(objectMapper, jsonNode)
					if (!emitter.isDisposed) {
						emitter.onSuccess(result)
					}
				}
			}
		} catch (e: IOException) {
			e.printStackTrace()
			emitter.tryOnError(e)
		}
	}

	//endregion

}