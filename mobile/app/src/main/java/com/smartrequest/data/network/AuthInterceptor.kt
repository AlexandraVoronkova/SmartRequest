package com.smartrequest.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val authCredentialsService: com.smartrequest.data.entities.auth.AuthCredentialsService) :
		Interceptor {

	companion object {

		@JvmStatic
		private val lock = Object()

	}


	override fun intercept(chain: Interceptor.Chain): Response {
		var request = chain.request()

		//Build new request
		val builder = request.newBuilder()

		val authHeaders = authCredentialsService.getAuthHeaders() //save auth headers of this request for future
		setAuthHeader(builder, authHeaders)

		request = builder.build() //overwrite old request
		val response =
				chain.proceed(request) //perform request, here original request will be executed

		if (response.code() == 401) { //if unauthorized
			logout()
		}

		return response
	}

	private fun setAuthHeader(requestBuilder: Request.Builder, authHeaders: Map<String, String>) {
		if (authHeaders.isNotEmpty()) {
			for (entry in authHeaders.entries) {
				requestBuilder.header(entry.key, entry.value)
			}
		}

	}

	private fun logout() {
		authCredentialsService.performLogout()
	}
}