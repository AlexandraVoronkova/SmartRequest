package com.smartrequest.data.entities.request.model

import android.support.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@Keep
data class Request(val id: Int,
				   val name: String,
				   val status: String) {
}