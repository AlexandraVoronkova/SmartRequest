package com.smartrequest.data.entities.category

import com.fasterxml.jackson.core.type.TypeReference
import com.smartrequest.data.entities.category.model.Category
import com.smartrequest.data.network.api.ApiClient
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class CategoryServiceImpl @Inject constructor(@Named("nonAuth") private val apiClient: ApiClient) : CategoryService {

	companion object {

		const val URL = "http://192.168.43.198:80/cats"
	}

	override fun getCategoryList(): Single<List<Category>> {
		return apiClient.get(URL, null, object : TypeReference<List<Category>>() {})
	}

	override fun getCategoryListById(id: Int): Single<List<Category>> {
		return apiClient.get("$URL/$id", null, object : TypeReference<List<Category>>() {})
	}
}