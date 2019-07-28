package com.smartrequest.data.entities.category.model

import android.support.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@Keep
data class Category(val id: Int,
					val name: String) {
}