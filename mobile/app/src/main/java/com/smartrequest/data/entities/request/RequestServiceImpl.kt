package com.smartrequest.data.entities.request

import android.content.Context
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.smartrequest.data.entities.auth.model.AuthResponse
import com.smartrequest.data.entities.category.model.Category
import com.smartrequest.data.entities.request.model.Request
import com.smartrequest.data.network.api.ApiClient
import com.smartrequest.data.network.api.ResponseParserException
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class RequestServiceImpl @Inject constructor(@Named("nonAuth") private val apiClient: ApiClient) : RequestService {

	companion object {

		const val URL = "http://192.168.43.198:80/req"
	}

	private val requestList = mutableListOf<Request>()

	override fun getStatusList(): Single<List<Request>> {
		return Single.just(requestList)
	}

	override fun addRequest(request: Request) {
		requestList.add(request)
	}

	override fun sendRequest(userId: Int, categoryId: Int, text: String): Completable {
		val params = mutableMapOf<String, Any>().apply {
			put("user_req", userId)
			put("cat", categoryId)
			put("text", text)
		}
		return apiClient.post(URL, null, params, object :
				TypeReference<Any>() {})
				.onErrorReturn {
					return@onErrorReturn if (it is ResponseParserException) {
						 Single.just(true)
					} else {
						 Single.error<Boolean>(it)
					}
				}
				.ignoreElement()
	}
}
