package com.smartrequest.data.entities.auth.model

import android.support.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@Keep
data class AuthResponse(@JsonProperty("access") val accessToken: com.smartrequest.data.entities.auth.model.AuthResponse.AccessToken) {

	@Keep
	data class AccessToken(val token: String,
	                       val refresh: String,
	                       val expire: Date)

}